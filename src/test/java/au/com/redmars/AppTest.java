package au.com.redmars;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.List;

import org.junit.Test;

import au.com.redmars.cli.Command;
import au.com.redmars.cli.CommandLine;
import au.com.redmars.cli.Commands;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Test
    public void noArgumentsProvided()
    {
        String[] args = new String[0];
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
        assertTrue("No command line arguments provided",files.size()==0);    
    }
    @Test
    public void wrongFileExtensionProvided()
    {
        String[] args=new String[1];
        args[0] = "img-2345.jpg";
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
        
        files.forEach(f -> assertFalse("Wrong file extension",DNG.isDNG(f)) );
    }
    @Test
    public void LowerDNGFileExtensionProvided()
    {
        String[] args=new String[1];
        args[0] = "img-2345.dng";
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
        files.forEach(f -> assertTrue("Lowercase dng Filename supplied",DNG.isDNG(f)));
    }
    @Test
    public void UpperDNGFileExtensionProvided()
    {
        String[] args=new String[1];
        args[0] = "img-2345.DNG";
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
        files.forEach(f ->
        assertTrue("Uppercase DNG Filename supplied",DNG.isDNG(f)));
    }
    @Test
    public void LowerTIFFileExtensionProvided()
    {
        String[] args=new String[1];
        args[0] = "img-2345.tif";
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
        files.forEach(f ->
        assertTrue("Lowercase tif Filename supplied",DNG.isDNG(f)));
    }
    @Test
    public void UpperTIFFileExtensionProvided()
    {
        String[] args=new String[1];
        args[0] = "img-2345.TIF";
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
        files.forEach(f ->
        assertTrue("Uppercase TIF Filename supplied",DNG.isDNG(f)));
    }

}
