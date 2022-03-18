package au.com.redmars;

import java.util.Arrays;

public class StringUtils {

	public static String formatObjectArray(byte[] bytes,int limit) {
        if (bytes.length > limit) {
            return String.format("%s ... %s",
            Arrays.toString(Arrays.copyOfRange(bytes,0,limit / 2)),
            Arrays.toString(Arrays.copyOfRange(bytes,bytes.length-limit / 2,bytes.length)));   
        } 
        else {
            return Arrays.toString(bytes);
        }
    }
	public static String formatObjectArray(Object[] values,int limit) {
        if (values.length > limit) {
            return String.format("%s ... %s",
            Arrays.toString(Arrays.copyOfRange(values,0,limit / 2)),
            Arrays.toString(Arrays.copyOfRange(values,values.length-limit / 2,values.length)));   
        } 
        else {
            return Arrays.toString(values);
        }
    }
	public static String formatObjectArray(double[] values,int limit) {
        if (values.length > limit) {
            return String.format("%s ... %s",
            Arrays.toString(Arrays.copyOfRange(values,0,limit / 2)),
            Arrays.toString(Arrays.copyOfRange(values,values.length-limit / 2,values.length)));   
        } 
        else {
            return Arrays.toString(values);
        }
    }
}
