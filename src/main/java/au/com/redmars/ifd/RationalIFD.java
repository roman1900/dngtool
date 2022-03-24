package au.com.redmars.ifd;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.com.redmars.StringUtils;

public class RationalIFD extends IFDEntry{

	public RationalIFD(byte[] rawDNGBytes, Integer byteLocation, ByteOrder byteOrder) throws Exception {
		super(rawDNGBytes, byteLocation, byteOrder);
		
	}
	public RationalIFD(IFDEntry ifdEntry) throws Exception {
		super(ifdEntry.rawDNGBytes, ifdEntry.byteLocation, ifdEntry.byteOrder);
	}
	@Override
	public List<Double> getValues() {
		List<Long> values = new ArrayList<>();
        for(int n = 0; n < count * 2; ++n) {
			values.add(getLong(Arrays.copyOfRange(value,n * 4,n * 4 + 4)));
		}
        List<Double> result = new ArrayList<>();
		for(int e = 0; e < values.size(); e +=2) {
			result.add(((Long)values.get(e)).doubleValue() / ((Long)values.get(e+1)).doubleValue());
		} 
		return result;
	}
	@Override
	public String toString() {
		return super.toString()+toValueString();
	}
	public String toValueString() {
		return StringUtils.formatObjectList(getValues(), 10);
	}
}
