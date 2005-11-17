package hub.sam.mof.plugin.modelview.actions;

import hub.sam.mof.plugin.modelview.ModelView;
import hub.sam.mof.plugin.modelview.tree.TreeObject;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;

public abstract class ContextAction extends Action {

	protected final ModelView view;
	
	abstract protected boolean isEnabledFor(TreeObject obj); 	
	abstract protected void runFor(TreeObject obj);
	
	public ContextAction(ModelView view) {
		this.view = view;
	}
	
	public ContextAction(ModelView view, String text, int param) {
		super(text, param);
		this.view = view;
	}
	
	public boolean shouldEnable(IStructuredSelection selection) {
		if (selection.size() != 1) {
			return false;
		} else {
			if (selection.getFirstElement() instanceof TreeObject) {
				return isEnabledFor((TreeObject)selection.getFirstElement());
			} else {
				return false;
			}
		}
	}
	
	@Override
	public void run() {		
		runFor((TreeObject)((IStructuredSelection)view.getViewer().getSelection()).getFirstElement());		
	}
}
