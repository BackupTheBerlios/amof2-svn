package hub.sam.mof.plugin.modelview.tree;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.PlatformUI;

public abstract class  TreeParent extends TreeObject {
	private Collection<TreeObject> children = null;
	private boolean isCacheValid = false;
	private final MyPropertyChangeListener listener = new MyPropertyChangeListener();
	
	class MyPropertyChangeListener implements PropertyChangeListener {
		public void propertyChange(PropertyChangeEvent evt) {		
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {			
					if (getParent() == null) {
						refresh();						
						getView().refresh(TreeParent.this);
					} else {						
						getParent().refresh();
						getView().refresh(getParent());
					}
				}				
			});
		}
	}
	
	public TreeParent(java.lang.Object element, TreeParent parent, TreeViewer view) {
		super(element, parent, view);
		if (element instanceof cmof.reflection.Object) {
			((cmof.reflection.Object)element).addListener(listener);
		}
	}

	public Collection<TreeObject> getChildren() {
		if (children == null) {
			children = retrieveChildren();
		} else if (!isCacheValid()) {
			for(TreeObject child: children) {
				child.delete();
			}
			children = retrieveChildren();
		} 
		return children;
	}
		
	private boolean isCacheValid() {
		return isCacheValid;
	}
	
	public final void refresh() {
		if (isCacheValid) {
			isCacheValid = false;
			for (TreeObject to: getChildren()) {
				if (to instanceof TreeParent) {
					((TreeParent)to).refresh();
				}
			}			
		}
	}
	
	public boolean hasChildren() {
		return getChildren().size() > 0;
	}
	
	protected  Collection<TreeObject> retrieveChildren() {
		isCacheValid = true;
		return null;
	}

	@Override
	protected void delete() {
		Object element = getElement();
		if (element instanceof cmof.reflection.Object) {
			((cmof.reflection.Object)element).removeListener(listener);
		}
		if (children != null) {
			for (TreeObject child: children) {
				child.delete();
			}
		}
		super.delete();
	}	
	
}