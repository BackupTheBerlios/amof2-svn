package hub.sam.mof.plugin.modelview.tree;

import hub.sam.mof.plugin.modelview.ObjectKind;

import java.util.*;

public class MetaClassObjectTreeObject extends ObjectTreeObject {

	private final cmof.UmlClass metaClass;
	
	public MetaClassObjectTreeObject(cmof.UmlClass metaClass, cmof.reflection.Object theObject, TreeParent parent) {
		super(theObject, parent);
		this.metaClass = metaClass;
	}
	
	@Override
	public ObjectKind getKind() {
		return ObjectKind.MetaObject;
	}
	
	@Override
	public String getText() {
		return metaClass.getQualifiedName();
	}
}
