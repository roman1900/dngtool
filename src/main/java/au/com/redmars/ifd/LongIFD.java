package au.com.redmars.ifd;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.com.redmars.StringUtils;

public class LongIFD extends IFDEntry{

	public LongIFD(byte[] rawDNGBytes, Integer byteLocation, ByteOrder byteOrder, Integer makeOffset) throws Exception {
		super(rawDNGBytes, byteLocation, byteOrder, makeOffset);
	}
	public LongIFD(IFDEntry ifdEntry) throws Exception {
		super(ifdEntry.rawDNGBytes, ifdEntry.byteLocation, ifdEntry.byteOrder, ifdEntry.makeOffset);
	}
	@Override
	public List<Long> getValues() {
		List<Long> values = new ArrayList<>();
		for(int n = 0; n < count; ++n) {
			values.add(getLong(Arrays.copyOfRange(value,n * byteSize,n * byteSize + byteSize)));
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
