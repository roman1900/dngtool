package au.com.redmars.ifd;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import au.com.redmars.StringUtils;

public class ByteIFD extends IFDEntry{

	public ByteIFD(byte[] rawDNGBytes, Integer byteLocation, ByteOrder byteOrder) throws Exception {
		super(rawDNGBytes, byteLocation, byteOrder);
	}
	public ByteIFD(IFDEntry ifdEntry) throws Exception {
		super(ifdEntry.rawDNGBytes, ifdEntry.byteLocation, ifdEntry.byteOrder);
	}
	@Override
	public List<Byte> getValues() {
		List<Byte> values = new ArrayList<>();
		for (int i=0; i<value.length;++i) {
			values.add(value[i]);
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
