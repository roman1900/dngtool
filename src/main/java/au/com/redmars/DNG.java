package au.com.redmars;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DNG {
	private ImageHeader imageHeader;
    private byte[] rawDNGBytes;
    
    //TODO: dumpIFDs is redundant replaced with IFDStruct.toString()
    public void dumpIFDs(Integer offset,String prefix) throws Exception{
            
			int ptr = 0;
            if (offset == null) {
                offset = imageHeader.ifdOffset.intValue();
            } 
			ptr = offset + 2;
            int ifdEntryCount = ByteBuffer.wrap(Arrays.copyOfRange(rawDNGBytes, offset, offset + 2)).order(imageHeader.byteOrder).getChar();
            
            System.out.printf("%sIFD entry count: %d\r\n",prefix,ifdEntryCount);

            for(int i = 0; i < ifdEntryCount; ++i) {
                IFDEntry currentTag = new IFDEntry(rawDNGBytes,ptr,imageHeader.byteOrder);
                System.out.printf("%s%s ",prefix,currentTag.toString());
                
                switch (currentTag.getFieldType()) {
                    case BYTE:
                        System.out.printf("%s\r\n",StringUtils.formatObjectList(new ByteIFD(currentTag).getValues(),10));
                        break;
                    case SHORT:
                        System.out.printf("%s\r\n",StringUtils.formatObjectList(new ShortIFD(currentTag).getValues(),10));
                        break;
                    case LONG:
                        System.out.printf("%s\r\n",StringUtils.formatObjectList(new LongIFD(currentTag).getValues(),10));
                        break;
                    case RATIONAL:
                    case SRATIONAL:
                        System.out.printf("%s\r\n",StringUtils.formatObjectList(new RationalIFD(currentTag).getValues(),10));
                        break;
                    case FLOAT:
                        System.out.printf("%s\r\n",StringUtils.formatObjectList(new FloatIFD(currentTag).getValues(),10));
                        break;
                    case DOUBLE:
                        System.out.printf("%s\r\n",StringUtils.formatObjectList(new DoubleIFD(currentTag).getValues(),10));
                        break;
                    case UNDEFINED:
                        //TODO: Implement individual undefined per TAG specs
                        System.out.printf("UNDEFINED DATA TAG SPECIFIC\r\n");
                        break;    
                    case ASCII:
                        System.out.println(new String((new AsciiIFD(currentTag)).getValues()
                                .stream()
                                .map(String::valueOf)
                                .collect(Collectors.joining())));
                        break;
                    default:
                        throw new Exception(String.format("Unhandled field type in switch statement: %s",currentTag.getFieldType().name()));
                }
               
                
                if (currentTag.getTagIdentifier() == TagIdentifier.Exif_IFD)
                {
                    System.out.printf("%s\tExif IFD\r\n",prefix);
                    dumpIFDs((new LongIFD(currentTag)).getValues().get(0).intValue(),String.format("%s\t",prefix));
                }
                if (currentTag.getTagIdentifier() == TagIdentifier.SubIFDs) {
                    if (currentTag.getIsValue()) {
                        dumpIFDs((new LongIFD(currentTag)).getValues().get(0).intValue(), String.format("%s\t",prefix));
                    } 
                    else {
                        offset = currentTag.getOffset();
                        for(int n = 0; n < currentTag.getCount(); ++n) {
                                System.out.printf("%s\tChild IFD %d\r\n",prefix,n);
                                dumpIFDs(currentTag.getLong(Arrays.copyOfRange(rawDNGBytes,offset,offset + 4)).intValue(),String.format("%s\t",prefix));
                                offset += 4;
                        }
                    }
                }
                if (currentTag.getTagIdentifier() == TagIdentifier.DNGPrivateData) {
                    Boolean foundnul = false;
                    int o = currentTag.getOffset();
                    while (!foundnul) {
                        if (rawDNGBytes[o] == 0) foundnul = true;
                        else o++;
                    }
                    System.out.printf("%d %s\r\n",currentTag.getOffset(),new String(Arrays.copyOfRange(rawDNGBytes,currentTag.getOffset(),o+1)));
                }
                if (currentTag.getTagIdentifier() == TagIdentifier.XMP) {
                    System.out.printf("%s\r\n",new String(Arrays.copyOfRange(rawDNGBytes,currentTag.getOffset(),currentTag.getOffset() +  currentTag.getCount())));
                }
                ptr = ptr + 12;
            } 
            if (ByteBuffer.wrap(Arrays.copyOfRange(rawDNGBytes, ptr, ptr+4)).order(imageHeader.byteOrder).getInt() > 0) {
                dumpIFDs((int)ByteBuffer.wrap(Arrays.copyOfRange(rawDNGBytes, ptr, ptr+4)).order(imageHeader.byteOrder).getInt(),String.format("%s\t",prefix));
            }
            
	}
    public void dumpXMP(IFDStruct root) throws Exception{
        root.getChildren().stream().filter(x -> x.getData().getTagIdentifier().name().equals("XMP"))
        .forEach(x -> {
            try {
                System.out.printf("%s\r\n",(new ByteIFD(x.getData())).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public int readIFDEntries(IFDStruct root,Integer offset) throws Exception{
        if (offset == null)
            offset = imageHeader.ifdOffset.intValue();
        int ptr = offset + 2;
        int ifdEntryCount = ByteBuffer.wrap(Arrays.copyOfRange(rawDNGBytes, offset, offset + 2)).order(imageHeader.byteOrder).getChar();
        for(int i = 0; i < ifdEntryCount; ++i) {
            IFDEntry currentTag = new IFDEntry(rawDNGBytes,ptr,imageHeader.byteOrder);
            root.addChild(currentTag);
            if (currentTag.getTagIdentifier() == TagIdentifier.SubIFDs) {
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
            ptr = ptr + 12;
        }
        return ByteBuffer.wrap(Arrays.copyOfRange(rawDNGBytes, ptr, ptr+4)).order(imageHeader.byteOrder).getInt();
    }

	DNG(byte[] rawDNGBytes) throws Exception{
        this.rawDNGBytes = rawDNGBytes;
        imageHeader = new ImageHeader(rawDNGBytes);
	}
}
