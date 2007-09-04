package hub.sam.mof.remote;

import hub.sam.srmi.GenericSynchInvocationHandler;

import java.lang.reflect.Proxy;
import java.rmi.RemoteException;

import cmof.reflection.ObjectChangeListener;

public class RemoteObjectChangeListenerImpl extends java.rmi.server.UnicastRemoteObject
        implements RemoteObjectChangeListener {

	private final ObjectChangeListener localListener;
	
	public RemoteObjectChangeListenerImpl(final ObjectChangeListener localListener) throws RemoteException {
		super();
        this.localListener = (ObjectChangeListener) Proxy.newProxyInstance(
                localListener.getClass().getClassLoader(), new Class[] {ObjectChangeListener.class},
                new GenericSynchInvocationHandler(localListener, MofRepositorySynchObject.getInstance()));
	}

	public void handleDelete(RemoteObject object) throws RemoteException {
		localListener.handleDelete(LocalObjectImpl.createLocalObjectFromRemoteObject(object));
	}

	public int remoteHashCode() throws RemoteException {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((localListener == null) ? 0 : localListener.hashCode());
		return result;
	}

	public boolean remoteEquals(Object obj) throws RemoteException {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final RemoteObjectChangeListenerImpl other = (RemoteObjectChangeListenerImpl) obj;
		if (localListener == null) {
			if (other.localListener != null)
				return false;
		} else if (!localListener.equals(other.localListener))
			return false;
		return true;
	}	
}
