package au.com.redmars;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            imageHeader = new ImageHeader(Arrays.copyOfRange(rawDNGBytes, 0, 8));
            System.out.println(imageHeader.byteOrder);
            System.out.println(imageHeader.tiffIndentifier);
            System.out.println(imageHeader.ifdOffset);
            byte[] ifdEntryBytes = Arrays.copyOfRange(rawDNGBytes, 
                                            imageHeader.ifdOffset.intValue(),
                                            imageHeader.ifdOffset.intValue() + 2);
            ptr = imageHeader.ifdOffset.intValue() + 2;
            int ifdEntryCount = imageHeader.lsb ? ByteUtils.lsbByteToInt(ifdEntryBytes) 
                                : ByteUtils.msbByteToInt(ifdEntryBytes);
            System.out.println(ifdEntryCount);

            for(int i = 0; i < ifdEntryCount; ++i) {
                int tag = imageHeader.getInt(Arrays.copyOfRange(rawDNGBytes, ptr, ptr+2));
                int type = imageHeader.getInt(Arrays.copyOfRange(rawDNGBytes, ptr+2 , ptr+4));
                int count = imageHeader.getInt(Arrays.copyOfRange(rawDNGBytes, ptr+4 , ptr+7));
                
                System.out.printf("Entry: %d TAG: %d Type: %d\r\n",i+1,tag,type); 
                if(Tag.valueOfTag(tag)!=null)
                    System.out.println(Tag.valueOfTag(tag));
                else throw new Exception(String.format("Unknown TAG encountered: %d",tag));
                if(Type.valueOfTag(type)!=null)
                    System.out.println(Type.valueOfTag(type));
                else throw new Exception(String.format("Unknown TAG encountered: %d",type));
                System.out.println(count);
                
                ptr = ptr + 12;
            } 
            long nextIFD=imageHeader.getInt(Arrays.copyOfRange(rawDNGBytes, ptr, ptr+4));
            System.out.printf("%d Next IFD location\r\n",nextIFD);
        } catch (Exception e) {
            System.out.printf("%s\r\n",e.getMessage());
        }
    }
}
