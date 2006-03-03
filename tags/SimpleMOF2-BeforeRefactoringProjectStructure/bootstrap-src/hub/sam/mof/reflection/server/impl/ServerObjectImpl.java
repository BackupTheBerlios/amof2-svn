package hub.sam.mof.reflection.server.impl;

import cmof.common.ReflectiveCollection;
import cmof.common.ReflectiveSequence;
import cmof.exception.IllegalArgumentException;
import cmof.reflection.Argument;
import hub.sam.mof.reflection.*;
import hub.sam.mof.reflection.server.ServerObject;

public class ServerObjectImpl extends AbstractBridge implements ServerObject {

    protected final ObjectImpl localObject;
    
    public ServerObjectImpl(ObjectImpl local) {
    	this.localObject = local;
    }
    
    public ServerObject getMetaClass() {
        return ((ObjectImpl)localObject.getMetaClass()).getServerObject();
    }

    public ServerObject container() {
        return ((ObjectImpl)localObject.container()).getServerObject();
    }

    public Object get(ServerObject property) throws IllegalArgumentException {
        Object localValue = localObject.get((cmof.Property)((ServerObjectImpl)property).localObject);
        return serverizeLocalValue(localValue);        
    }

    public void set(ServerObject property, Object value)
            throws ClassCastException, IllegalArgumentException {
        localObject.set((cmof.Property)((ServerObjectImpl)property).localObject, deserverizeRemoteValue(value));
    }

    public boolean isSet(ServerObject property)
            throws java.lang.IllegalArgumentException {
        return localObject.isSet((cmof.Property)((ServerObjectImpl)property).localObject);
    }

    public void unset(ServerObject property)
            throws java.lang.IllegalArgumentException {
        localObject.unset((cmof.Property)((ServerObjectImpl)property).localObject);
    }

    public void delete() {
        localObject.delete();
    }

    public Object invokeOperation(ServerObject op,
            ReflectiveSequence<Argument> arguments) {
        return null; //TODO (complicated: clientize arguments)
    }

    public boolean isInstanceOfType(ServerObject type, boolean includeSubTypes) {
        return localObject.isInstanceOfType((cmof.UmlClass)((ServerObjectImpl)type).localObject, includeSubTypes);
    }

    public ServerObject getOutermostContainer() {
        return ((ObjectImpl)localObject.getOutermostContainer()).getServerObject();
    }

    @SuppressWarnings ("unchecked")
    public ReflectiveCollection getComponents() {
        return new ServerReflectiveCollectionImpl(localObject.getComponents());
    }

    public Object get(String propertyName) {
        return serverizeLocalValue(localObject.get(propertyName));
    }

    public Object invokeOperation(String opName, Object[] args) {
        return null; // TODO
    }

    public void set(String propertyName, Object value) {
        localObject.set(propertyName, deserverizeRemoteValue(value));
    }
}
