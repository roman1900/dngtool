package au.com.redmars;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShortIFD extends IFDEntry implements IFDInterface<Integer>{
	
	public ShortIFD(byte[] rawDNGBytes, Integer byteLocation, ByteOrder byteOrder) throws Exception {
		super(rawDNGBytes, byteLocation, byteOrder);
		
	}
	
	public ShortIFD(IFDEntry ifdEntry) throws Exception {
		super(ifdEntry.rawDNGBytes, ifdEntry.byteLocation, ifdEntry.byteOrder);
	}

	@Override
	public List<Integer> getValues() {
		List<Integer> values = new ArrayList<>();
		for(int n = 0; n < count; ++n) {
			values.add(getUShort(Arrays.copyOfRange(value,n * 2,n * 2 + 2)));
		}
		return values;
	}
	
}
