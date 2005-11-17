package hub.sam.mof.plugin.modelview.tree.builder;

import hub.sam.mof.plugin.modelview.Images;
import hub.sam.mof.plugin.modelview.tree.IChildManager;
import hub.sam.mof.plugin.modelview.tree.TreeObject;

import org.eclipse.swt.graphics.Image;
import cmof.Property;

public class PropertyBuilder extends TypedElementBuilder {

	@Override
	public void addChildren(Object obj, IChildManager mgr) {
		Property property = (Property)obj;
		TreeObject to = null;
		
		for (Property redef: property.getRedefinedProperty()) {
			to = mgr.addChild(redef);
			to.setImage(Images.getDefault().getRedefinition());
			to.setCategory(Categories.REDEFINITION);
			to.setText("(from " + redef.getNamespace().getQualifiedName() + ") " + to.getText());
		}
		for (Property subset: property.getSubsettedProperty()) {
			to = mgr.addChild(subset);
			to.setImage(Images.getDefault().getSubsets());
			to.setCategory(Categories.SUBSETS);
			to.setText("(from " + subset.getNamespace().getQualifiedName() + ") " + to.getText());
		}
		Property opposite = property.getOpposite();
		if (opposite != null) {
			to = mgr.addChild(opposite);
			to.setCategory(Categories.OPPOSITE);
			to.setImage(Images.getDefault().getAssociation());
			to.setText("(from " + opposite.getNamespace().getQualifiedName() + ") " + to.getText());
		}
		super.addChildren(obj, mgr);
	}
	
	@Override
	public String getText(Object obj) {
		Property property = (Property)obj;
		String result = super.getText(obj);
		if (property.isDerived()) {
			result = "/" + result;
		}
		if (property.isDerivedUnion()) {
			result += ", union";
		}
		
		return result;
	}
	
	@Override
	public Image getImage(Object obj) {
		return Images.getDefault().getProperty();
	}

	@Override
	public int getCategory(Object obj) {
		return Categories.PROPERTY;
	}

}
