package hub.sam.mof.plugin.modelview.tree;

import hub.sam.mof.plugin.modelview.ObjectKind;

public class PrimitiveTreeObject extends TreeObject {

	private final Object element;
	
	protected PrimitiveTreeObject(Object element, TreeParent parent) {
		super(element, parent);
		this.element = element;
	}
	
	@Override
	public ObjectKind getKind() {
		return ObjectKind.PrimitiveValue;
	}
	
	@Override
	public String getText() {
		return element.toString();
	}

}
