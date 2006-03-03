package hub.sam.mof.reflection.client;

import cmof.common.ReflectiveCollection;

public interface ClientObject extends cmof.reflection.Object {
    
    public java.lang.Object get(String propertyName);
    
    public java.lang.Object invokeOperation(String opName, java.lang.Object[] args);
    
    public void set(String propertyName, java.lang.Object value);
         
    public ClientObject container();
    
    public ClientObject getOutermostContainer();
    
    public ReflectiveCollection<? extends ClientObject> getComponents();
}
