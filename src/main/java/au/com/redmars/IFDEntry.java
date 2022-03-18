package au.com.redmars;

import java.util.Arrays;

public class IFDEntry {

	private TagIdentifier tagIdentifier;
	private FieldType fieldType;
	private Integer count;
	private Integer byteSize;
	private Boolean isValue;
	private Integer offset;
	private byte[] 	value;


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

	public Boolean getIsValue() {
		return isValue;
	}

	public byte[] getValue() {
		if (isValue)
			return value;
		else 
			return null;
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
		return String.format("%25s\t%10s\tCount: %5d Value/s: ",tagIdentifier.name(),fieldType.name(),count);
	}

	public IFDEntry(byte[] rawTag,ImageHeader imageHeader) throws Exception{
		int tag = imageHeader.getInt(Arrays.copyOfRange(rawTag, 0, 2));
        if(TagIdentifier.valueOfTag(tag)!=null)
            tagIdentifier = TagIdentifier.valueOfTag(tag);
        else throw new Exception(String.format("Unknown TAG encountered: %d",tag));
        
		int type = imageHeader.getInt(Arrays.copyOfRange(rawTag, 2 , 4));
        if(FieldType.valueOfTag(type)!=null)
			fieldType = FieldType.valueOfTag(type);
        else throw new Exception(String.format("Unknown Type encountered: %d",type));
        
		count = imageHeader.getLong(Arrays.copyOfRange(rawTag, 4 , 8)).intValue();
        

		switch (fieldType) {
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
				throw new Exception(String.format("Missing fieldType in switch statement encountered: %d",fieldType));
		}
		isValue = (count * byteSize) <= 4;

        if (isValue) 
			value = Arrays.copyOfRange(rawTag, 8 , 12);
        else
            offset =  imageHeader.getLong(Arrays.copyOfRange(rawTag, 8 , 12)).intValue();
                
	}

}
