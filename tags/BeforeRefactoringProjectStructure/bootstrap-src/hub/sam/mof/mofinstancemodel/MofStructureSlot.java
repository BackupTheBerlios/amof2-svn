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
import hub.sam.mof.instancemodel.*;

public class MofStructureSlot extends StructureSlot<UmlClass,Property,java.lang.Object> {

	private MofClassInstance owner;       
    private MofValueSpecificationList values;
    private Property feature;
    	
	protected MofStructureSlot(MofClassInstance owner, Property feature) {
        super(feature);
		this.owner = owner;
        this.feature = feature;
		this.values = new MofValueSpecificationList(owner, this);
	}
    
    @Override
	public MofValueSpecificationList getValuesAsList() {
        return values;
    }

    @SuppressWarnings("unchecked")
	@Override
	public List<ValueSpecificationImpl<UmlClass,Property,java.lang.Object>> getValues() {
        List result = new Vector();
        for (Object o: values) {
            result.add(o);
        }
        return result;
    }
    
    public MofClassInstance getOwningInstance() {
        return owner;
    }
    
    public Property getDefiningFeature() {
        return feature;
    } 
    
    @Override
	protected void finalize() {
        super.finalize();
        values.clear();
        owner = null;
        feature = null;
    }
}
