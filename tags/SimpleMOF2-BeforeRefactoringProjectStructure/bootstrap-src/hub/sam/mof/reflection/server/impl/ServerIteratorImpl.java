package hub.sam.mof.reflection.server.impl;

import java.util.Iterator;
import hub.sam.mof.reflection.server.*;

public class ServerIteratorImpl<E> extends AbstractBridge implements ServerIterator {

	private final Iterator<E> local;
	
	protected ServerIteratorImpl(Iterator<E> local) {
		this.local = local;
	}
	
	public boolean hasNext() {
		return local.hasNext();
	}

	public Object next() {
		return serverizeLocalValue(local.next());
	}

	public void remove() {
		local.remove();
	}
}
