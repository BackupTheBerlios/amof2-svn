package hub.sam.mof.plugin.modelview.actions;

import hub.sam.mof.plugin.modelview.ModelView;
import hub.sam.mof.plugin.modelview.ModelViewContentProvider;
import hub.sam.mof.plugin.modelview.tree.TreeObject;

public class AddToFilteredClassesAction extends ContextAction {

	public AddToFilteredClassesAction(ModelView view) {
		super(view);
		setText("Filter Objects of same class");		
	}

	@Override
	protected boolean isEnabledFor(TreeObject obj) {
		if (obj.getElement() instanceof cmof.reflection.Object) {
			return true;
		}
		return false;
	}

	@Override
	protected void runFor(TreeObject obj) {
		((ModelViewContentProvider)view.getViewer().getContentProvider()).addClassToFilter(obj.getElement().getClass());
		view.getViewer().refresh();		
	}
}
