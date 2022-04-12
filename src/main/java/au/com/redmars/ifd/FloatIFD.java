package au.com.redmars.ifd;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.com.redmars.StringUtils;

public class FloatIFD extends IFDEntry{

	public FloatIFD(byte[] rawDNGBytes, Integer byteLocation, ByteOrder byteOrder, Integer makeOffset) throws Exception {
		super(rawDNGBytes, byteLocation, byteOrder, makeOffset);
		
	}
	public FloatIFD(IFDEntry ifdEntry) throws Exception {
		super(ifdEntry.rawDNGBytes, ifdEntry.byteLocation, ifdEntry.byteOrder, ifdEntry.makeOffset);
	}
	@Override
	public List<Float> getValues() {
		List<Float> values = new ArrayList<>();
		for(int n = 0; n < count; ++n) {
			values.add(getFloat(Arrays.copyOfRange(value,n * byteSize,n * byteSize + byteSize)));
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
