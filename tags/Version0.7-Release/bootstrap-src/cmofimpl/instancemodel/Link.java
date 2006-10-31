package cmofimpl.instancemodel;

import cmof.common.ReflectiveCollection;
import core.abstractions.structuralfeatures.*;

public interface Link extends Instance {
    
    public ReflectiveCollection<LinkSlot> getSlot();
    
    public cmof.Association getAssociation();
    
    public LinkSlot getSlot(StructuralFeature definingFeature);
}
