package au.com.redmars;

import java.util.Arrays;

public class ImageHeader {

    boolean lsb;
    String byteOrder;
    Integer tiffIndentifier;
    Long ifdOffset;
    ImageHeader (byte[] rawHeader) throws Exception {
        byteOrder = new String(new byte[]{rawHeader[0],rawHeader[1]});
        if (byteOrder.equals("II")){
            lsb = true;
        } else if (byteOrder.equals("MM")){
            lsb = false;
        } else {
            throw new Exception(String.format("Not a valid DNG file: ByteOrder given %s",byteOrder));
        }
        tiffIndentifier = lsb ? (int)rawHeader[2] : (int)rawHeader[3];
        // DNG allows for 64bit files. This is identified by 43 instead of 42.
        if (tiffIndentifier != 42 && tiffIndentifier != 43) {
            throw new Exception(String.format("Incorrect TIFF identifier: %d",tiffIndentifier)); 
        } 
        ifdOffset = getLong(Arrays.copyOfRange(rawHeader, 4, 7));
    }
    public long getLong(byte[] bytes) {
        return lsb ? ByteUtils.lsbByteToLong(bytes) 
        : ByteUtils.msbByteToLong(bytes);
    }
    public int getInt(byte[] bytes) {
        return lsb ? ByteUtils.lsbByteToInt(bytes) 
        : ByteUtils.msbByteToInt(bytes);
    }
}
