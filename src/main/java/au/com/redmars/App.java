package au.com.redmars;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Stream;

import au.com.redmars.cli.Command;
import au.com.redmars.cli.CommandLine;
import au.com.redmars.cli.Commands;
import au.com.redmars.exceptions.IncorrectFileNameException;
import au.com.redmars.ifd.IFDEntry;
import au.com.redmars.ifd.IFDStruct;
import au.com.redmars.ifd.TagIdentifier;

public class App 
{ 
    public static void doFile(String filePath,Boolean report) throws IncorrectFileNameException {
        if (!DNG.isDNG(filePath)) {
            throw new IncorrectFileNameException("dngtool follows strict DNG speficiation. File extension must be DNG or TIF");
        }
        byte[] rawDNGBytes = null;
        try {
            InputStream dngStream = new FileInputStream(filePath);
            rawDNGBytes = dngStream.readAllBytes();
            dngStream.close();
        } catch (IOException e) {
            if (report) {
                System.out.printf("%s - cannot read file\r\n",filePath);
            } else {
                System.out.printf("Unable to read file %s\r\n",filePath);
            }
        } catch (Exception e) {
            if (report) {
                System.out.printf("%s - cannot read file\r\n",filePath);
            } else {
                System.out.printf("%s\r\n",e.getMessage());
            }
        }
        try {
            DNG dng = new DNG(rawDNGBytes);
            
            
            Integer offset = null;
            do {
                IFDStruct root = new IFDStruct(new IFDEntry());
                offset = dng.readIFDEntries(root,null);
                System.out.println(root.toString());
                if (report) {
                    root.getByTag(TagIdentifier.OriginalRawFileName).ifPresent(
                    x -> System.out.printf("%s - %s\r\n",new String(x.getData().toValueString()).replace(".CR2",".DNG"))
                    );
                } 
                else {
                    root.dumpXMP(); 
                }
            } while (offset > 0);

        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
    }

    public static void doDirectory(String directory,Boolean report) {
        try (Stream<Path> walk = Files.walk(Paths.get(directory))) {
            walk.filter(Files::isRegularFile)
                .forEach(f -> {try {doFile(f.toString(),report);} catch (IncorrectFileNameException e) {e.printStackTrace();}});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main( String[] args ) 
    {
        final Boolean report;
        Commands commands = new Commands();
        Command command = new Command("r","Recurse subdirectories requires -d");
        commands.addCommand(command);
        command = new Command("d","Process all files in directory",true);
        command.setArgs(1,true);
        
        commands.addCommand(command);
        command = new Command("m","Move files to specified directory");
        commands.addCommand(command);
        command = new Command("r", "Generate a report");
        
        try {
            CommandLine.parseCommandLine(commands, args);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        report = commands.getCommand("r").getIsSet();
        if (commands.getCommand("m").getIsSet()){

        }
        if (commands.getCommand("d").getIsSet()){
            
            commands.getCommand("d").getValues().stream().forEach(d -> doDirectory(d,report));

            

            

        }
        List<String> files = commands.getNonCommandArgs();

        if (files.size() < 1)
        {
            throw new IllegalArgumentException("No arguments provided");
        }
        
        files.forEach(f -> {
            try {
                doFile(f,false);
            } catch (IncorrectFileNameException i) {
                i.printStackTrace();
            }
        });
    }
}
