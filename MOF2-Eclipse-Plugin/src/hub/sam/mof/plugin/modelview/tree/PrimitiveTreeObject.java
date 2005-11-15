package hub.sam.mof.plugin.modelview.tree;

import org.eclipse.swt.graphics.Image;

public class PrimitiveTreeObject extends TreeObject {

	private final Object element;
	
	protected PrimitiveTreeObject(Object element, TreeParent parent) {
		super(element, parent);
		this.element = element;
	}
	
	@Override
	public Image getImage() {
		return null;
	}
	
	@Override
	public String getText() {
		return element.toString();
	}

}
