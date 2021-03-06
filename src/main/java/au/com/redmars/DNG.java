package au.com.redmars;

import java.nio.ByteBuffer;
import java.util.Arrays;

import au.com.redmars.ifd.AsciiIFD;
import au.com.redmars.ifd.ByteIFD;
import au.com.redmars.ifd.CameraOffset;
import au.com.redmars.ifd.DoubleIFD;
import au.com.redmars.ifd.FloatIFD;
import au.com.redmars.ifd.IFDEntry;
import au.com.redmars.ifd.IFDStruct;
import au.com.redmars.ifd.LongIFD;
import au.com.redmars.ifd.RationalIFD;
import au.com.redmars.ifd.ShortIFD;
import au.com.redmars.ifd.TagIdentifier;
import au.com.redmars.ifd.UndefinedIFD;

public class DNG {
    private ImageHeader imageHeader;
    private byte[] rawDNGBytes;
    private String make;

    public static boolean isDNG(String filePath) {
        return filePath.toUpperCase().endsWith("DNG") || filePath.toUpperCase().endsWith("TIF");
    }

    public int readIFDEntries(IFDStruct root, Integer offset,Integer makeOffset) throws Exception {
        if (offset == null)
            offset = imageHeader.ifdOffset.intValue();
        int ptr = offset + 2;
        int ifdEntryCount = ByteBuffer.wrap(Arrays.copyOfRange(rawDNGBytes, offset, offset + 2))
                .order(imageHeader.byteOrder).getChar();
        if (App.verbose) System.out.printf("%sExpecting %d entries\r\n",StringUtils.verboseDateTime(),ifdEntryCount);
        for (int i = 0; i < ifdEntryCount; ++i) {
            //if (App.verbose) System.out.printf("%sProcessing entry number:%d\r\n",StringUtils.verboseDateTime(),i+1);
            IFDEntry currentTag = new IFDEntry(rawDNGBytes, ptr, imageHeader.byteOrder,makeOffset);
            if (currentTag.getFieldType() == null) {
                root.addChild(currentTag);
            } else {
                switch (currentTag.getFieldType()) {
                    case ASCII:
                        root.addChild(new AsciiIFD(currentTag));
                        break;
                    case UNKNOWN:
                    case UNDEFINED:
                        root.addChild(new UndefinedIFD(currentTag));
                        break;
                    case SBYTE:
                    case BYTE:
                        root.addChild(new ByteIFD(currentTag));
                        break;
                    case DOUBLE:
                        root.addChild(new DoubleIFD(currentTag));
                        break;
                    case FLOAT:
                        root.addChild(new FloatIFD(currentTag));
                        break;
                    case SLONG:
                    case LONG:
                        root.addChild(new LongIFD(currentTag));
                        break;
                    case SRATIONAL:
                    case RATIONAL:
                        root.addChild(new RationalIFD(currentTag));
                        break;
                    case SSHORT:
                    case SHORT:
                        root.addChild(new ShortIFD(currentTag));
                        break;
                    default:
                        int ft =  currentTag.getFieldType().value;
                        currentTag = null;
                        throw new Exception(String.format("Missing fieldType in switch statement encountered: %d",ft));
                }
            }
            if (currentTag.getTagIdentifier() == TagIdentifier.Make) {
                make = new AsciiIFD(currentTag).toValueString().trim();
            }
            if (currentTag.getTagIdentifier() == TagIdentifier.SubIFDs
                    || currentTag.getTagIdentifier() == TagIdentifier.Exif_IFD) {
                (new LongIFD(currentTag)).getValues()
                        .forEach(x -> {
                            root.getLast().addChild(new IFDEntry());
                            try {
                                readIFDEntries(root.getLast().getLast(), x.intValue(),0);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
            }
            // TODO: Convert DNGPrivateData into an IFDStruct with IFDEntry children
            if (currentTag.getTagIdentifier() == TagIdentifier.DNGPrivateData && isAdobeNotes(currentTag)) {
                int mkNoteOffset = 0;
                int mkTagOffset;
                switch (make) {
                    case "Apple":
                        mkNoteOffset = currentTag.getOffset() + 47;
                        mkTagOffset = CameraOffset.Apple.value;
                        break;
                    case "Canon":
                        mkNoteOffset = currentTag.getOffset() + 20;
                        mkTagOffset = CameraOffset.Canon.value;
                        break;
                    case "SAMSUNG":
                        mkNoteOffset = currentTag.getOffset() + 32;
                        mkTagOffset = CameraOffset.SAMSUNG.value;
                        break;
                    case "PENTAX Corporation":
                        mkNoteOffset = currentTag.getOffset() + 38;
                        mkTagOffset = CameraOffset.PENTAX.value;
                        break;
                    case "OLYMPUS IMAGING CORP.":
                        mkNoteOffset = currentTag.getOffset() + 40;
                        mkTagOffset = CameraOffset.Olympus.value;
                        break;
                    case "SONY":
                        mkNoteOffset = currentTag.getOffset() + 16;
                        mkTagOffset = CameraOffset.SONY.value;
                    break;
                    default:
                        mkTagOffset = 0;
                        break;
                }
                root.getLast().addChild(new IFDEntry());
                if (App.verbose) System.out.printf("%sReading %s Maker Notes\r\n",StringUtils.verboseDateTime(),make);
                readIFDEntries(root.getLast().getLast(), mkNoteOffset,mkTagOffset);
            }

            ptr = ptr + 12;
        }
        return ByteBuffer.wrap(Arrays.copyOfRange(rawDNGBytes, ptr, ptr + 4)).order(imageHeader.byteOrder).getInt();
    }

    private boolean isAdobeNotes(IFDEntry currentTag) { 
        String adobe = new String(Arrays.copyOfRange(rawDNGBytes,currentTag.getOffset(),currentTag.getOffset()+5));
        return ((adobe.equals("Adobe")));
    }

    DNG(byte[] rawDNGBytes) throws Exception {
        this.rawDNGBytes = rawDNGBytes;
        imageHeader = new ImageHeader(rawDNGBytes);
    }
}
