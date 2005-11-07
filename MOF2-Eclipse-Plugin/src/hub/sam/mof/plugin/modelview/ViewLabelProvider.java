/**
 * 
 */
package hub.sam.mof.plugin.modelview;

import hub.sam.mof.plugin.modelview.tree.TreeObject;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

class ViewLabelProvider extends LabelProvider {

	private final Image repository;
	private final Image extent;
	private final Image object;
	private final Image metaClass;
	private final Image attribute;
	private final Image components;
	
	@SuppressWarnings("unused")
	private final ModelView view;

	public ViewLabelProvider(ModelView view) {
		this.view = view;
		this.repository = ImageDescriptor.createFromFile(ModelView.class, "repository.gif").createImage();
		this.extent = ImageDescriptor.createFromFile(ModelView.class, "extent.gif").createImage();
		this.object = ImageDescriptor.createFromFile(ModelView.class, "object.gif").createImage();
		this.metaClass = ImageDescriptor.createFromFile(ModelView.class, "metaClass.gif").createImage();
		this.attribute = ImageDescriptor.createFromFile(ModelView.class, "attribute.gif").createImage();
		this.components = ImageDescriptor.createFromFile(ModelView.class, "components.gif").createImage();
		
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
				return repository;
				//return org.eclipse.jdt.ui.JavaUI.getSharedImages().getImage(
				//		org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_JAR);
			case Extent:
				return extent;
				//return org.eclipse.jdt.ui.JavaUI.getSharedImages().getImage(
				//		org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_PACKAGE);
			case Object:
				return object;
				//return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
			case MetaObject:
				return metaClass;
				//return org.eclipse.jdt.ui.JavaUI.getSharedImages().getImage(
				//		org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_CLASS);			
			case Attribute:
				return attribute;
				//return org.eclipse.jdt.ui.JavaUI.getSharedImages().getImage(
				//		org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_LOCAL_VARIABLE);			
			case ObjectValue:
				return object;
				//return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
			case Components:
				return components;
				//return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
			case PrimitiveValue:
				return null;
			default: return null;
			}
		} else {				
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
		}
	}
}