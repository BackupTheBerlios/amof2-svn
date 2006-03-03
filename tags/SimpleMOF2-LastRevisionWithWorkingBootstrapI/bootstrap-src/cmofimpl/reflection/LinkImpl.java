package cmofimpl.reflection;

import cmof.*;
import cmof.reflection.Object;
import cmof.reflection.Link;

public class LinkImpl implements Link {
	private final Association association;
	private final Object e1,e2;
	
	public LinkImpl(Association association, Object e1, Object e2) {
		this.association = association;
		this.e1 = e1;
		this.e2 = e2;
	}
	
    public boolean equals(Link otherLink) {
        return false;
    }

    public void delete() {
    }

    public Association getAssociation() {
        return null;
    }
}
