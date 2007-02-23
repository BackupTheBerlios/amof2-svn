/**
 * 
 */
package hub.sam.mof.plugin.modelview.tree;

import hub.sam.mof.plugin.properties.MOF2PropertySource;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IActionFilter;
import org.eclipse.ui.views.properties.IPropertySource;

public class TreeObject extends PlatformObject implements IAdaptable, IActionFilter {

	private TreeParent fParent;
	private final java.lang.Object element;
	private Image image = null;
	private String text = "unknown";
	private int category = 0;
	private final IPropertySource propertySource;
	private final TreeViewer fView;
	
	public TreeObject(java.lang.Object element, TreeParent parent, TreeViewer view) {
		this.element = element;
		this.fParent = parent;
		this.propertySource = new MOF2PropertySource(element);
		this.fView = view;
	}
	
	public Object getElement() {
		return element;
	}
	
	public TreeParent getParent() {
		return fParent;
	}
	
	@Override
	public String toString() {
		return element.toString();
	}
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getText() {
		if (text == null) {
			return "";
		} else {
			return text;
		}		
	}

	public void setText(String from, String text) {
		StringBuffer sb = new StringBuffer(text);
		sb.append(" (from ");
		sb.append(from);
		sb.append(")");
		setText(sb.toString());
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
	
	@Override
	public Object getAdapter(Class key) {		
		if (IPropertySource.class == key) {
			return propertySource;
		} else {		
			return super.getAdapter(key);
		}
	}

    public boolean testAttribute(Object target, String name, String value) {
        if (name.equals("element_instance_of")) {
            try {
                return Class.forName(value).isInstance(element);
            }
            catch (ClassNotFoundException e) {
                return false;
            }
        }
        return false;
    }
    
    protected final TreeViewer getView() {
    	return fView;
    }
    
    protected void delete() {
    	fParent = null;
    }
}