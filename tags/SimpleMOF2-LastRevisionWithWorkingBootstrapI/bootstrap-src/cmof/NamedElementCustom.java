package cmof;

import hub.sam.mof.instancemodel.*;
import cmofimpl.reflection.ExtentImpl;
import cmofimpl.reflection.Identifier;

public class NamedElementCustom extends NamedElementDlg {
    public NamedElementCustom(ClassInstance instance, ExtentImpl extent) {
        super(instance, extent);
    }
    
    public NamedElementCustom(Identifier a1, Identifier a2, String a3, String[] a4) {
        super(a1, a2, a3, a4);
    }
    
    public String getQualifiedName() {        
        String result = getName();
        Namespace ns = getNamespace();
        while (ns != null) {
            result = ns.getName() + "." + result;
            ns = ns.getNamespace();
        }
        return result;
    }
}
