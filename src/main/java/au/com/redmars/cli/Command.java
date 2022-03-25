package au.com.redmars.cli;

import java.util.List;

public class Command {
    String          description;
    String          name;
    String          identifier;
    Boolean         requiresArg;
    Boolean         hasArgs;
    Boolean         isRequired;
    int             argCount;
    List<String>    values;        

    Command(String name, String description) {
        this.name = name;
        this.description = description;
        this.requiresArg = false;
    }

    Command(String name, String description, Boolean requiresArg) {
        this.name = name;
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
