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

package hub.sam.mof.mofinstancemodel;

import java.util.*;
import cmof.*;
import hub.sam.mof.instancemodel.ValueSpecification;
import hub.sam.mof.instancemodel.*;

public class MofLinkSlot {

	private final MofLink owner;
    private final Property property;
    private final List<ValueSpecification<UmlClass,Property,java.lang.Object>> values = new Vector<ValueSpecification<UmlClass,Property,java.lang.Object>>();
	
	public MofLinkSlot(MofLink owner, Property feature, InstanceValue<UmlClass,Property,java.lang.Object> value) {
        this.property = feature;
		this.owner = owner;		
        values.add(value);
	}
	
    public List<? extends ValueSpecification<UmlClass,Property,java.lang.Object>> getValue() {
        return values;
    }
	
    public MofLink getOwningInstance() {
        return owner;
    }
    
    public Property getDefiningFeature() {
        return property;
    }
}
