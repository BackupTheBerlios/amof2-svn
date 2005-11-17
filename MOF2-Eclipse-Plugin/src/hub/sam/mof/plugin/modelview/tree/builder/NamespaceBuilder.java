package hub.sam.mof.plugin.modelview.tree.builder;

import hub.sam.mof.plugin.modelview.tree.IChildManager;
import cmof.Constraint;
import cmof.ElementImport;
import cmof.Namespace;

public abstract class NamespaceBuilder extends ElementBuilder {

	@Override
	public void addChildren(Object obj, IChildManager mgr) {	
		Namespace ns = (Namespace)obj;
		
		for (Constraint constraint: ns.getOwnedRule()) {
			mgr.addChild(constraint);
		}
		for (ElementImport elementImport: ns.getElementImport()) {
			mgr.addChild(elementImport.getImportedElement());
		}
		super.addChildren(obj, mgr);
	}
}
