/**
 * 
 */
package hub.sam.mof.plugin.views;

import hub.sam.mof.plugin.views.tree.TreeObject;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

class ViewLabelProvider extends LabelProvider {

	@SuppressWarnings("unused")
	private final ModelView view;

	public ViewLabelProvider(ModelView view) {
		this.view = view;
	}
	
	@Override
	public String getText(java.lang.Object obj) {
		if (obj instanceof TreeObject) {
			return ((TreeObject)obj).getText();
		} else {
			return "UNKNOWN";
		}		
	}
	
	@Override
	public Image getImage(java.lang.Object obj) {
		if (obj instanceof TreeObject) {
			ObjectKind kind = ((TreeObject)obj).getKind();
			switch (kind) {
			case Repository:
				return org.eclipse.jdt.ui.JavaUI.getSharedImages().getImage(
						org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_JAR);
			case Extent:
				return org.eclipse.jdt.ui.JavaUI.getSharedImages().getImage(
						org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_PACKAGE);
			case Object:
				return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
			case MetaObject:
				return org.eclipse.jdt.ui.JavaUI.getSharedImages().getImage(
						org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_CLASS);			
			case Attribute:
				return org.eclipse.jdt.ui.JavaUI.getSharedImages().getImage(
						org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_LOCAL_VARIABLE);			
			case ObjectValue:
				return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
			case Components:
				return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
			case PrimitiveValue:
				return null;
			default: return null;
			}
		} else {				
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
		}
	}
}