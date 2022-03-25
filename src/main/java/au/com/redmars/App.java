package au.com.redmars;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import au.com.redmars.cli.Command;
import au.com.redmars.cli.CommandLine;
import au.com.redmars.cli.Commands;
import au.com.redmars.exceptions.IncorrectFileNameException;
import au.com.redmars.ifd.IFDEntry;
import au.com.redmars.ifd.IFDStruct;
import au.com.redmars.ifd.TagIdentifier;

public class App 
{ 
    public static void doFile(String filePath) throws IncorrectFileNameException {
        if (!DNG.isDNG(filePath)) {
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
            
            
            Integer offset = null;
            do {
                IFDStruct root = new IFDStruct(new IFDEntry());
                offset = dng.readIFDEntries(root,null);
                System.out.println(root.toString());
                root.dumpXMP();
                root.getByTag(TagIdentifier.OriginalRawFileName).ifPresent(
                    x -> System.out.printf("%s\r\n",new String(x.getData().toValueString()))
                );
            } while (offset > 0);

        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
    }

    public static void main( String[] args ) 
    {
        Command command = new Command("r","Create a report");
        Commands commands = new Commands();
        commands.addCommand(command);
        try {
            CommandLine.parseCommandLine(commands, args);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        List<String> files = commands.getNonCommandArgs();

        if (files.size() < 1)
        {
            throw new IllegalArgumentException("No arguments provided");
        }
        
        files.forEach(f -> {
            try {
                doFile(f);
            } catch (IncorrectFileNameException i) {
                i.printStackTrace();
            }
        });
    }
}
