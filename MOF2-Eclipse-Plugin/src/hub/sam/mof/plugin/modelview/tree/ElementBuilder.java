package hub.sam.mof.plugin.modelview.tree;

import org.eclipse.swt.graphics.Image;

import cmof.reflection.Object;
import hub.sam.mof.plugin.modelview.ViewLabelProvider;

public abstract class ElementBuilder extends AutomatedBuilder {
	
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
