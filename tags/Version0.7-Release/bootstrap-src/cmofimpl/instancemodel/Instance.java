package cmofimpl.instancemodel;

import core.abstractions.instances.*;
import core.abstractions.structuralfeatures.*;

public interface Instance extends core.abstractions.instances.InstanceSpecification {
    public Slot getSlot(StructuralFeature definingFeature);
}
