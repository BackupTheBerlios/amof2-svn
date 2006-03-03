package hub.sam.mof.reflection.server.impl;

import cmof.common.ReflectiveSequence;
import hub.sam.mof.reflection.server.ServerReflectiveSequence;

public class ServerReflectiveSequenceImpl<E> extends
		ServerReflectiveCollectionImpl<E> implements ServerReflectiveSequence {

	private final ReflectiveSequence<E> localSequence;
	
	@Override
	protected ReflectiveSequence<E> local() {
		return localSequence;
	}
	
	public ServerReflectiveSequenceImpl(ReflectiveSequence<E> local) {
		super(local);
		this.localSequence = local;
	}
	
	public Object get(int index) {
		return deserverizeRemoteValue(local().get(index));
	}

	public void set(int index, Object element) {
		local().set(index, serverizeLocalValue(element));		
	}

	public void add(int index, Object element) {
		local().add(index, serverizeLocalValue(element));
	}

	public void addAll(int index, Iterable elements) {
		for (Object element: elements) {
			local().add(serverizeLocalValue(element));
		}		
	}

	public void remove(int index) {
		local().remove(index);
	}

	public ServerReflectiveSequence subList(int from, int to) {
		return new ServerReflectiveSequenceImpl<E>(local().subList(from, to));
	}

}
