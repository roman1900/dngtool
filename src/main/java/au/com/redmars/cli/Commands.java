package au.com.redmars.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Commands {
    List<Command>   commands;
    List<String>    nonCommandArgs;

<<<<<<< HEAD
    public Commands() {
=======
    Commands() {
>>>>>>> 74c3347df4ef3afcf87bb204dfe5d90ab8299e4f
        commands = new ArrayList<>();
        nonCommandArgs = new ArrayList<>();
    } 

    public void addCommand(Command command) {
        //TODO: Check for duplicate commands
        commands.add(command);
    }

<<<<<<< HEAD
    public Optional<Command> getCommand(String identifier) {
        return commands.stream().filter(c -> c.identifier.equals(identifier)).findFirst();
=======
    public Optional<Command> getCommand(String name) {
        return commands.stream().filter(c -> c.name.equals(name)).findFirst();
>>>>>>> 74c3347df4ef3afcf87bb204dfe5d90ab8299e4f
    }

    public List<Command> getCommands() {
        return commands;
    }

<<<<<<< HEAD
    public boolean removeCommand(String identifier) {
        Optional<Command> command = commands.stream().filter(c -> c.identifier.equals(identifier)).findFirst();
=======
    public boolean removeCommand(String name) {
        Optional<Command> command = commands.stream().filter(c -> c.name.equals(name)).findFirst();
>>>>>>> 74c3347df4ef3afcf87bb204dfe5d90ab8299e4f
        return command.isPresent()?commands.remove(command.get()):false;
    }

    public void addNonCommandArg(String argument) {
        nonCommandArgs.add(argument);
    }
<<<<<<< HEAD

=======
    
>>>>>>> 74c3347df4ef3afcf87bb204dfe5d90ab8299e4f
    public List<String> getNonCommandArgs() {
        return nonCommandArgs;
    }

}
