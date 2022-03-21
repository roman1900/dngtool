package au.com.redmars;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LongIFD extends IFDEntry implements IFDInterface<Long>{

	public LongIFD(byte[] rawDNGBytes, Integer byteLocation, ByteOrder byteOrder) throws Exception {
		super(rawDNGBytes, byteLocation, byteOrder);
	}
	public LongIFD(IFDEntry ifdEntry) throws Exception {
		super(ifdEntry.rawDNGBytes, ifdEntry.byteLocation, ifdEntry.byteOrder);
	}
	@Override
	public List<Long> getValues() {
		List<Long> values = new ArrayList<>();
		for(int n = 0; n < count; ++n) {
			values.add(getLong(Arrays.copyOfRange(value,n * byteSize,n * byteSize + byteSize)));
		}
		return values;
	}

	
}
