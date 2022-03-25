package au.com.redmars.cli;

import java.text.ParseException;
import java.util.Optional;

public class CommandLine {
    public static void parseCommandLine(Commands commands,String args[]) throws ParseException{
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
                            //TODO: This does not respect requiresArgs
                            int c = 0;
                            while (!args[i+c].startsWith("-") && c < command.get().argCount) {
                                command.get().values.add(args[i+c+1]);
                                ++c;
                            }
                            i = i + c;
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
