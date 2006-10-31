package cmofimpl.instancemodel;

import cmof.common.ReflectiveCollection;
import core.abstractions.structuralfeatures.*;

public interface ElementInstance extends Instance {
    
    public ReflectiveCollection<StructureSlot> getSlot();
    
    public StructureSlot getSlot(StructuralFeature definingFeature);
}
