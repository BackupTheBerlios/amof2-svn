package hub.sam.mof.plugin.modelview.tree;

import cmof.reflection.*;
import hub.sam.mof.plugin.modelview.ObjectKind;

import java.util.*;

public class ExtentTreeObject extends TreeParent {

	private String extentName;
	private final Extent extent;
	
	public ExtentTreeObject(Extent extent, String extentName, TreeParent parent) {
		super(extent, parent);
		this.extent = extent;
		this.extentName = extentName;
	}

	@Override
	protected Collection<TreeObject> retrieveChildren() {
		super.retrieveChildren();
		Collection<TreeObject> result = new Vector<TreeObject>();		
		Collection<cmof.reflection.Object> outermostComposites = new Vector<cmof.reflection.Object>();
		for (cmof.reflection.Object aObject: extent.getObject()) {
			if (aObject.container() == null) {
				outermostComposites.add(aObject);
			}
		}
		for (cmof.reflection.Object aObject: outermostComposites) {
			result.add(new ObjectTreeObject(aObject, this));
		}
		return result;
	}
	
	@Override
	public String getText() {
		return extentName;
	}
	
	@Override
	public ObjectKind getKind() {
		return ObjectKind.Extent;
	}
}
