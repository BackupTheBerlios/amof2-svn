package hub.sam.mof.reflection.server;

import cmof.common.*;
import cmof.reflection.Argument;

public interface ServerObject {

	public ServerObject getMetaClass();

	public ServerObject container();

	public java.lang.Object get(ServerObject property)
			throws cmof.exception.IllegalArgumentException;

	public void set(ServerObject property, java.lang.Object value) 
            throws ClassCastException, cmof.exception.IllegalArgumentException;

	public boolean isSet(ServerObject property) throws IllegalArgumentException;

	public void unset(ServerObject property) throws IllegalArgumentException;

	public boolean equals(java.lang.Object element);

	public void delete();

	public java.lang.Object invokeOperation(ServerObject op, ReflectiveSequence<Argument> arguments);

	public boolean isInstanceOfType(ServerObject type, boolean includeSubTypes);

	public Object getOutermostContainer();

	public ReflectiveCollection getComponents();
    
    public java.lang.Object get(String propertyName);
    
    public java.lang.Object invokeOperation(String opName, java.lang.Object[] args);
    
    public void set(String propertyName, java.lang.Object value);
    
}
