/**
 * 
 */
package hub.sam.mof.plugin.modelview.tree;

import java.util.*;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.swt.graphics.Image;

public class TreeObject implements IAdaptable {

	private final TreeParent parent;
	private final java.lang.Object element;
	private Image image = null;
	private String text = "unknown";
	private int category = 0;
	
	public TreeObject(java.lang.Object element, TreeParent parent) {
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

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public Object getContext() {
		return this;
	}
	
	private Collection<Integer> options = new HashSet<Integer>(3);
	
	public void setOption(int option) {
		options.add(new Integer(option));
	}
	
	public void unsetOption(int option) {
		options.remove(new Integer(option));
	}
	
	public boolean optionIsSet(int option) {
		return options.contains(new Integer(option));
	}
}