package hub.sam.mof.plugin.modelview.actions;

import hub.sam.mof.plugin.modelview.*;
import hub.sam.mof.plugin.modelview.tree.InvisibleTreeRoot;
import hub.sam.mof.plugin.modelview.tree.RepositoryTreeObject;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.*;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class RemoveRepositoryAction extends Action {
	
	
	private final ModelView view;
	
	public RemoveRepositoryAction(ModelView view) {
		this.view = view;
		setText("Remove");
		setToolTipText("Removes the repository from the view");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_TOOL_CUT));
		setDisabledImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_TOOL_CUT_DISABLED));
	}
	
	@Override
	public void run() {		
		RepositoryTreeObject toDelete = (RepositoryTreeObject)((IStructuredSelection)view.getViewer().getSelection()).getFirstElement();
		((InvisibleTreeRoot)toDelete.getParent()).removeChild(toDelete);
		view.getViewer().refresh();
	}
	
	public boolean shouldEnable(IStructuredSelection selection) {
		if (selection.size() != 1) {
			return false;
		} else {
			if (selection.getFirstElement() instanceof hub.sam.mof.plugin.modelview.tree.RepositoryTreeObject) {
				return true;
			} else {
				return false;
			}
		}
	}
}
