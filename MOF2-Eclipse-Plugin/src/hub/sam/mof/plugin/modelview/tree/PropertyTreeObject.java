package hub.sam.mof.plugin.modelview.tree;

import hub.sam.mof.plugin.modelview.Images;

import java.util.Collection;

import org.eclipse.swt.graphics.Image;

public class PropertyTreeObject extends ManTreeObject {

	private final cmof.Property property;
	private final cmof.reflection.Object object;
	
	public PropertyTreeObject(cmof.Property property, cmof.reflection.Object theObject, TreeParent parent, IBuilderFactory factory) {
		super(theObject, parent, factory);
		this.property = property;
		this.object = theObject;
	}

	@Override
	public Image getImage() {
		return Images.getDefault().getAttribute();
	}
	
	@Override
	public String getText() {
		return property.getQualifiedName() + ":" + property.getType().getQualifiedName();
	}
	
	@Override
	protected Collection<TreeObject> retrieveChildren() {
		super.retrieveChildren();
		Object value = object.get(property);
		Collection<TreeObject> result = new java.util.Vector<TreeObject>();
		if (value instanceof cmof.common.ReflectiveCollection) {
			for(Object singleValue: (cmof.common.ReflectiveCollection)value) {
				if (singleValue instanceof cmof.reflection.Object) {
					result.add(build(singleValue));
				} else {
					result.add(new PrimitiveTreeObject(singleValue, this));
				}
			}
		} else if (value instanceof cmof.reflection.Object) {
			result.add(build(value));
		} else {
			result.add(new PrimitiveTreeObject(value, this));
		}
		return result;
	}
}
