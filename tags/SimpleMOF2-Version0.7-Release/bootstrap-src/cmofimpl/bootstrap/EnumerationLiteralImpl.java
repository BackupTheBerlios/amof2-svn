package cmofimpl.bootstrap;

import cmofimpl.instancemodel.ClassInstanceImpl;
import cmofimpl.reflection.ExtentImpl;

public class EnumerationLiteralImpl extends cmof.EnumerationLiteralImpl {

    public EnumerationLiteralImpl(ClassInstanceImpl instance, ExtentImpl extent) {
        super(instance, extent);
    }

    public String getName() {
        return (String)get("name");
    }
}
