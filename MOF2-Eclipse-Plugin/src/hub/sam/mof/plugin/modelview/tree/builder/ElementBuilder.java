package hub.sam.mof.plugin.modelview.tree.builder;

import org.eclipse.swt.graphics.Image;

import cmof.reflection.Object;
import hub.sam.mof.plugin.modelview.Images;
import hub.sam.mof.plugin.modelview.ViewLabelProvider;
import hub.sam.mof.plugin.modelview.tree.AutomatedBuilder;
import hub.sam.mof.plugin.modelview.tree.IChildManager;
import hub.sam.mof.plugin.modelview.tree.ObjectBuilder;
import hub.sam.mof.plugin.modelview.tree.TreeObject;

public abstract class ElementBuilder extends AutomatedBuilder {
			
	public void addChildren(java.lang.Object obj, IChildManager mgr) {				
		TreeObject to = new ObjectBuilder().create(obj, mgr.getParent(), mgr.getFactory());
		to.setImage(Images.getDefault().getInfos());
		to.setText("<details>");
		mgr.addChild(to);		
	}

	@Override
	public Image getImage(java.lang.Object element) {
		return ViewLabelProvider.getDefault().getImage(element);		
	}

	@Override
	public String getText(java.lang.Object element) {	
		if (element instanceof cmof.reflection.Object) {
			Object theObject = (Object)element;		
			String result = null;
			try {
				result = (String)theObject.get("qualifiedName");
			} catch (Exception e) {
				// empty			
			}
			if (result == null) {
				try {
					result = "[" + theObject.getMetaClass().getQualifiedName() + "]";
				} catch (Exception e) {
					// empty			
				}	
			}
			if (result == null) {
				result = "UNKNOWN";
			}
			return result;
		} else {
			return "UNEXPECTED";
		}
	}
}
