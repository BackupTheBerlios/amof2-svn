package hub.sam.mof.plugin.modelview.tree;

import cmof.ElementImport;
import cmof.Namespace;

public class NamespaceBuilder extends ElementBuilder {

	public void addChildren(Object obj, IChildManager mgr) {	
		Namespace ns = (Namespace)obj;
		for (ElementImport elementImport: ns.getElementImport()) {
			mgr.addChild(elementImport.getImportedElement());
		}
	}
}
