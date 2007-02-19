package hub.sam.mof.plugin.modelview.tree;

import hub.sam.mof.plugin.modelview.ModelView;

import org.eclipse.swt.graphics.Image;

public class PrimitiveTreeObject extends TreeObject {

	private final Object element;
	
	protected PrimitiveTreeObject(Object element, TreeParent parent, ModelView view) {
		super(element, parent, view);
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
