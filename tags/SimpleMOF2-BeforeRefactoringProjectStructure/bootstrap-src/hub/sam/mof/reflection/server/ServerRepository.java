package hub.sam.mof.reflection.server;

import java.util.Collection;

public interface ServerRepository {

    public ServerExtent getExtent(String name);
    
    public Collection getExtentNames();

    public ServerExtent createExtent(String name);
    
    public void deleteExtent(String name);
    
    public ServerFactory createFactory(ServerExtent forExtent, ServerObject forPackage);     
 
}
