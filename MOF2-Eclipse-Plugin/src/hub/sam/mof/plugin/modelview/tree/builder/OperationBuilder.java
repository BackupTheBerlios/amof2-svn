package hub.sam.mof.plugin.modelview.tree.builder;

import org.eclipse.swt.graphics.Image;

import hub.sam.mof.plugin.modelview.Images;
import hub.sam.mof.plugin.modelview.tree.IChildManager;
import hub.sam.mof.plugin.modelview.tree.TreeObject;
import cmof.Operation;
import cmof.Parameter;

public class OperationBuilder extends NamespaceBuilder {
	@Override
	public void addChildren(Object obj, IChildManager mgr) {
		Operation operation = (Operation)obj;
		
		Object type = operation.getType();
		if (type != null) {
			TreeObject to = mgr.addChild(type);
			to.setImage(Images.getDefault().getType());
			to.setCategory(Categories.TYPE);
			int lower = operation.getLower();
			long upper = operation.getUpper();
			to.setText(operation.getType().getQualifiedName() + " - " + lower + ".." + ((upper == -1) ? "*" : new Long(upper).toString()));
		}
		
		for (Parameter parameter: operation.getParameter()) {
			mgr.addChild(parameter);
		}
		
		super.addChildren(obj, mgr);
	}
	
	@Override
	public String getText(Object obj) {
		Operation operation = (Operation)obj;
		String result = operation.getName();
		if (operation.isQuery()) {
			result += ", query";
		}		
		return result;
	}
	
	@Override
	public Image getImage(Object obj) {
		return Images.getDefault().getOperation();
	}

	@Override
	public int getCategory(Object obj) {
		return Categories.OPERATION;
	}
}
