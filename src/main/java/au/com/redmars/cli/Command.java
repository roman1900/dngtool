package au.com.redmars.cli;

import java.util.List;

public class Command {
    String          description;
    String          identifier;
    Boolean         requiresArg;
    Boolean         hasArgs;
    Boolean         isRequired;
    int             argCount;
    List<String>    values;        

    public Command(String identifier, String description) {
        this.identifier = identifier;
        this.description = description;
        this.requiresArg = false;
    }

    public Command(String identifier, String description, Boolean requiresArg) {
        this.identifier = identifier;
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
