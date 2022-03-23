package au.com.redmars;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IFDStruct {
	private IFDEntry data;
	private IFDStruct parent;
	private List<IFDStruct> children;
	private String displayString;
	public IFDStruct(IFDEntry Data) {
		parent = null;
		data = Data;
		children = new ArrayList<IFDStruct>();
	}

	public boolean isRoot() {
		return parent == null;
	}

	public void addChild(IFDEntry Data) {
		IFDStruct child = new IFDStruct(Data);
		child.parent = this;
		this.children.add(child);
	}
	public int depth() {
		int d = 0;
		if (parent != null) {
			d += 1;
			d += parent.depth();
		} 
		return d;
	}
	public List<IFDStruct> getChildren() {
		return children;
	}
	public IFDStruct getLast() {
		return this.children.get(this.children.size() - 1);
	}

	public IFDEntry getData() {
		return this.data;
	}

	public String toString() {
		String tabLevel = "\t".repeat(this.depth());
		displayString = tabLevel+"[\r\n";
		children.forEach(x -> {
            displayString +=String.format("%s%s ",tabLevel,x.data.toString());
            try {
                switch (x.data.getFieldType()) {
                    case BYTE:
                        displayString+=String.format("%s\r\n",StringUtils.formatObjectList(new ByteIFD(x.data).getValues(),10));
                        break;
                    case SHORT:
                        displayString+=String.format("%s\r\n",StringUtils.formatObjectList(new ShortIFD(x.data).getValues(),10));
                        break;
                    case LONG:
                        displayString+=String.format("%s\r\n",StringUtils.formatObjectList(new LongIFD(x.data).getValues(),10));
                        break;
                    case RATIONAL:
                    case SRATIONAL:
                        displayString+=String.format("%s\r\n",StringUtils.formatObjectList(new RationalIFD(x.data).getValues(),10));
                        break;
                    case FLOAT:
                        displayString+=String.format("%s\r\n",StringUtils.formatObjectList(new FloatIFD(x.data).getValues(),10));
                        break;
                    case DOUBLE:
                        displayString+=String.format("%s\r\n",StringUtils.formatObjectList(new DoubleIFD(x.data).getValues(),10));
                        break;
                    case UNDEFINED:
                        //TODO: Implement individual undefined per TAG specs
                        displayString+=String.format("UNDEFINED DATA TAG SPECIFIC\r\n");
                        break;    
                    case ASCII:
						displayString+=String.format("%s\r\n",new String((new AsciiIFD(x.data)).getValues()
                                .stream()
                                .map(String::valueOf)
                                .collect(Collectors.joining())));
                        break;
                    default:
                        throw new Exception(String.format("Unhandled field type in switch statement: %s",x.data.getFieldType().name()));
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            if (x.getChildren().size() > 0) {
                x.getChildren().forEach(c ->{
                    try {displayString += c.toString();} catch (Exception e) {e.printStackTrace();}
                });
            }
        });
		displayString += tabLevel+"]\r\n";
		return displayString;
	}
}
