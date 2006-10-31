package hub.sam.mof.reflection.server;

import cmof.common.ReflectiveCollection;
import cmof.exception.QueryParseException;

public interface ServerExtent {
    public ReflectiveCollection getObject();

    public ReflectiveCollection objectsOfType(ServerObject type,
            boolean includeSubtypes);

    /* TODO
    public ReflectiveCollection<? extends Link> linksOfType(ServerObject type);
    */

    /* TODO
    public ReflectiveCollection<? extends Object> linkedObjects(
            Association association, Object endObject, boolean end1to2direction);
    */

    /*
    public boolean linkExists(Association association, Object firstObject,
            Object secondObject);
    */

    public ServerObject query(java.lang.String queryString) throws QueryParseException;
}
