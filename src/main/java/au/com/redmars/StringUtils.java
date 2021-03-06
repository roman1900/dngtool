package au.com.redmars;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class StringUtils {

    public static <T> String formatObjectList(List<T> items,int limit) {
        if (items.size() > limit) {
            return String.format("%s ... %s",
                items.subList(0, 5).toString(),
                items.subList(items.size()-5,items.size())
            );
        } 
        else {
            return items.toString();
        }
    }

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
    public static String verboseDateTime() {
        return String.format("[%tFT%<tT]\t",LocalDateTime.now());
    }
}
