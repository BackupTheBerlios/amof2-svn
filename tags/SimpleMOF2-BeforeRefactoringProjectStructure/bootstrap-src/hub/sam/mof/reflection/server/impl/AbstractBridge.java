package hub.sam.mof.reflection.server.impl;

import hub.sam.mof.reflection.ObjectImpl;
import hub.sam.mof.reflection.server.ServerObject;
import cmof.common.ReflectiveCollection;
import cmof.common.ReflectiveSequence;

public abstract class AbstractBridge {
	
	protected ObjectImpl getLocalValueFromServerObject(ServerObject serverObject) {
		return ((ServerObjectImpl)serverObject).localObject;
	}
	
	protected ServerObject getServerObjectFromLocalValue(ObjectImpl localValue) {
		return localValue.getServerObject();
	}
    
	@SuppressWarnings("unchecked")
	protected Object serverizeLocalValue(Object localValue) {         
        if (localValue instanceof ObjectImpl) {
            return getServerObjectFromLocalValue((ObjectImpl)localValue);
        } else if (localValue instanceof ReflectiveCollection) {
        	if (localValue instanceof ReflectiveSequence) {
        		return new ServerReflectiveSequenceImpl((ReflectiveSequence)localValue);
        	} else {
        		return new ServerReflectiveCollectionImpl((ReflectiveCollection)localValue);
        	}            
        } else if (localValue instanceof java.io.Serializable) {
            return localValue; 
        } else {
            throw new RuntimeException("assert");
        }  
    }
    
    protected Object deserverizeRemoteValue(Object remoteValue) {
        if (remoteValue instanceof ServerObjectImpl) {
            return getLocalValueFromServerObject((ServerObject)remoteValue);
        } else if (remoteValue instanceof java.io.Serializable) {
            return remoteValue;
        } else {
            throw new RuntimeException("assert");
        }
    }
}
