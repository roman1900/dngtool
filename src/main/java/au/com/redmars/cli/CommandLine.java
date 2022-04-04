package au.com.redmars.cli;

import java.text.ParseException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.joining;

public class CommandLine {
    public static void parseCommandLine(Commands commands,String args[]) throws ParseException{
        int i = 0;
        while (i < args.length){
            if (args[i].startsWith("-")) {
                String commandId = args[i].substring(1);
                Command command = commands.getCommand(commandId);
                if (command != null) {
                    command.isSet = true;
                    if (command.argCount > 0) {
                        ++i;
                        if(args[i].startsWith("-")) {
                            if (command.requiresArg) {
                                throw new ParseException(String.format("%s requires an argument",args[i-1]),i);
                            } else {
                                --i;
                            }
                        } else {
                            //TODO: This does not respect requiresArgs
                            int c = 0;
                            while (i+c < args.length && !args[i+c].startsWith("-") && c < command.argCount) {
                                command.values.add(args[i+c]);
                                ++c;
                            }
                            i = i + c;
                        }
                    }
                } else {
                    throw new ParseException(String.format("unknown argument encountered %s",args[i]), i);    
                }
            } else {
                commands.addNonCommandArg(args[i]);
            }
            ++i;
        }
        if (commands.getCommands().stream().anyMatch(x ->  x.isRequired && !x.isSet)) {
            List<Command> notSetandRequired = commands.getCommands().stream().filter(x -> x.isRequired && !x.isSet).collect(toList());
            throw new ParseException(String.format("These arguments are not provided and are required %s", notSetandRequired.stream().map(x -> "-"+x.identifier+" ").collect(joining())),0);
        }
        
    }
}
