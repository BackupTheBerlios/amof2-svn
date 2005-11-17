package hub.sam.mof.plugin.modelview;

import hub.sam.mof.plugin.modelview.actions.*;
import hub.sam.mof.plugin.modelview.tree.builder.Categories;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class ModelView extends ViewPart {
	TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private AddRepositoryAction addRepository;
	private RemoveRepositoryAction removeRepository;
	private AddModelAction addModel;
	private AddToFilteredClassesAction addToFilteredClasses;
	private ShowDetailsAction showDetails;
	private Action setFilter;

	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	 
	class NameSorter extends ViewerSorter {
		// empty
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	@Override
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.setContentProvider(new ModelViewContentProvider(this));
		viewer.setLabelProvider(ViewLabelProvider.getDefault());			
		viewer.setInput(getViewSite());
		viewer.setSorter(new Categories());
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	public TreeViewer getViewer() {
		return viewer;
	}
	
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				ModelView.this.fillContextMenu(manager);				
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(addRepository);
		manager.add(new Separator());
		manager.add(setFilter);
	}

	void fillContextMenu(IMenuManager manager) {
		removeRepository.setEnabled(removeRepository.shouldEnable((IStructuredSelection)viewer.getSelection()));
		manager.add(removeRepository);	
		addModel.setEnabled(addModel.shouldEnable((IStructuredSelection)viewer.getSelection()));
		manager.add(addModel);
		addToFilteredClasses.setEnabled(addToFilteredClasses.shouldEnable((IStructuredSelection)viewer.getSelection()));
		manager.add(addToFilteredClasses);
		showDetails.setEnabled(showDetails.shouldEnable((IStructuredSelection)viewer.getSelection()));
		manager.add(showDetails);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(addRepository);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {
		addRepository = new AddRepositoryAction(this);					
		removeRepository = new RemoveRepositoryAction(this);
		addModel = new AddModelAction(this);
		addToFilteredClasses = new AddToFilteredClassesAction(this);
		showDetails = new ShowDetailsAction(this);
		
		setFilter = new Action() {
			@Override
			@SuppressWarnings("synthetic-access")
			public void run() {
				showMessage("setFilter executed");
			}
		};
		setFilter.setText("Filter ...");
		setFilter.setToolTipText("Configure the filter to constrain the kind model objects show in the tree.");
					
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@SuppressWarnings("synthetic-access")
			public void doubleClick(DoubleClickEvent event) {
				drillDownAdapter.goInto(((IStructuredSelection)event.getSelection()).getFirstElement());
			}
		});
	}
	
	public void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"Model View",
			message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}