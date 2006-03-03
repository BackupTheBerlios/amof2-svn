package cmofimpl.reflection;

import cmof.reflection.*;

public abstract class PackageImpl extends NamedElement implements cmof.reflection.Package {

    protected PackageImpl(String name) {
        super(name);
    }

    public Factory getFactory() {
        return null;
    }
}
