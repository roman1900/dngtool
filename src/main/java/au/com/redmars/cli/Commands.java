package au.com.redmars.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Commands {
    List<Command>   commands;
    List<String>    nonCommandArgs;

    public Commands() {
        commands = new ArrayList<>();
        nonCommandArgs = new ArrayList<>();
    } 

    public void addCommand(Command command) {
        //TODO: Check for duplicate commands
        commands.add(command);
    }

    public Optional<Command> getCommand(String identifier) {
        return commands.stream().filter(c -> c.identifier.equals(identifier)).findFirst();
    }

    public List<Command> getCommands() {
        return commands;
    }

    public boolean removeCommand(String identifier) {
        Optional<Command> command = commands.stream().filter(c -> c.identifier.equals(identifier)).findFirst();
        return command.isPresent()?commands.remove(command.get()):false;
    }

    public void addNonCommandArg(String argument) {
        nonCommandArgs.add(argument);
    }

    public List<String> getNonCommandArgs() {
        return nonCommandArgs;
    }

}
