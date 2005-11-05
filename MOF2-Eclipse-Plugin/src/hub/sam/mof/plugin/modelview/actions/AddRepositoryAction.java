package hub.sam.mof.plugin.modelview.actions;

import hub.sam.mof.plugin.modelview.ModelView;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class AddRepositoryAction extends Action {
	
	private final ModelView view;
	
	public AddRepositoryAction(ModelView view) {
		this.view = view;
		setText("Add ...");
		setToolTipText("Add a remote repository to the view.");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
	}
	
	@Override
	public void run() {	
		Display display=Display.getCurrent();
	    Shell shell=new Shell(display);
	    AddRepositoryDialog dialog = new AddRepositoryDialog(shell, "jnp://localhost:1099", view);
	    dialog.open();
	    shell.dispose();	
	}
}
