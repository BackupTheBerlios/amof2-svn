package hub.sam.mof.plugin.modelview.tree;

import hub.sam.mof.plugin.modelview.ObjectKind;

import java.util.*;

public class ObjectTreeObject extends TreeParent {

	private final cmof.reflection.Object theObject;
	
	public ObjectTreeObject(cmof.reflection.Object theObject, TreeParent parent) {
		super(theObject, parent);
		this.theObject = theObject;
	}

	protected cmof.reflection.Object getObject() {
		return theObject;
	}
	
	@Override
	protected Collection<TreeObject> retrieveChildren() {
		Collection<TreeObject> result = new Vector<TreeObject>();
		
		result.add(new MetaClassObjectTreeObject(theObject.getMetaClass(),theObject, this));		
		for (Object superClass: theObject.getMetaClass().allParents()) {
			if (superClass instanceof cmof.UmlClass) {
				result.add(new MetaClassObjectTreeObject((cmof.UmlClass)superClass, theObject, this));
			}
		}

		result.add(new ComponentsTreeObject(theObject, this));
		return result;
	}

	@Override
	public String getText() {
		String result = null;
		try {
			result = (String)theObject.get("name");
		} catch (Exception e) {
			// empty			
		}
		if (result == null) {
			try {
				result = "[" + theObject.getMetaClass().getQualifiedName() + "]";
			} catch (Exception e) {
				// empty			
			}	
		}
		if (result == null) {
			result = "UNKNOWN";
		}
		return result;
	}
	
	@Override
	public ObjectKind getKind() {
		return ObjectKind.Object;
	}
}
