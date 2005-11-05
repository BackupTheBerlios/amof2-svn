/**
 * 
 */
package hub.sam.mof.plugin.modelview.tree;

import java.util.*;

public abstract class  TreeParent extends TreeObject {
	private Collection<TreeObject> children = null;
	
	public TreeParent(java.lang.Object element, TreeParent parent) {
		super(element, parent);
	}

	public Collection<TreeObject> getChildren() {
		if (children == null || !isCacheValid()) {
			children = retrieveChildren();
		}
		return children;
	}
		
	protected boolean isCacheValid() {
		return true;
	}
	
	public boolean hasChildren() {
		return getChildren().size() > 0;
	}
	
	protected abstract Collection<TreeObject> retrieveChildren();
	
}