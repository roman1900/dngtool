package au.com.redmars;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AsciiIFD extends IFDEntry implements IFDInterface<Character>{

	public AsciiIFD(byte[] rawDNGBytes, Integer byteLocation, ByteOrder byteOrder) throws Exception {
		super(rawDNGBytes, byteLocation, byteOrder);
		
	}
	public AsciiIFD(IFDEntry ifdEntry) throws Exception {
		super(ifdEntry.rawDNGBytes, ifdEntry.byteLocation, ifdEntry.byteOrder);
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
		return super.toString()+new String(this.getValues()
		                    .stream()
		                    .map(String::valueOf)
		                    .collect(Collectors.joining()));
	}
}
