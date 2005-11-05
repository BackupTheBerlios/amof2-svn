package hub.sam.mof.plugin.views.tree;


import java.util.Collection;

public class InvisibleTreeRoot extends TreeParent {

	private Collection<TreeObject> children;
	
	public InvisibleTreeRoot() {
		super(null, null);
		children = new java.util.ArrayList<TreeObject>(1);
		try {
			//children.add(new RepositoryTreeObject(hub.sam.mof.Repository.connectToRemoteRepository("jnp://olymp:1099"), this));
			children.add(new RepositoryTreeObject(hub.sam.mof.Repository.connectToLocalRepository(), this));    	
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	@Override
	protected Collection<TreeObject> retrieveChildren() {
		return children;
	}

}
