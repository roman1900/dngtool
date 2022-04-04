package au.com.redmars.cli;

import java.util.ArrayList;
import java.util.List;

public class Command {
    String          description;
    String          identifier;
    Boolean         requiresArg;
    Boolean         isRequired;
    int             argCount = 0;
    List<String>    values = new ArrayList<>();
    Boolean         isSet;        

    public Command(String identifier, String description) {
        this.identifier = identifier;
        this.description = description;
        this.requiresArg = false;
        this.isSet = false;
        this.isRequired = false;
        this.argCount = 0;
    }

    public Command(String identifier, String description, Boolean isRequired) {
        this.identifier = identifier;
        this.description = description;
        this.requiresArg = false;
        this.isSet = false;
        this.isRequired = isRequired;
        this.argCount = 0;
    }

    public void setArgs(int argCount,Boolean required) {
        this.argCount = argCount;
        this.requiresArg = required;
    }

    public Boolean getIsRequired() {
        return this.isRequired;
    }

    public void setIsRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }

    public List<String> getValues() {
        return values;
    }

    public Boolean getIsSet() {
        return isSet;
    }

    public void setIsSet(Boolean isSet) {
        this.isSet = isSet;
    }
}
