package hub.sam.mof.reflection.server.impl;

import cmof.common.ReflectiveCollection;
import cmof.exception.QueryParseException;
import cmof.reflection.*;
import hub.sam.mof.reflection.*;
import hub.sam.mof.reflection.server.ServerExtent;
import hub.sam.mof.reflection.server.ServerObject;

public class ServerExtentImpl extends AbstractBridge implements ServerExtent {
    
    private final Extent localExtent;
    
    public ServerExtentImpl(Extent local) {
        this.localExtent = local;
    }

    @SuppressWarnings ("unchecked")
    public ReflectiveCollection getObject() {
        return new ServerReflectiveCollectionImpl(localExtent.getObject());
    }

    @SuppressWarnings ("unchecked")
    public ReflectiveCollection objectsOfType(ServerObject type, boolean includeSubtypes) {
        return new ServerReflectiveCollectionImpl(localExtent.objectsOfType((cmof.UmlClass)getLocalValueFromServerObject(type), includeSubtypes));
    }

    public ServerObject query(String queryString) throws QueryParseException {
        return getServerObjectFromLocalValue((ObjectImpl)localExtent.query(queryString));
    }

}
