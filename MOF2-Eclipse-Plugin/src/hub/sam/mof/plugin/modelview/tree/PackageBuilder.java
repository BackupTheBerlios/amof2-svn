package hub.sam.mof.plugin.modelview.tree;

import cmof.Package;
import cmof.PackageMerge;

public class PackageBuilder extends NamespaceBuilder {

	@Override
	public void addChildren(Object obj, IChildManager mgr) {
		cmof.Package pkg = (cmof.Package)obj;
		for (Object ownedType: pkg.getOwnedType()) {
			mgr.addChild(ownedType);
		}
		for (Package nestedPkg: pkg.getNestedPackage()) {
			mgr.addChild(nestedPkg);
		}
		for (PackageMerge merges: pkg.getPackageMerge()) {			
			mgr.addChild(merges.getMergedPackage());
		}
		
		super.addChildren(obj, mgr);
	}

}
