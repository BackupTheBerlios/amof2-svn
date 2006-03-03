package hub.sam.mof.plugin.modelview.actions;

import hub.sam.mof.plugin.modelview.ModelView;
import hub.sam.mof.plugin.modelview.ModelViewContentProvider;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class RefreshAction extends Action {
		
	private final ModelView view;

	public RefreshAction(ModelView view) {
		this.view = view;
		setText("Refresh");	
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_TOOL_REDO));
	}

	@Override
	public void run() {
		((ModelViewContentProvider)view.getViewer().getContentProvider()).getRoot().refresh();
		view.getViewer().refresh();
	}
}
