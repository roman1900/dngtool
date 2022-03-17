package au.com.redmars;

public class ByteUtils {
    public static long lsbByteToLong(byte[] bytes) {
        long result = 0;
        for (int i = 0; i < bytes.length; ++i) {
            result += (bytes[i] & 0xffL) << (i * 8);
        }
        return result;
    }
    public static long msbByteToLong(byte[] bytes) {
        byte[] result = new byte[bytes.length];
        int r = 0;
        for (int i = bytes.length -1; i >=0; --i) {
            result[r] = bytes[i];
            r++;
        }
        return lsbByteToLong(result);
    }
    public static int lsbByteToInt(byte[] bytes) {
        int result = 0;
        for (int i = 0; i < bytes.length; ++i) {
            result += (bytes[i] & 0xffL) << (i * 8);
        }
        return result;
    }
    public static int msbByteToInt(byte[] bytes) {
        byte[] result = new byte[bytes.length];
        int r = 0;
        for (int i = bytes.length -1; i >=0; --i) {
            result[r] = bytes[i];
            r++;
        }
        return lsbByteToInt(result);
    }
}
