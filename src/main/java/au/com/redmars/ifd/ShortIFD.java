package au.com.redmars.ifd;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.com.redmars.StringUtils;

public class ShortIFD extends IFDEntry{
	
	public ShortIFD(byte[] rawDNGBytes, Integer byteLocation, ByteOrder byteOrder, Integer makeOffset) throws Exception {
		super(rawDNGBytes, byteLocation, byteOrder, makeOffset);
		
	}
	
	public ShortIFD(IFDEntry ifdEntry) throws Exception {
		super(ifdEntry.rawDNGBytes, ifdEntry.byteLocation, ifdEntry.byteOrder, ifdEntry.makeOffset);
	}

	@Override
	public List<Integer> getValues() {
		List<Integer> values = new ArrayList<>();
		for(int n = 0; n < count; ++n) {
			values.add(getUShort(Arrays.copyOfRange(value,n * 2,n * 2 + 2)));
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
