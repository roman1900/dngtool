package au.com.redmars;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class ImageHeader {

    static ByteOrder byteOrder;
    Integer tiffIndentifier;
    Long ifdOffset;
    ImageHeader (byte[] rawHeader) throws Exception {
        switch (new String(new byte[]{rawHeader[0],rawHeader[1]})) {
            case "II":
                byteOrder = ByteOrder.LITTLE_ENDIAN;
                break;
            case "MM":
                byteOrder = ByteOrder.BIG_ENDIAN;
                break;
            default:
                throw new Exception(String.format("Not a valid DNG file: Unknown byte order encountered %s",new String(new byte[]{rawHeader[0],rawHeader[1]})));
        }
        tiffIndentifier = (int) ByteBuffer.wrap(new byte[] {rawHeader[2],rawHeader[3]}).order(byteOrder).getShort();
        // DNG allows for 64bit files. This is identified by 43 instead of 42.
        if (tiffIndentifier != 42 && tiffIndentifier != 43) {
            throw new Exception(String.format("Incorrect TIFF identifier: %d",tiffIndentifier)); 
        } 
        ifdOffset = getLong(Arrays.copyOfRange(rawHeader, 4, 8));
    }
    public Long getLong(byte[] bytes) {
        return ((Integer)ByteBuffer.wrap(bytes).order(byteOrder).getInt()).longValue();
    }
    public int getInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).order(byteOrder).getChar();
    }
    public float getFloat(byte[] bytes) {
        return ByteBuffer.wrap(bytes).order(byteOrder).getFloat();
    }
	public Double getDouble(byte[] bytes) {
		return ByteBuffer.wrap(bytes).order(byteOrder).getDouble();
	}
}
