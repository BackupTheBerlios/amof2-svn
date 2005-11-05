package hub.sam.mof.plugin.modelview.tree;


import java.util.Collection;

public class InvisibleTreeRoot extends TreeParent {

	private Collection<TreeObject> children;
	
	public InvisibleTreeRoot() {
		super(null, null);
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
		return children;
	}

}
