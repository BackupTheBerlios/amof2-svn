package hub.sam.mof.plugin.modelview.actions;

import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import hub.sam.mof.plugin.modelview.ModelView;
import hub.sam.mof.plugin.modelview.ModelViewContentProvider;
import hub.sam.mof.plugin.modelview.tree.ObjectTreeObject;

public class AddToFilteredClassesAction extends ContextAction {

	public AddToFilteredClassesAction(ModelView view) {
		super(view);
		setText("Filter Objects of same class");
		setToolTipText("Adds the class of this object to the set of classes for that all objects will not be shown.");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_TOOL_CUT));
	}

	@Override
	protected boolean isEnabledFor(Object obj) {
		return obj instanceof ObjectTreeObject;
	}

	@Override
	protected void runFor(Object obj) {
		ObjectTreeObject runFor = (ObjectTreeObject)obj;
		((ModelViewContentProvider)view.getViewer().getContentProvider()).addClassToFilter(runFor.getElement().getClass());
		view.getViewer().refresh();		
	}

}
