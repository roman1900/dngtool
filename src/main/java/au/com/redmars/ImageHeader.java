package au.com.redmars;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class ImageHeader {

    ByteOrder byteOrder;
    Integer tiffIndentifier;
    Long ifdOffset;

    ImageHeader (byte[] rawDNGBytes) throws Exception {
        byte[] rawHeader = Arrays.copyOfRange(rawDNGBytes, 0, 8);
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
        ifdOffset = ((Integer)ByteBuffer.wrap(Arrays.copyOfRange(rawHeader, 4, 8)).order(byteOrder).getInt()).longValue();
    }
    
}
