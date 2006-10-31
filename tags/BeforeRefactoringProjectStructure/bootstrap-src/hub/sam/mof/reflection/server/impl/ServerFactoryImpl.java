package hub.sam.mof.reflection.server.impl;

import cmof.reflection.Factory;
import hub.sam.mof.reflection.*;
import hub.sam.mof.reflection.server.ServerFactory;
import hub.sam.mof.reflection.server.ServerObject;

public class ServerFactoryImpl extends AbstractBridge implements ServerFactory {

    private final Factory localFactory;
    
    public ServerFactoryImpl(Factory local) {
        this.localFactory = local;
    }
    
    public Object createFromString(ServerObject dataType, String string) {
        return serverizeLocalValue(localFactory.createFromString((cmof.DataType)getLocalValueFromServerObject(dataType), string));
    }

    public String convertToString(ServerObject dataType, Object object) {        
        return localFactory.convertToString((cmof.DataType)getLocalValueFromServerObject(dataType), deserverizeRemoteValue(object));
    }

    public ServerObject create(ServerObject metaClass) {
        return getServerObjectFromLocalValue((ObjectImpl)localFactory.create((cmof.UmlClass)getLocalValueFromServerObject(metaClass)));
    }
}
