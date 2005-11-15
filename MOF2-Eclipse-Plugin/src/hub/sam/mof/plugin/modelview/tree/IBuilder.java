package hub.sam.mof.plugin.modelview.tree;


public interface IBuilder {
	public TreeObject create(Object obj, TreeParent parent, IBuilderFactory factory);
		
	public void addChildren(Object obj, IChildManager mgr);
}
