package hub.sam.mof.plugin.modelview.tree.builder;

import hub.sam.mof.plugin.modelview.Images;
import hub.sam.mof.plugin.modelview.tree.IChildManager;
import hub.sam.mof.plugin.modelview.tree.TreeObject;
import cmof.MultiplicityElement;
import cmof.NamedElement;
import cmof.TypedElement;

public class TypedElementBuilder extends ElementBuilder {
	@Override
	public void addChildren(Object obj, IChildManager mgr) {
		TypedElement typedElement = (TypedElement)obj;
		
		TreeObject to = mgr.addChild(typedElement.getType());
		to.setImage(Images.getDefault().getType());
		to.setCategory(Categories.TYPE);
		to.setText(typedElement.getType().getQualifiedName());
		
		super.addChildren(obj, mgr);
	}
	
	@Override
	public String getText(Object obj) {
		MultiplicityElement multiplicityElement = (MultiplicityElement)obj;
		String result = null;
		if (obj instanceof NamedElement) {
			result = ((NamedElement)obj).getName();
		}
		if (result == null) {
			result = "<unnamed>";
		}
	
		int lower = multiplicityElement.getLower();
		long upper = multiplicityElement.getUpper();
		result += " - " + lower + ".." + ((upper == -1) ? "*" : new Long(upper).toString());
		
		return result;
	}

	@Override
	public int getCategory(Object obj) {
		return 1000;
	}
}
