package hub.sam.mof.remote;

import hub.sam.mof.IRepository;
import hub.sam.srmi.GenericSynchInvocationHandler;

import java.lang.reflect.Proxy;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Vector;

import cmof.reflection.Extent;

public class RemoteRepositoryImpl extends java.rmi.server.UnicastRemoteObject implements RemoteRepository {
	
	private final IRepository localRepository;

	public RemoteRepositoryImpl(final IRepository localRepository) throws RemoteException {
		super();
		try {
    		this.localRepository = (IRepository) Proxy.newProxyInstance(
    		        localRepository.getClass().getClassLoader(), new Class[] {IRepository.class},
                    new GenericSynchInvocationHandler(localRepository, MofRepositorySynchObject.getInstance()));
		}
		catch (RuntimeException e) {
		    throw new RemoteException(e.toString(), e);
		}
	}

	public RemoteExtent getExtent(String name) throws RemoteException {
		Extent localExtent = localRepository.getExtent(name);
		return localExtent == null ? null : new RemoteExtentImpl(localExtent);
	}

	public Collection<String> getExtentNames() throws RemoteException {
		return new Vector<String>(localRepository.getExtentNames());
	}	
	
	public void addRepositoryChangeListener(RemoteRepositoryChangeListener listener) throws RemoteException {
		localRepository.addRepositoryChangeListener(new LocalRepositoryChangeListenerImpl(listener));
	}

	public void removeRepositoryChangeListener(RemoteRepositoryChangeListener listener) throws RemoteException {
		localRepository.removeRepositoryChangeListener(new LocalRepositoryChangeListenerImpl(listener));
	}

	public int remoteHashCode() throws RemoteException {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((localRepository == null) ? 0 : localRepository.hashCode());
		return result;
	}

	public boolean remoteEquals(Object obj) throws RemoteException {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final RemoteRepositoryImpl other = (RemoteRepositoryImpl) obj;
		if (localRepository == null) {
			if (other.localRepository != null)
				return false;
		} else if (!localRepository.equals(other.localRepository))
			return false;
		return true;
	}
}
