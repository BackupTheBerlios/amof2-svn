/**
 * 
 */
package hub.sam.mof.plugin.modeleditor;

import java.io.IOException;

import hub.sam.mof.Repository;
import hub.sam.mof.plugin.modelview.ModelViewContentProvider;
import hub.sam.mof.plugin.modelview.ModelViewLabelProvider;
import hub.sam.mof.plugin.modelview.tree.builder.Categories;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.EditorPart;

import cmof.Package;
import cmof.reflection.Extent;

/**
 * @author guwac
 *
 */
public class ModelEditor extends EditorPart {

	private static final Repository REPOSITORY = Repository.getLocalRepository();
	private static final Extent CMOF_EXTENT = REPOSITORY.getExtent(Repository.CMOF_EXTENT_NAME);
	private static final Package CMOF_PACKAGE = (Package) CMOF_EXTENT.query("Package:cmof");
	
	private TreeViewer viewer;
	private Extent extent;
	
	/**
	 * 
	 */
	public ModelEditor() {

		super();
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		
		setSite(site);
		setInput(input);
		
		if (input instanceof IPathEditorInput) {
			
			String path = ((IPathEditorInput) input).getPath().toOSString();
			
			extent = REPOSITORY.createExtent(path);
			try{
				REPOSITORY.loadXmiIntoExtent(extent, CMOF_PACKAGE, path);
			} catch (Exception e) {
				throw new PartInitException("Could not load file into extent.", e);
			}
		} else
			throw new PartInitException("Wrong resource.");
	}

	/**
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		ExtentViewContentProvider provider = new ExtentViewContentProvider(this, viewer);
		provider.setExtent(extent);
		viewer.setContentProvider(provider);
        		
		viewer.setLabelProvider(ModelViewLabelProvider.getDefault());			
		viewer.setInput(getEditorSite());
		viewer.setSorter(new Categories());
		getSite().setSelectionProvider(viewer);
	}

	/**
	 * 
	 */
	@Override
	public void setFocus() {
		
		viewer.getControl().setFocus();
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#isDirty()
	 */
	@Override
	public boolean isDirty() {
		
		return false;
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		
		IEditorInput output = getEditorInput();
		if(output instanceof IPathEditorInput)
			try{
				String path = ((IPathEditorInput) output).getPath().toOSString();
				REPOSITORY.writeExtentToXmi(path, CMOF_PACKAGE, extent);
			} catch (Exception e) {
				// TODO: handle exception
			}
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 */
	@Override
	public boolean isSaveAsAllowed() {
		
		return false;
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
	}

	/**
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
		
		Repository.getLocalRepository().deleteExtent(extent.toString());
		super.dispose();
	}
}
