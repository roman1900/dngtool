package au.com.redmars.cli;

import java.util.List;

public class Command {
    String          description;
<<<<<<< HEAD
=======
    String          name;
>>>>>>> 74c3347df4ef3afcf87bb204dfe5d90ab8299e4f
    String          identifier;
    Boolean         requiresArg;
    Boolean         hasArgs;
    Boolean         isRequired;
    int             argCount;
    List<String>    values;        

<<<<<<< HEAD
    public Command(String identifier, String description) {
        this.identifier = identifier;
=======
    Command(String name, String description) {
        this.name = name;
>>>>>>> 74c3347df4ef3afcf87bb204dfe5d90ab8299e4f
        this.description = description;
        this.requiresArg = false;
    }

<<<<<<< HEAD
    public Command(String identifier, String description, Boolean requiresArg) {
        this.identifier = identifier;
=======
    Command(String name, String description, Boolean requiresArg) {
        this.name = name;
>>>>>>> 74c3347df4ef3afcf87bb204dfe5d90ab8299e4f
        this.description = description;
        this.requiresArg = requiresArg;
    }

    public void setArgs(int argCount) {
        this.argCount = argCount;
    }

    public void setRequiresArg(boolean isRequired) {
        this.isRequired = isRequired;
    }

    public List<String> getValues() {
        return values;
    }
}
