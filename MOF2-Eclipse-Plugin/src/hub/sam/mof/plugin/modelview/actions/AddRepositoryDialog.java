package hub.sam.mof.plugin.modelview.actions;

import hub.sam.mof.Repository;
import hub.sam.mof.plugin.modelview.*;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AddRepositoryDialog extends Dialog {

	Text repositoryUrlField;
	private Button repositoryLocalRadio;
	private Button repositoryRemoteRadio;
	private String repositoryUrl = null;
	private final ModelView view;
	
	protected AddRepositoryDialog(Shell parentShell, String repositoryUrl, ModelView view) {
		super(parentShell);
		parentShell.setText("Add a repository");
		this.repositoryUrl = repositoryUrl;
		this.view = view;
	}
	
	@Override
	public void create() {
		super.create();
		repositoryUrlField.setFocus();
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite main = new Composite(parent, SWT.NONE);

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		main.setLayout(layout);
		main.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label messageLabel = new Label(main, SWT.WRAP);
		messageLabel.setText("Choose repository kind.");
		GridData data = new GridData();
		data.horizontalSpan = 2;				
		messageLabel.setLayoutData(data);
		
		repositoryLocalRadio = new Button(main, SWT.RADIO);
		repositoryLocalRadio.setText("local repository");
		data = new GridData();
		data.horizontalSpan = 2;				
		repositoryLocalRadio.setLayoutData(data);	
		
		repositoryRemoteRadio = new Button(main, SWT.RADIO);
		repositoryRemoteRadio.setText("remote repository");
		data = new GridData();
		data.horizontalSpan = 2;				
		repositoryRemoteRadio.setLayoutData(data);
		
		new Label(main, SWT.NONE).setText("URL:");
		repositoryUrlField = new Text(main, SWT.BORDER);
		repositoryUrlField.setText(repositoryUrl);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.ENTRY_FIELD_WIDTH);
		repositoryUrlField.setLayoutData(data);
		
		repositoryRemoteRadio.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				repositoryUrlField.setEnabled(true);		
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);				
			}		
		});
		
		repositoryLocalRadio.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				repositoryUrlField.setEnabled(false);		
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);				
			}		
		});
		
		repositoryLocalRadio.setSelection(true);
		repositoryUrlField.setEnabled(false);
		return main;
	}
	
	protected void createRepositoryUrlField(Composite main) {
		new Label(main, SWT.NONE).setText("URL:");
		repositoryUrlField = new Text(main, SWT.BORDER);
		repositoryUrlField.setText(repositoryUrl);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.ENTRY_FIELD_WIDTH);
		repositoryUrlField.setLayoutData(data);		
	}

	public String getRepositoryUrl() {
		return repositoryUrl;
	}
	
	public boolean isLocalRepositorySelected() {
		return repositoryLocalRadio.getSelection();
	}

	@Override
	protected void okPressed() {
		String repositoryUrl = repositoryUrlField.getText();
		if (repositoryUrl == null || repositoryUrl.length() == 0) {
			return;
		}
		this.repositoryUrl = repositoryUrl;
		if (isLocalRepositorySelected()) {
			((ModelViewContentProvider)view.getViewer().getContentProvider()).addRepository(Repository.connectToLocalRepository());
			super.okPressed();
		} else {
			try {
				((ModelViewContentProvider)view.getViewer().getContentProvider()).addRepository(Repository.connectToRemoteRepository(getRepositoryUrl()));
				super.okPressed();
			} catch (Exception e) {
				MessageDialog.openError(
						view.getViewer().getControl().getShell(),
						"Can not connect ...",
						"Can not connect to remote repository: " + e.getMessage());
				return;
			}
		}
		
	}

	@Override
	protected void cancelPressed() {
		repositoryUrl = null;
		super.cancelPressed();
	}	
}
