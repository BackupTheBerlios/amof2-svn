package hub.sam.mof.plugin.views.tree;

import hub.sam.mof.plugin.views.ObjectKind;

import java.util.Collection;
import java.util.Vector;

public class ComponentsTreeObject extends TreeParent {

	private final cmof.reflection.Object theObject;
	
	public ComponentsTreeObject(cmof.reflection.Object theObject, TreeParent parent) {
		super(theObject, parent);
		this.theObject = theObject;
	}

	@Override
	protected Collection<TreeObject> retrieveChildren() {
		Collection<TreeObject> result = new Vector<TreeObject>();
		for (cmof.reflection.Object component: theObject.getComponents()) {
			result.add(new ObjectTreeObject(component, this));
		}
		return result;
	}
	
	@Override
	public ObjectKind getKind() {
		return ObjectKind.Components;
	}
	
	@Override
	public String getText() {
		return "components";
	}

}
