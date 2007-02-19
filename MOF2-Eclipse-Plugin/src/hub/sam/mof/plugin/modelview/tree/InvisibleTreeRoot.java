package hub.sam.mof.plugin.modelview.tree;


import hub.sam.mof.plugin.modelview.ModelView;

import java.util.Collection;

public class InvisibleTreeRoot extends TreeParent {

	private Collection<TreeObject> children;
	
	public InvisibleTreeRoot(ModelView view) {
		super(null, null, view);
		children = new java.util.ArrayList<TreeObject>();
	}
	
	public void removeChild(TreeObject child) {
		children.remove(child);
	}
	
	public void addChild(TreeObject child) {
		children.add(child);
	}
	
	@Override
	protected Collection<TreeObject> retrieveChildren() {
		super.retrieveChildren();
		return children;
	}

}
