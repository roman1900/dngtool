package au.com.redmars.ifd;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	public void dumpXMP() {
        children.stream().filter(x -> x.getData().getTagIdentifier().name().equals("XMP"))
        .forEach(x -> {
            try {
                System.out.printf("%s\r\n",(new ByteIFD(x.getData())).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
	public List<IFDStruct> getChildren() {
		return children;
	}
	public IFDStruct getLast() {
		return this.children.get(this.children.size() - 1);
	}
	public Optional<IFDStruct> getByTag(TagIdentifier tagIdentifier) {
		//TODO: This will only return direct descendants of this IFDStruct
		// The TAG we want may be in a Sub IFD
		return this.children.stream().filter(x -> x.data.getTagIdentifier() == tagIdentifier).findFirst();
	}
	public IFDEntry getData() {
		return this.data;
	}

	public String toString() {
		String tabLevel = "\t".repeat(this.depth());
		displayString = tabLevel+"[\r\n";
		children.forEach(x -> {
            displayString +=String.format("%s%s\r\n",tabLevel,x.data.toString());
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
