package au.com.redmars.ifd;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.com.redmars.StringUtils;

public class DoubleIFD extends IFDEntry{

	public DoubleIFD(byte[] rawDNGBytes, Integer byteLocation, ByteOrder byteOrder) throws Exception {
		super(rawDNGBytes, byteLocation, byteOrder);
		
	}
	public DoubleIFD(IFDEntry ifdEntry) throws Exception {
		super(ifdEntry.rawDNGBytes, ifdEntry.byteLocation, ifdEntry.byteOrder);
	}
	@Override
	public List<Double> getValues() {
		List<Double> values = new ArrayList<>();
		for(int n = 0; n < count; ++n) {
			values.add(getDouble(Arrays.copyOfRange(value,n * byteSize,n * byteSize + byteSize)));
		}
		return values;
	}
	@Override
	public String toString() {
		return super.toString()+toValueString();
	}
	public String toValueString() {
		return StringUtils.formatObjectList(getValues(), 10);
	}
	
}
