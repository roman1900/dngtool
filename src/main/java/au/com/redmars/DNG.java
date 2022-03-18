package au.com.redmars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DNG {
	private ImageHeader imageHeader;
    private byte[] rawDNGBytes;
    
	public void dumpIFDs(Integer offset,String prefix) throws Exception{
            
			int ptr = 0;
            List<Integer> subIFDs = new ArrayList<>();
            if (offset == null) {
                offset = imageHeader.ifdOffset.intValue();
            } 
			ptr = offset + 2;
            int ifdEntryCount = imageHeader.getInt(Arrays.copyOfRange(rawDNGBytes, offset, offset + 2));
            
            System.out.printf("%sIFD entry count: %d\r\n",prefix,ifdEntryCount);

            for(int i = 0; i < ifdEntryCount; ++i) {
                IFDEntry currentTag = new IFDEntry(Arrays.copyOfRange(rawDNGBytes, ptr, ptr+12),imageHeader);
                System.out.printf("%s%s ",prefix,currentTag.toString());
                if (currentTag.getIsValue()) {
                    switch (currentTag.getFieldType()) {
                        case BYTE:
                            System.out.println(Arrays.toString(currentTag.getValue()));
                            break;
                        case SHORT:
                            System.out.println(imageHeader.getInt(currentTag.getValue()));
                            break;
                        case LONG:
                            System.out.println(imageHeader.getLong(currentTag.getValue()));
                            break;
                        case UNDEFINED:
                        case ASCII:
                            System.out.println(new String(currentTag.getValue()));
                            break;
                        default:
                            throw new Exception(String.format("Unhandled field type in switch statement: %s",currentTag.getFieldType().name()));
                    }
                } else {
                    //int offset;
                    Object[] values;
                    switch(currentTag.getFieldType()) {
                        case BYTE:
                        case SHORT:
                            System.out.printf("%s%s\r\n",prefix,StringUtils.formatObjectArray(Arrays.copyOfRange(rawDNGBytes,currentTag.getOffset(),currentTag.getOffset() +  currentTag.getCount()), 10));
                            break;
                        case LONG:
                            values = new Long[currentTag.getCount()];
                            offset = currentTag.getOffset();
                            for(int n = 0; n < values.length; ++n) {
                                values[n] = imageHeader.getLong(Arrays.copyOfRange(rawDNGBytes,offset,offset + 4));
                                offset += 4;
                            }
                            System.out.printf("%s%s\r\n",prefix,StringUtils.formatObjectArray(values,10));
                            break;
                        case RATIONAL:
                        case SRATIONAL:
                            values = new Long[currentTag.getCount()*2];
                            offset = currentTag.getOffset();
                            for(int n = 0; n < values.length; ++n) {
                                values[n] = imageHeader.getLong(Arrays.copyOfRange(rawDNGBytes,offset,offset + 4));
                                offset += 4;
                            }
                            double[] result = new double[values.length/2];
                            for(int e = 0; e < result.length; ++e) {
                                result[e] = ((Long)values[e*2]).doubleValue() / ((Long)values[e*2+1]).doubleValue();
                            } 
                            System.out.printf("%s%s\r\n",prefix,StringUtils.formatObjectArray(result,10));
                            break;
                        case FLOAT:
                            values = new Float[currentTag.getCount()];
                            offset = currentTag.getOffset();
                            for(int n = 0; n < values.length; ++n) {
                                values[n] = imageHeader.getFloat(Arrays.copyOfRange(rawDNGBytes,offset,offset + 4));
                                offset += 4;
                            }
                            System.out.printf("%s%s\r\n",prefix,StringUtils.formatObjectArray(values,10));
                            break;
                        case DOUBLE:
                            values = new Double[currentTag.getCount()];
                            offset = currentTag.getOffset();
                            for(int n = 0; n < values.length; ++n) {
                                values[n] = imageHeader.getDouble(Arrays.copyOfRange(rawDNGBytes,offset,offset + 8));
                                offset += 8;
                            }
                            System.out.printf("%s%s\r\n",prefix,StringUtils.formatObjectArray(values,10));
                            break;
                        case UNDEFINED:
                            System.out.printf("%sUNDEFINED DATA TAG SPECIFIC\r\n",prefix);
                            break;
                        case ASCII:
                            System.out.printf("%s%s\r\n",prefix,new String(Arrays.copyOfRange(rawDNGBytes,currentTag.getOffset(),currentTag.getOffset() + currentTag.getCount())));
                            break;
                        default: 
                            throw new Exception(String.format("Unhandled field type in switch statement: %s",currentTag.getFieldType().name()));
                    }
                }
                if (currentTag.getTagIdentifier() == TagIdentifier.Exif_IFD)
                {
                    System.out.printf("%s\tExif IFD\r\n",prefix);
                    dumpIFDs(imageHeader.getLong(currentTag.getValue()).intValue(),String.format("%s\t",prefix));
                }
                if (currentTag.getTagIdentifier() == TagIdentifier.SubIFDs) {
                    if (currentTag.getIsValue()) {
                        dumpIFDs(imageHeader.getLong(currentTag.getValue()).intValue(), String.format("%s\t",prefix));
                    } 
                    else {
                        Long[] values = new Long[currentTag.getCount()];
                        offset = currentTag.getOffset();
                        for(int n = 0; n < currentTag.getCount(); ++n) {
                                System.out.printf("%s\tChild IFD %d\r\n",prefix,n);
                                dumpIFDs(imageHeader.getLong(Arrays.copyOfRange(rawDNGBytes,offset,offset + 4)).intValue(),String.format("%s\t",prefix));
                                offset += 4;
                        }
                    }
                }
                ptr = ptr + 12;
            } 
            if (imageHeader.getInt(Arrays.copyOfRange(rawDNGBytes, ptr, ptr+4)) > 0) {
                dumpIFDs(imageHeader.getInt(Arrays.copyOfRange(rawDNGBytes, ptr, ptr+4)),String.format("%s\t",prefix));
            }
            
	}

	DNG(byte[] rawDNGBytes) throws Exception{
        this.rawDNGBytes = rawDNGBytes;
        imageHeader = new ImageHeader(Arrays.copyOfRange(rawDNGBytes, 0, 8));
        System.out.println(imageHeader.tiffIndentifier);
        System.out.println(imageHeader.ifdOffset);
	}
}
