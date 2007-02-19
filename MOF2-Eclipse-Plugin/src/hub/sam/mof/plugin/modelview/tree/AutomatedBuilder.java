package hub.sam.mof.plugin.modelview.tree;

import hub.sam.mof.plugin.modelview.ModelView;

import org.eclipse.swt.graphics.Image;


public abstract class AutomatedBuilder implements IBuilder {

	public TreeObject create(Object obj, TreeParent parent,
			IBuilderFactory factory, ModelView view) {
		TreeObject result = new BuildTreeObject(obj, parent, this, factory, view);	
		result.setText(getText(obj));
		result.setImage(getImage(obj));
		result.setCategory(getCategory(obj));
		return result;
	}

	public abstract String getText(Object obj);
	
	public abstract Image getImage(Object obj);
	
	public abstract int getCategory(Object obj);
}
