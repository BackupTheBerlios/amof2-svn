package hub.sam.mof.plugin.modelview.tree;


public class ManTreeObject extends TreeParent {

	private final IBuilderFactory factory;
	
	public ManTreeObject(java.lang.Object obj, TreeParent parent, IBuilderFactory factory) {
		super(obj, parent);
		this.factory = factory;
	}
	
	protected TreeObject build(java.lang.Object obj) {
		return factory.getBuilder(obj).create(obj, this, factory);
	}
}
