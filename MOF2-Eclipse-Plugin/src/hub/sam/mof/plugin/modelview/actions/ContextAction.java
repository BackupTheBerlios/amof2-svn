package hub.sam.mof.plugin.modelview.actions;

import hub.sam.mof.plugin.modelview.ModelView;
import hub.sam.mof.plugin.modelview.tree.TreeObject;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;

public abstract class ContextAction extends Action {

	protected final ModelView view;
	
	abstract protected boolean isEnabledFor(Object obj); 	
	abstract protected void runFor(Object obj);
	
	public ContextAction(ModelView view) {
		this.view = view;
	}
	
	public boolean shouldEnable(IStructuredSelection selection) {
		if (selection.size() != 1) {
			return false;
		} else {
			if (selection.getFirstElement() instanceof TreeObject) {
				return isEnabledFor(selection.getFirstElement());
			} else {
				return false;
			}
		}
	}
	
	@Override
	public void run() {		
		runFor(((IStructuredSelection)view.getViewer().getSelection()).getFirstElement());		
	}
}
