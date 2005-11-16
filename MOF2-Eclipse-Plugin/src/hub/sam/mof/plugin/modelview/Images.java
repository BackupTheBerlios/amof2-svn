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
	
	private final Image thePackage;
	private final Image theClass;
	private final Image property;
	private final Image operation;
	private final Image association;
	private final Image redefinition;
	private final Image subsets;
	private final Image type;
	private final Image infos;
	
	private Images() {
		this.repository = ImageDescriptor.createFromFile(ModelView.class, "repository.gif").createImage();
		this.extent = ImageDescriptor.createFromFile(ModelView.class, "extent.gif").createImage();
		this.object = ImageDescriptor.createFromFile(ModelView.class, "object.gif").createImage();
		this.metaClass = ImageDescriptor.createFromFile(ModelView.class, "metaClass.gif").createImage();
		this.attribute = ImageDescriptor.createFromFile(ModelView.class, "attribute.gif").createImage();
		this.components = ImageDescriptor.createFromFile(ModelView.class, "components.gif").createImage();	
		
		this.thePackage = ImageDescriptor.createFromFile(ModelView.class, "package.gif").createImage();
		this.theClass = ImageDescriptor.createFromFile(ModelView.class, "class.gif").createImage();
		this.property = ImageDescriptor.createFromFile(ModelView.class, "property.gif").createImage();
		this.operation = ImageDescriptor.createFromFile(ModelView.class, "operation.gif").createImage();
		this.association = ImageDescriptor.createFromFile(ModelView.class, "association.gif").createImage();
		this.redefinition = ImageDescriptor.createFromFile(ModelView.class, "redefinition.gif").createImage();
		this.subsets = ImageDescriptor.createFromFile(ModelView.class, "subsets.gif").createImage();				
		this.type = ImageDescriptor.createFromFile(ModelView.class, "type.gif").createImage();
		this.infos = ImageDescriptor.createFromFile(ModelView.class, "infos.gif").createImage();
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

	public Image getAssociation() {
		return association;
	}

	public Image getOperation() {
		return operation;
	}

	public Image getProperty() {
		return property;
	}

	public Image getRedefinition() {
		return redefinition;
	}

	public Image getSubsets() {
		return subsets;
	}

	public Image getClassIcon() {
		return theClass;
	}

	public Image getPackage() {
		return thePackage;
	}

	public Image getType() {
		return type;
	}

	public Image getInfos() {
		return infos;
	}
	
}
