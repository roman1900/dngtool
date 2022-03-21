package au.com.redmars;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FloatIFD extends IFDEntry implements IFDInterface<Float>{

	public FloatIFD(byte[] rawDNGBytes, Integer byteLocation, ByteOrder byteOrder) throws Exception {
		super(rawDNGBytes, byteLocation, byteOrder);
		
	}
	public FloatIFD(IFDEntry ifdEntry) throws Exception {
		super(ifdEntry.rawDNGBytes, ifdEntry.byteLocation, ifdEntry.byteOrder);
	}
	@Override
	public List<Float> getValues() {
		List<Float> values = new ArrayList<>();
		for(int n = 0; n < count; ++n) {
			values.add(getFloat(Arrays.copyOfRange(value,n * byteSize,n * byteSize + byteSize)));
		}
		return values;
	}
	
}
