package hub.sam.mof.plugin.modelview.tree.builder;

import hub.sam.mof.plugin.modelview.Images;
import hub.sam.mof.plugin.modelview.tree.IChildManager;

import org.eclipse.swt.graphics.Image;

import cmof.Operation;
import cmof.Property;
import cmof.UmlClass;

public class ClassBuilder extends ClassifierBuilder {

	@Override
	public void addChildren(Object obj, IChildManager mgr) {
		for(Property property: ((UmlClass)obj).getOwnedAttribute()) {
			mgr.addChild(property);
		}
		for(Operation op: ((UmlClass)obj).getOwnedOperation()) {
			mgr.addChild(op);
		}
		super.addChildren(obj, mgr);
	}

	
	
	@Override
	public String getText(Object element) {
		UmlClass theClass = (UmlClass)element;
		String result = theClass.getName();
		if (theClass.isAbstract()) {
			result += ", abstract";
		}
		return result;
	}

	@Override
	public Image getImage(Object element) {
		return Images.getDefault().getClassIcon();
	}
}
