package au.com.redmars;

import au.com.redmars.exceptions.IncorrectFileNameException;

public class App 
{
    public static void main( String[] args ) throws IncorrectFileNameException
    {

        if (args.length <=0)
        {
            System.out.println("dngtool usage:");
            System.out.println("dngtool file");
        }
        String filePath = args[0];
        if (!filePath.toUpperCase().endsWith("DNG") & !filePath.toUpperCase().endsWith("TIF")) {
            throw new IncorrectFileNameException("dngtool follows strict DNG speficiation. File extension must be DNG or TIF");
        }
    }
}
