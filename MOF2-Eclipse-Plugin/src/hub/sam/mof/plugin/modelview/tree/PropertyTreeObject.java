package hub.sam.mof.plugin.modelview.tree;

import hub.sam.mof.plugin.modelview.ObjectKind;

import java.util.Collection;

public class PropertyTreeObject extends ObjectTreeObject {

	private final cmof.Property property;
	
	public PropertyTreeObject(cmof.Property property, cmof.reflection.Object theObject, TreeParent parent) {
		super(theObject, parent);
		this.property = property;
	}

	@Override
	public ObjectKind getKind() {
		return ObjectKind.Attribute;
	}
	
	@Override
	public String getText() {
		return property.getQualifiedName() + ":" + property.getType().getQualifiedName();
	}
	
	@Override
	protected Collection<TreeObject> retrieveChildren() {
		super.retrieveChildren();
		Object value = getObject().get(property);
		Collection<TreeObject> result = new java.util.Vector<TreeObject>();
		if (value instanceof cmof.common.ReflectiveCollection) {
			for(Object singleValue: (cmof.common.ReflectiveCollection)value) {
				if (singleValue instanceof cmof.reflection.Object) {
					result.add(new ObjectTreeObject((cmof.reflection.Object)singleValue, this));
				} else {
					result.add(new PrimitiveTreeObject(singleValue, this));
				}
			}
		} else if (value instanceof cmof.reflection.Object) {
			result.add(new ObjectTreeObject((cmof.reflection.Object)value, this));
		} else {
			result.add(new PrimitiveTreeObject(value, this));
		}
		return result;
	}
}
