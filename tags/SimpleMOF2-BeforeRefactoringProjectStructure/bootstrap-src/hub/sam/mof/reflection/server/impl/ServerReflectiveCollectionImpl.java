package hub.sam.mof.reflection.server.impl;

import hub.sam.mof.reflection.server.*;
import cmof.common.ReflectiveCollection;

public class ServerReflectiveCollectionImpl<E> extends AbstractBridge 
		implements ServerReflectiveCollection {
	
	private final ReflectiveCollection<E> localCollection;
	
	protected ReflectiveCollection<E> local() {
		return localCollection;
	}
	
	public ServerReflectiveCollectionImpl(ReflectiveCollection<E> local) {
		this.localCollection = local;
	}
	
	public boolean add(Object element) {
		return local().add(deserverizeRemoteValue(element));
	}

	public boolean contains(Object element) {		
		return local().contains(deserverizeRemoteValue(element));
	}

	public boolean remove(Object element) {
		return local().remove(deserverizeRemoteValue(element));
	}

	public boolean addAll(Iterable elements) {
		boolean result = false;
		for (Object element: elements) {
			result |= local().add(deserverizeRemoteValue(element));
		}
		return result;
	}

	public boolean containsAll(Iterable elements) {
		boolean result = true;		
		for (Object element: elements) {
			result &= local().contains(deserverizeRemoteValue(element));
		}
		return result;
	}

	public boolean removeAll(Iterable elements) {
		boolean result = false;
		for (Object element: elements) {
			result |= local().remove(deserverizeRemoteValue(element));
		}
		return result;
	}

	public int size() {
		return local().size();
	}

	public ServerIterator iterator() {
		return new ServerIteratorImpl<E>(local().iterator());			
	}

}
