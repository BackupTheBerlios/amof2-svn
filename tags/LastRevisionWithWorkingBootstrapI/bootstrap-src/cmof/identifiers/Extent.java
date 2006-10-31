package cmof.identifiers;

import cmof.common.ReflectiveSequence;

public interface Extent {
        
    public ReflectiveSequence<? extends cmof.reflection.Object> object();
    public boolean useContainment();	 	    
}
