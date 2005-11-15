package hub.sam.mof.plugin.modelview.tree;

public class CMOFBuilderFactory implements IBuilderFactory {

	public IBuilder getBuilder(Object obj) {
		if (obj instanceof cmof.Package) {
			return new PackageBuilder();
		}
		return null;
	}

}
