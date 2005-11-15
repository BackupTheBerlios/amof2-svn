package hub.sam.mof.plugin.modelview.tree;

import cmof.Property;
import hub.sam.mof.plugin.modelview.Images;

public class ObjectBuilder implements IBuilder {

	public TreeObject create(Object obj, TreeParent parent,
			IBuilderFactory factory) {
		TreeObject to = new BuildTreeObject(obj, parent, this, factory);
		to.setImage(Images.getDefault().getObject());
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
		mgr.addChild(new ComponentsTreeObject(theObject, mgr.getParent(), mgr.getFactory()));
		for (cmof.NamedElement property: metaClass.getMember()) {
			if (property instanceof Property) {
				mgr.addChild(new PropertyTreeObject((Property)property, theObject, mgr.getParent(), mgr.getFactory()));
			}
		}
	}
}
