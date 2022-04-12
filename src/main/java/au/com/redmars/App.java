package au.com.redmars;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
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
    public static Boolean verbose = false;
    public static PrintStream out;
    private static DNG dng;
    private static IFDStruct root;
    private static String path;
    public static void doFile(String filePath,Boolean report) throws IncorrectFileNameException {

        if (!DNG.isDNG(filePath)) {
            throw new IncorrectFileNameException("dngtool follows strict DNG specification. File extension must be DNG or TIF");
        }
        byte[] rawDNGBytes = null;
        try {
            InputStream dngStream = new FileInputStream(filePath);
            rawDNGBytes = dngStream.readAllBytes();
            dngStream.close();
        } catch (IOException e) {
            if (verbose) {
               System.out.printf("%sUnable to read file %s\r\n",StringUtils.verboseDateTime(),filePath);
            }
        } catch (Exception e) {
            if (verbose) {
                System.out.printf("%sCannot read file %s\r\n",StringUtils.verboseDateTime(),filePath);
            }
        }
        if (verbose) System.out.printf("%sReading %s\r\n",StringUtils.verboseDateTime(),filePath);
        
        try {
            dng = new DNG(rawDNGBytes);
            
            
            Integer offset = null;
            do {
                root = new IFDStruct(new IFDEntry());
                offset = dng.readIFDEntries(root,offset,0);
                if (report) {
                    path = filePath.replace(Path.of(filePath).getFileName().toString(),"");
                    if (root.getByTag(TagIdentifier.DateTimeOriginal).isPresent()) {
                        path = path + root.getByTag(TagIdentifier.DateTimeOriginal).get().getData().toValueString().substring(0, 
                        root.getByTag(TagIdentifier.DateTimeOriginal).get().getData().toValueString().indexOf(":")) +"\\";
                    }
                    root.getByTag(TagIdentifier.OriginalRawFileName).ifPresentOrElse(
                    x -> out.printf("cp %s %s\r\n",filePath,path + new String(x.getData().toValueString()).replace(".CR2",".DNG").replace("\0","")),
                    () -> root.getByTag(TagIdentifier.CanonFileNumber).ifPresentOrElse(
                        x -> out.printf("cp %s %s\r\n",filePath,new String(x.getData().toValueString())),
                        () -> out.printf("cp %s %s\r\n",filePath,path+Path.of(filePath).getFileName().toString()))
                    
                    
                    );

                } 
                else {
                    root.dumpXMP(); 
                }
            } while (offset > 0);
            dng = null;
            root = null;
            System.gc();
        } catch (Exception e) {
            dng = null;
            root = null;
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
        command = new Command("d","Process all files in directory");
        command.setArgs(1,true);
        
        commands.addCommand(command);
        command = new Command("m","Move files to specified directory");
        commands.addCommand(command);
        command = new Command("c", "Generate a copy report");
        command.setArgs(1,false);
        commands.addCommand(command);
        command = new Command("v", "Verbose output");
        commands.addCommand(command);
        try {
            CommandLine.parseCommandLine(commands, args);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        verbose = commands.getCommand("v").getIsSet();
        report = commands.getCommand("c").getIsSet();
        if (report) {
            if (commands.getCommand("c").getValues().size() == 0) {
                out = new PrintStream(System.out);
            } else {
                try {
                    out = new PrintStream(commands.getCommand("c").getValues().get(0));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
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
        if (report) {
            out.flush();
            out.close();
        }
    }
}
