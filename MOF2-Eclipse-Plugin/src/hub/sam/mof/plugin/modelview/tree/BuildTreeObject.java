package hub.sam.mof.plugin.modelview.tree;

import java.util.*;


public class BuildTreeObject extends TreeParent {

	private final IBuilder builder;	final IBuilderFactory factory;
	final TreeParent self;
	
	public BuildTreeObject(Object obj, TreeParent parent, IBuilder builder, IBuilderFactory factory) {
		super(obj, parent);
		this.builder = builder;
		this.factory = factory;
		this.self = this;
	}

	@Override
	protected Collection<TreeObject> retrieveChildren() {
		Collection<TreeObject> result = new Vector<TreeObject>();
		IChildManager mgr = new ChildManager(result);
		builder.addChildren(getElement(), mgr);
		return result;
	}
			
	class ChildManager implements IChildManager {
		private final Collection<TreeObject> childNodes;
		ChildManager(Collection<TreeObject> childNodes) {
			this.childNodes = childNodes;
		}
		public TreeObject addChild(Object childObject) {
			IBuilder builder = factory.getBuilder(childObject);
			TreeObject child = builder.create(childObject, self, factory);
			childNodes.add(child);
			return child;
		}
		public void addChild(TreeObject to) {
			childNodes.add(to);
		}
		public TreeParent getParent() {
			return self;
		}
		public IBuilderFactory getFactory() {
			return factory;
		}
	}
	
	@Override
	public Object getContext() {
		return builder;
	}
}
