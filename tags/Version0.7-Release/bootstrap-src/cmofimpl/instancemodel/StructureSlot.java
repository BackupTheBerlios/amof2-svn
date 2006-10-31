package cmofimpl.instancemodel;

import core.abstractions.instances.*;

public interface StructureSlot extends Slot, Cloneable {
    
    public ElementInstance getOwningInstance();
}
