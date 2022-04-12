package au.com.redmars.ifd;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AsciiIFD extends IFDEntry{

	public AsciiIFD(byte[] rawDNGBytes, Integer byteLocation, ByteOrder byteOrder, Integer makeOffset) throws Exception {
		super(rawDNGBytes, byteLocation, byteOrder, makeOffset);
		
	}
	public AsciiIFD(IFDEntry ifdEntry) throws Exception {
		super(ifdEntry.rawDNGBytes, ifdEntry.byteLocation, ifdEntry.byteOrder, ifdEntry.makeOffset);
	}
	@Override
	public List<Character> getValues() {
		List<Character> values = new ArrayList<>();
		for (int i=0; i<value.length;++i) {
			values.add((char)value[i]);
		}
		return values;
	}
	@Override
	public String toString() {
		return super.toString()+toValueString();
	}
	
	public String toValueString() {
		return new String(this.getValues()
		.stream()
		.map(String::valueOf)
		.collect(Collectors.joining()));
	}
}
