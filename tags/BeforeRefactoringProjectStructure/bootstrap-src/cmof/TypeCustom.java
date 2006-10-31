package cmof;

import hub.sam.mof.instancemodel.ClassInstance;
import hub.sam.mof.reflection.*;

public class TypeCustom extends cmof.TypeDlg {

    public TypeCustom(ClassInstance instance, ExtentImpl extent) {
        super(instance, extent);
    }

    public TypeCustom(Identifier id, Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }
    
    @Override
	public boolean conformsTo(core.abstractions.typedelements.Type other) {
        return self == other;
    }
}

