package au.com.redmars.cli;

import java.text.ParseException;
import java.util.Optional;

public class CommandLine {
<<<<<<< HEAD
    public static void parseCommandLine(Commands commands,String args[]) throws ParseException{
=======
    public void parseCommandLine(Commands commands,String args[]) throws ParseException{
>>>>>>> 74c3347df4ef3afcf87bb204dfe5d90ab8299e4f
        int i = 0;
        while (i < args.length){
            if (args[i].startsWith("-")) {
                String commandId = args[i].substring(1);
                Optional<Command> command = commands.getCommand(commandId);
                if (command.isPresent()) {
                    if (command.get().hasArgs) {
                        ++i;
                        if(args[i].startsWith("-")) {
                            if (command.get().isRequired) {
                                throw new ParseException("Argument is required",i);
                            } else {
                                --i;
                            }
                        } else {
                            //TODO: Save Arguments to command
                            // Iterate argCount times to save values
                            // If isRequired expect all argCount otherwise allow
                            // 1 to argCount values
                        }
                    }
                } else {
                    commands.addNonCommandArg(args[i]);    
                }
            } else {
                commands.addNonCommandArg(args[i]);
            }
            ++i;
        }
    }
}
