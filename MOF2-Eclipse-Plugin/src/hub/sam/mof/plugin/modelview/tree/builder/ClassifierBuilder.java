package hub.sam.mof.plugin.modelview.tree.builder;

import hub.sam.mof.plugin.modelview.Images;
import hub.sam.mof.plugin.modelview.tree.IChildManager;
import hub.sam.mof.plugin.modelview.tree.TreeObject;
import cmof.Classifier;

public class ClassifierBuilder extends NamespaceBuilder {
	@Override
	public void addChildren(Object obj, IChildManager mgr) {		
		for(cmof.Classifier redefines: ((Classifier)obj).getGeneral()) {
			TreeObject to = mgr.addChild(redefines);
			to.setImage(Images.getDefault().getRedefinition());
			to.setCategory(Categories.SUPERCLASS);
			to.setText(redefines.getNamespace().getQualifiedName(), to.getText());
		}
		super.addChildren(obj, mgr);
	}
	

	@Override
	public int getCategory(Object obj) {
		return Categories.CLASS;
	}
}
