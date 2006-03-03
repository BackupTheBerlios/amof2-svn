package hub.sam.mof.reflection.server;

public interface ServerFactory {
        
    public java.lang.Object createFromString(ServerObject dataType, String string);

    public String convertToString(ServerObject dataType, java.lang.Object object);

    public ServerObject create(ServerObject metaClass);

    /* TODO
    public cmof.reflection.Link createLink(ServerObject association,
            ServerObject firstObject,
            ServerObject secondObject);
            */
}
