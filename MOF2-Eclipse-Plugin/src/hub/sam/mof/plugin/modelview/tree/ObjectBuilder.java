package hub.sam.mof.plugin.modelview.tree;

import cmof.Property;
import hub.sam.mof.plugin.modelview.Images;
import hub.sam.mof.plugin.modelview.tree.builder.Categories;

public class ObjectBuilder implements IBuilder {

	public TreeObject create(Object obj, TreeParent parent,
			IBuilderFactory factory) {
		TreeObject to = new BuildTreeObject(obj, parent, this, factory);
		to.setImage(Images.getDefault().getObject());
		to.setCategory(Categories.ELEMENT);
		{
			cmof.reflection.Object theObject = (cmof.reflection.Object)obj;
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
			to.setText(result);
		}
		return to;
	}

	public void addChildren(Object obj, IChildManager mgr) {		
		cmof.reflection.Object theObject = (cmof.reflection.Object)obj;
		
		cmof.UmlClass metaClass = theObject.getMetaClass();
		TreeObject metaClassTO = mgr.addChild(metaClass);
		metaClassTO.setImage(Images.getDefault().getMetaClass());
		metaClassTO.setCategory(Categories.METACLASS);
		mgr.addChild(new ComponentsTreeObject(theObject, mgr.getParent(), mgr.getFactory()));
		
		cmof.reflection.Object container = theObject.container();
		if (container != null) {				
			TreeObject component = mgr.addChild(theObject.container());
			component.setText("<container>: " + component.getText());
			component.setCategory(Categories.METACLASS);
		}
		for (cmof.NamedElement property: metaClass.getMember()) {
			if (property instanceof Property) {
				mgr.addChild(new PropertyTreeObject((Property)property, theObject, mgr.getParent(), mgr.getFactory()));
			}
		}
	}
}
