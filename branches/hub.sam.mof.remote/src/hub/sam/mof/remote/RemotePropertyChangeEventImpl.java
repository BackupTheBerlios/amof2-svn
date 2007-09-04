package hub.sam.mof.remote;

import hub.sam.srmi.GenericSynchInvocationHandler;

import java.beans.PropertyChangeEvent;
import java.lang.reflect.Proxy;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemotePropertyChangeEventImpl extends UnicastRemoteObject implements RemotePropertyChangeEvent {
	
	private final IPropertyChangeEvent localEvent;
	
	private class MyPropertyChangeEvent implements IPropertyChangeEvent {
	    
	    private PropertyChangeEvent event;

        public MyPropertyChangeEvent(PropertyChangeEvent event) {
	        this.event = event;
	    }

        public Object getNewValue() {
            return event.getNewValue();
        }

        public Object getOldValue() {
            return event.getOldValue();
        }

        public Object getPropagationId() {
            return event.getPropagationId();
        }

        public String getPropertyName() {
            return event.getPropertyName();
        }

        public Object getSource() {
            return event.getSource();
        }

        public void setPropagationId(Object propagationId) {
            event.setPropagationId(propagationId);
        }
	    
	}
	
	public RemotePropertyChangeEventImpl(final PropertyChangeEvent localEvent) throws RemoteException {
		super();
		IPropertyChangeEvent proxy = new MyPropertyChangeEvent(localEvent);
        this.localEvent = (IPropertyChangeEvent) Proxy.newProxyInstance(
                this.getClass().getClassLoader(), new Class[] {IPropertyChangeEvent.class},
                new GenericSynchInvocationHandler(proxy, MofRepositorySynchObject.getInstance()));
	}

	public String getName() throws RemoteException {
		return localEvent.getPropertyName();
	}

	public Object getNewValue() throws RemoteException {
		return RemoteObjectImpl.createRemoteJavaObjectFromLocalJavaObject(localEvent.getNewValue());
	}

	public Object getOldValue() throws RemoteException {
		return RemoteObjectImpl.createRemoteJavaObjectFromLocalJavaObject(localEvent.getOldValue());
	}

	public Object getSource() throws RemoteException {
		return RemoteObjectImpl.createRemoteJavaObjectFromLocalJavaObject(localEvent.getSource());
	}

}
