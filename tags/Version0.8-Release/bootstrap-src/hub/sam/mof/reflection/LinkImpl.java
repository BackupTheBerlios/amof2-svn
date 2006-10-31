/*
A MOF 2 Java -- The MOF Repository tool for Java
Copyright (C) 2005 Markus Scheidgen

    This library is free software; you can redistribute it and/or modify it
under the terms of the GNU Lesser General Public License as published by the
Free Software Foundation; either version 2.1 of the License, or any later
version.

    This library is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
details.

    You should have received a copy of the GNU Lesser General Public License
along with this library; if not, write to the Free Software Foundation, Inc.,
59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
*/

package hub.sam.mof.reflection;

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
