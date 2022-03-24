package au.com.redmars;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class DNG {
	private ImageHeader imageHeader;
    private byte[] rawDNGBytes;
    
    
    public int readIFDEntries(IFDStruct root,Integer offset) throws Exception{
        if (offset == null)
            offset = imageHeader.ifdOffset.intValue();
        int ptr = offset + 2;
        int ifdEntryCount = ByteBuffer.wrap(Arrays.copyOfRange(rawDNGBytes, offset, offset + 2)).order(imageHeader.byteOrder).getChar();
        for(int i = 0; i < ifdEntryCount; ++i) {
            IFDEntry currentTag = new IFDEntry(rawDNGBytes,ptr,imageHeader.byteOrder);
            switch (currentTag.getFieldType()) {
                case ASCII:
                    root.addChild(new AsciiIFD(currentTag));
                    break;
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
                    throw new Exception(String.format("Missing fieldType in switch statement encountered: %d",currentTag.getFieldType()));
            }
            if (currentTag.getTagIdentifier() == TagIdentifier.SubIFDs || currentTag.getTagIdentifier() == TagIdentifier.Exif_IFD) {
                (new LongIFD(currentTag)).getValues()
                    .forEach(x -> {
                        root.getLast().addChild(new IFDEntry());
                        try {
                            readIFDEntries(root.getLast().getLast(),x.intValue());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
            }
            //TODO: Convert DNGPrivateData into an IFDStruct with IFDEntry children
            // if (currentTag.getTagIdentifier() == TagIdentifier.DNGPrivateData) {
			// 	Boolean foundnul = false;
			// 	int o = currentTag.getOffset();
			// 	while (!foundnul) {
			// 		if (rawDNGBytes[o] == 0) foundnul = true;
			// 		else o++;
			// 	}
			// 	System.out.printf("%d %s\r\n",currentTag.getOffset(),new String(Arrays.copyOfRange(rawDNGBytes,currentTag.getOffset(),o+1)));
			// }
			
            ptr = ptr + 12;
        }
        return ByteBuffer.wrap(Arrays.copyOfRange(rawDNGBytes, ptr, ptr+4)).order(imageHeader.byteOrder).getInt();
    }

	DNG(byte[] rawDNGBytes) throws Exception{
        this.rawDNGBytes = rawDNGBytes;
        imageHeader = new ImageHeader(rawDNGBytes);
	}
}
