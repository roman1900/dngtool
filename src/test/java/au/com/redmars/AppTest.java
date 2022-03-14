package au.com.redmars;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import au.com.redmars.exceptions.IncorrectFileNameException;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Test
    public void wrongFileExtensionProvided()
    {
        String[] args=new String[1];
        args[0] = "img-2345.jpg";
        boolean wrongExt = false;
        try {
            App.main(args);
        } catch (IncorrectFileNameException e) {
            wrongExt = true;
        } finally {
            assertTrue("Incorrect Filename supplied",wrongExt);
        }
    }
    @Test
    public void DNGFileExtensionProvided()
    {
        String[] args=new String[1];
        args[0] = "img-2345.dng";
        boolean wrongExt = false;
        try {
            App.main(args);
        } catch (IncorrectFileNameException e) {
            wrongExt = true;
        } finally {
            assertTrue("Incorrect Filename supplied",!wrongExt);
        }
    }
    @Test
    public void TIFFileExtensionProvided()
    {
        String[] args=new String[1];
        args[0] = "img-2345.TIF";
        boolean wrongExt = false;
        try {
            App.main(args);
        } catch (IncorrectFileNameException e) {
            wrongExt = true;
        } finally {
            assertTrue("Incorrect Filename supplied",!wrongExt);
        }
    }
}
