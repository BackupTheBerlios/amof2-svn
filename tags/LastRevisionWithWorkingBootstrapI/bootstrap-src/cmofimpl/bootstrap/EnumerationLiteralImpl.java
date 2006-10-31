package cmofimpl.bootstrap;

import hub.sam.mof.instancemodel.*;
import cmofimpl.reflection.ExtentImpl;

public class EnumerationLiteralImpl extends cmof.EnumerationLiteralImpl {

    public EnumerationLiteralImpl(ClassInstance instance, ExtentImpl extent) {
        super(instance, extent);
    }

    public String getName() {
        return (String)get("name");
    }
}
