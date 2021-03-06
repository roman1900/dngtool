package au.com.redmars.ifd;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;

import au.com.redmars.App;
import au.com.redmars.StringUtils;

public class IFDEntry {

	private TagIdentifier tagIdentifier;
	private FieldType fieldType;
	protected ByteOrder byteOrder;
	protected Integer	count;
	protected Integer byteSize;
	protected Integer makeOffset;
	private Boolean isValue;
	protected Integer	offset;
	protected byte[]	value;
	protected byte[]	rawDNGBytes;
	protected Integer	byteLocation;
	
	

	public TagIdentifier getTagIdentifier(){
		return tagIdentifier;
	}
	
	public void setTagIdentifier(TagIdentifier tagIdentifier) {
		this.tagIdentifier = tagIdentifier;
	}

	public FieldType getFieldType() {
		return fieldType;
	}

	public void setFieldType(FieldType fieldType){
		this.fieldType = fieldType;
	}

	protected Boolean getIsValue() {
		return isValue;
	}

	public Integer getCount() {
		return count;
	}

	public Integer getOffset() {
		if (isValue)
			return null;
		else
			return offset;
	}

	public String toString() {
		return String.format("%-25s\t%-10s\tCount: %5d Value/s: ",tagIdentifier.name(),fieldType.name(),count);
	}
	
	public IFDEntry() {

	}
	
	public IFDEntry(byte[] rawDNGBytes,Integer byteLocation,ByteOrder byteOrder,Integer makeOffset) throws Exception{
		this.rawDNGBytes = rawDNGBytes;
		this.byteOrder = byteOrder;
		this.byteLocation = byteLocation;
		this.makeOffset = makeOffset;
		byte[] rawTag = Arrays.copyOfRange(rawDNGBytes, byteLocation, byteLocation+12);
		int tag = getInt(Arrays.copyOfRange(rawTag, 0, 2)) + makeOffset;
		//invalid tag return an empty entry
		if (tag - makeOffset == 0) return;
		if(TagIdentifier.valueOfTag(tag)!=null)
            tagIdentifier = TagIdentifier.valueOfTag(tag); 
        else {
			if (App.verbose) System.out.printf("Invalid TAG:%d (%<x) %s\r\n",tag,StringUtils.formatObjectArray(rawTag, 12));
			return;
			//throw new Exception(String.format("Unknown TAG encountered: %d (%<x) at file byte offset %x",tag,byteLocation));
		}
		int type = getInt(Arrays.copyOfRange(rawTag, 2 , 4));
        if(FieldType.valueOfTag(type)!=null)
			fieldType = FieldType.valueOfTag(type);
        else {
			
			throw new Exception(String.format("Unknown Type encountered: %d for TAG %d (%<x) at file byte offset %x",type,tag,byteLocation));
		}
		count = getLong(Arrays.copyOfRange(rawTag, 4 , 8)).intValue();

		switch (fieldType) {
			case UNKNOWN:
			case BYTE:
			case ASCII:
			case SBYTE:
			case UNDEFINED:
				byteSize = 1;
				break;
			case SHORT:
			case SSHORT:
				byteSize = 2;
				break;
			case LONG:
			case SLONG:
			case FLOAT:
				byteSize = 4;
				break;
			case RATIONAL:
			case SRATIONAL:
			case DOUBLE:
				byteSize = 8;
				break;
			default:
				throw new Exception(String.format("Missing fieldType in switch statement encountered: %d",fieldType.value));
		}
		isValue = (count * byteSize) <= 4;

        if (isValue) 
			value = Arrays.copyOfRange(rawTag, 8 , 12);
        else {
            offset =  getLong(Arrays.copyOfRange(rawTag, 8 , 12)).intValue();
			value = Arrays.copyOfRange(rawDNGBytes, offset, offset + byteSize * count);
		}
                
	}
	
	public Long getLong(byte[] bytes) {
        return ((Integer)ByteBuffer.wrap(bytes).order(byteOrder).getInt()).longValue();
    }
    public int getInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).order(byteOrder).getChar();
    }
    public float getFloat(byte[] bytes) {
        return ByteBuffer.wrap(bytes).order(byteOrder).getFloat();
    }
	public Double getDouble(byte[] bytes) {
		return ByteBuffer.wrap(bytes).order(byteOrder).getDouble();
	}
	public int getUShort(byte[] bytes) {
		return ByteBuffer.wrap(bytes).order(byteOrder).getChar();
	}

    public List<?> getValues() {
        return null;
    }

    public String toValueString() {
        return null;
    }
}
