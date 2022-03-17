package au.com.redmars;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;

import au.com.redmars.exceptions.IncorrectFileNameException;

public class App 
{
    public static void main( String[] args ) throws IncorrectFileNameException,IllegalArgumentException
    {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        if (Objects.isNull(args) || args.length <=0)
        {
            throw new IllegalArgumentException("No arguments provided");
        }
        String filePath = args[0];
        if (!filePath.toUpperCase().endsWith("DNG") & !filePath.toUpperCase().endsWith("TIF")) {
            throw new IncorrectFileNameException("dngtool follows strict DNG speficiation. File extension must be DNG or TIF");
        }
        byte[] rawDNGBytes = null;
        ImageHeader imageHeader;
        try {
            InputStream dngStream = new FileInputStream(filePath);
            rawDNGBytes = dngStream.readAllBytes();
            dngStream.close();
        } catch (IOException e) {
            System.out.printf("Unable to read file %s\r\n",filePath);
        } catch (Exception e) {
            System.out.printf("%s\r\n",e.getMessage());
        }
        try {
            int ptr = 0;
            long nextptr = 0;
            imageHeader = new ImageHeader(Arrays.copyOfRange(rawDNGBytes, 0, 8));
            System.out.println(imageHeader.byteOrder);
            System.out.println(imageHeader.tiffIndentifier);
            System.out.println(imageHeader.ifdOffset);
            byte[] ifdEntryBytes = Arrays.copyOfRange(rawDNGBytes, 
                                            imageHeader.ifdOffset.intValue(),
                                            imageHeader.ifdOffset.intValue() + 2);
            ptr = imageHeader.ifdOffset.intValue() + 2;
            int ifdEntryCount = imageHeader.getInt(ifdEntryBytes);
            System.out.printf("IFD 0 entry count: %d\r\n",ifdEntryCount);

            for(int i = 0; i < ifdEntryCount; ++i) {
                IFDEntry currentTag = new IFDEntry(Arrays.copyOfRange(rawDNGBytes, ptr, ptr+12),imageHeader);
                System.out.println(currentTag.toString());
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
                    int offset;
                    Long[] values;
                    switch(currentTag.getFieldType()) {
                        case BYTE:
                        case SHORT:
                            byte[] subarray = Arrays.copyOfRange(rawDNGBytes,currentTag.getOffset(),currentTag.getOffset() +  currentTag.getCount());
                            if (subarray.length > 10) {
                                System.out.printf("%s ... %s\r\n",
                                Arrays.toString(Arrays.copyOfRange(subarray,0,5)),
                                Arrays.toString(Arrays.copyOfRange(subarray,subarray.length-5,subarray.length)));   
                            } 
                            else {
                                System.out.println(Arrays.toString(subarray));
                            }
                            break;
                        case LONG:
                            values = new Long[currentTag.getCount()];
                            offset = currentTag.getOffset();
                            for(int n = 0; n < values.length; ++n) {
                                values[n] = imageHeader.getLong(Arrays.copyOfRange(rawDNGBytes,offset,offset + 4));
                                offset += 4;
                            }
                            if (values.length > 10) {
                                System.out.printf("%s ... %s",
                                    Arrays.toString(Arrays.copyOfRange(values,0,5)),
                                    Arrays.toString(Arrays.copyOfRange(values,values.length-5,values.length)));     
                            } else {
                                System.out.println(Arrays.toString(values));
                            }
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
                                result[e] = values[e*2] / (double)values[e*2+1];
                            } 
                            System.out.println(Arrays.toString(result));
                            break;
                        case ASCII:
                            System.out.println(new String(Arrays.copyOfRange(rawDNGBytes,currentTag.getOffset(),currentTag.getOffset() + currentTag.getCount())));
                            break;
                        default: 
                            throw new Exception(String.format("Unhandled field type in switch statement: %s",currentTag.getFieldType().name()));
                    }
                }
                if (currentTag.getTagIdentifier() == TagIdentifier.Exif_IFD)
                {
                   nextptr = imageHeader.getLong(currentTag.getValue());
                }
                ptr = ptr + 12;
            } 
            long nextIFD=imageHeader.getInt(Arrays.copyOfRange(rawDNGBytes, ptr, ptr+4));
            System.out.printf("%d Next IFD location\r\n",nextIFD);

            //Process EXIF IFD
            ifdEntryBytes = Arrays.copyOfRange(rawDNGBytes, 
                                            (int)nextptr,
                                            (int)nextptr + 2);
            ifdEntryCount = imageHeader.getInt(ifdEntryBytes);
            System.out.printf("Exif IFD entry count: %d\r\n",ifdEntryCount);
            ptr = (int)nextptr + 2;
            for(int i = 0; i < ifdEntryCount; ++i) {
                IFDEntry currentTag = new IFDEntry(Arrays.copyOfRange(rawDNGBytes, ptr, ptr+12),imageHeader);
                System.out.println(currentTag.toString());
                if (currentTag.getIsValue()) {
                    switch (currentTag.getFieldType()) {
                        case BYTE:
                            System.out.println(currentTag.getValue());
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
                }
                if (currentTag.getTagIdentifier() == TagIdentifier.Exif_IFD)
                {
                   nextptr = imageHeader.getLong(currentTag.getValue());
                }
                ptr = ptr + 12;
            } 
            nextIFD=imageHeader.getInt(Arrays.copyOfRange(rawDNGBytes, ptr, ptr+4));
            System.out.printf("%d Next IFD location\r\n",nextIFD);

        } catch (Exception e) {
            System.out.printf("File:%s Line:%d %s\r\n",e.getStackTrace()[0].getFileName(),e.getStackTrace()[0].getLineNumber(), e.getMessage());
        }
    }
}
