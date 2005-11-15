package hub.sam.mof.plugin.modelview;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public class Images {

	private final static Images defaultInstance = new Images();
	public static Images getDefault() {
		return defaultInstance;
	}
	
	private final Image repository;
	private final Image extent;
	private final Image object;
	private final Image metaClass;
	private final Image attribute;
	private final Image components;	
	
	private Images() {
		this.repository = ImageDescriptor.createFromFile(ModelView.class, "repository.gif").createImage();
		this.extent = ImageDescriptor.createFromFile(ModelView.class, "extent.gif").createImage();
		this.object = ImageDescriptor.createFromFile(ModelView.class, "object.gif").createImage();
		this.metaClass = ImageDescriptor.createFromFile(ModelView.class, "metaClass.gif").createImage();
		this.attribute = ImageDescriptor.createFromFile(ModelView.class, "attribute.gif").createImage();
		this.components = ImageDescriptor.createFromFile(ModelView.class, "components.gif").createImage();	
	}

	public Image getAttribute() {
		return attribute;
	}

	public Image getComponents() {
		return components;
	}

	public Image getExtent() {
		return extent;
	}

	public Image getMetaClass() {
		return metaClass;
	}

	public Image getObject() {
		return object;
	}

	public Image getRepository() {
		return repository;
	}
	
}
