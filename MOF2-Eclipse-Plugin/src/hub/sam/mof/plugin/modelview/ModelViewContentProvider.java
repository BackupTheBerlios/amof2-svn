package hub.sam.mof.plugin.modelview;

import hub.sam.mof.plugin.modelview.tree.InvisibleTreeRoot;
import hub.sam.mof.plugin.modelview.tree.RepositoryTreeObject;
import hub.sam.mof.plugin.modelview.tree.TreeObject;
import hub.sam.mof.plugin.modelview.tree.TreeParent;
import hub.sam.mof.reflection.client.ClientRepository;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ModelViewContentProvider implements IStructuredContentProvider, ITreeContentProvider {

	private final ModelView view;

	private InvisibleTreeRoot invisibleRoot;
	
	public ModelViewContentProvider(ModelView view) {
		this.view = view;
	}
	
	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		// empty
	}
	
	public void dispose() {
		// empty
	}
	
	public Object[] getElements(Object parent) {
		if (parent.equals(this.view.getViewSite())) {
			if (invisibleRoot==null) initialize();
			return getChildren(invisibleRoot);
		}
		return getChildren(parent);
	}
	public Object getParent(Object child) {
		if (child instanceof TreeObject) {
			return ((TreeObject)child).getParent();
		}
		return null;
	}
	public Object [] getChildren(Object parent) {
		if (parent instanceof TreeParent) {
			return ((TreeParent)parent).getChildren().toArray();
		}
		return new Object[0];
	}
	public boolean hasChildren(Object parent) {
		if (parent instanceof TreeParent)
			return ((TreeParent)parent).hasChildren();
		return false;
	}

	private void initialize() {			
		invisibleRoot = new InvisibleTreeRoot();
	}
	
	public TreeParent getRoot() {
		return invisibleRoot;
	}
		
	public void addRepository(ClientRepository repository) {
		invisibleRoot.addChild(new RepositoryTreeObject(repository, invisibleRoot));
		view.getViewer().refresh();
	}
}