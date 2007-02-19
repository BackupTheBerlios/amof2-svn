package hub.sam.mof.plugin.modelview.tree;

import hub.sam.mof.plugin.modelview.ModelView;


public interface IBuilder {
	public TreeObject create(Object obj, TreeParent parent, IBuilderFactory factory, ModelView view);
		
	public void addChildren(Object obj, IChildManager mgr, ModelView view);
}
