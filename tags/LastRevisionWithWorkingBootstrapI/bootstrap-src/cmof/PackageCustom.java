package cmof;

import cmof.common.ReflectiveCollection;
import hub.sam.mof.instancemodel.*;
import cmofimpl.reflection.*;
import cmofimpl.util.SetImpl;

public class PackageCustom extends cmof.PackageDlg {
    public PackageCustom(ClassInstance instance, ExtentImpl extent) {
        super(instance, extent);
    }
    
    public PackageCustom(Identifier a1, Identifier a2, String a3, String[] a4) {
        super(a1, a2, a3, a4);
    }
  
    public ReflectiveCollection<? extends NamedElement> getMember() {
        ReflectiveCollection<? extends NamedElement> members = new SetImpl<NamedElement>(getOwnedMember());
        ReflectiveCollection<? extends NamedElement> importedMembers = getImportedMember();
        members.addAll(importedMembers);
        return members;
    }
}
