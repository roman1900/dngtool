package au.com.redmars.ifd;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class UndefinedIFD extends IFDEntry{

    public UndefinedIFD(byte[] rawDNGBytes, Integer byteLocation, ByteOrder byteOrder) throws Exception {
		super(rawDNGBytes, byteLocation, byteOrder);
		
	}
	public UndefinedIFD(IFDEntry ifdEntry) throws Exception {
		super(ifdEntry.rawDNGBytes, ifdEntry.byteLocation, ifdEntry.byteOrder);
	}
	@Override
	public List<Byte> getValues() {
		List<Byte> values = new ArrayList<>();
		for (int i=0; i<value.length;++i) {
			values.add((byte)value[i]);
		}
		return values;
	}
	@Override
	public String toString() {
		return super.toString()+toValueString();
	}
	public String toValueString() {
		return "UNDEFINED DATA: TAG SPECIFIC";
	}
}
