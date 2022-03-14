package au.com.redmars.exceptions;

public class IncorrectFileNameException extends Exception{
    public IncorrectFileNameException(String errorMessage) {
        super(errorMessage);
    }
}
