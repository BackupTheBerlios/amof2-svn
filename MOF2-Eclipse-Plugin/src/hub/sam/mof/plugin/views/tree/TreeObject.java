/**
 * 
 */
package hub.sam.mof.plugin.views.tree;


import hub.sam.mof.plugin.views.ObjectKind;

import org.eclipse.core.runtime.IAdaptable;

public abstract class TreeObject implements IAdaptable {

	private final TreeParent parent;
	private final java.lang.Object element;
	
	protected TreeObject(java.lang.Object element, TreeParent parent) {
		this.element = element;
		this.parent = parent;
	}
	
	protected Object getElement() {
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
	
	public String getText() {
		return "UNKNOWN";
	}
	
	public ObjectKind getKind() {
		return ObjectKind.Object;
	}
}