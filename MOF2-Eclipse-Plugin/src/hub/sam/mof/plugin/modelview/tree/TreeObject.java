/**
 * 
 */
package hub.sam.mof.plugin.modelview.tree;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.swt.graphics.Image;

public abstract class TreeObject implements IAdaptable {

	private final TreeParent parent;
	private final java.lang.Object element;
	private Image image = null;
	private String text = "unknown";
	
	protected TreeObject(java.lang.Object element, TreeParent parent) {
		this.element = element;
		this.parent = parent;
	}
	
	public Object getElement() {
		return element;
	}
	
	public TreeParent getParent() {
		return parent;
	}
	
	@Override
	public String toString() {
		return element.toString();
	}
	
	public Object getAdapter(Class key) {
		return null;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}