package hub.sam.mof.plugin.modelview.tree;

import hub.sam.mof.plugin.modelview.ObjectKind;

import java.util.*;

import cmof.Property;

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
		super.retrieveChildren();
		Collection<TreeObject> result = new Vector<TreeObject>();
		
		cmof.UmlClass metaClass = theObject.getMetaClass();
		result.add(new MetaClassObjectTreeObject(metaClass,theObject, this));		
		result.add(new ComponentsTreeObject(theObject, this));
		for (cmof.NamedElement property: metaClass.getMember()) {
			if (property instanceof Property) {
				result.add(new PropertyTreeObject((Property)property, theObject, this));
			}
		}
		return result;
	}

	@Override
	public String getText() {
		String result = null;
		try {
			result = (String)theObject.get("qualifiedName");
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
