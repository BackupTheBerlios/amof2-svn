package hub.sam.mof.plugin.views.tree;

import hub.sam.mof.plugin.views.ObjectKind;

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
