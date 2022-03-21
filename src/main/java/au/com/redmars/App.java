package au.com.redmars;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import au.com.redmars.exceptions.IncorrectFileNameException;

public class App 
{
    public static void main( String[] args ) throws IncorrectFileNameException,IllegalArgumentException
    {
        if (Objects.isNull(args) || args.length <=0)
        {
            throw new IllegalArgumentException("No arguments provided");
        }
        String filePath = args[0];
        if (!filePath.toUpperCase().endsWith("DNG") & !filePath.toUpperCase().endsWith("TIF")) {
            throw new IncorrectFileNameException("dngtool follows strict DNG speficiation. File extension must be DNG or TIF");
        }
        byte[] rawDNGBytes = null;
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
            DNG dng = new DNG(rawDNGBytes);
            
            dng.dumpIFDs(null,"");
            
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
    }
}
