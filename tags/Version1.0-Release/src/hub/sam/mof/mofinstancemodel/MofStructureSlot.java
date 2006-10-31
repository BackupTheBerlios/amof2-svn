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

import cmof.Property;
import cmof.UmlClass;
import hub.sam.mof.instancemodel.StructureSlot;
import hub.sam.mof.instancemodel.ValueSpecificationImpl;

import java.util.List;
import java.util.Vector;

public class MofStructureSlot extends StructureSlot<UmlClass,Property,java.lang.Object> {

    private MofValueSpecificationList values;

	protected MofStructureSlot(MofClassInstance owner, Property feature) {
        super(feature, owner);
		this.values = new MofValueSpecificationList(owner, this);
	}

    @Override
	public MofValueSpecificationList getValuesAsList() {
        return values;
    }

    @Override
	protected List<ValueSpecificationImpl<UmlClass,Property,java.lang.Object>> createValues() {    	
    	return null;
    }

    @SuppressWarnings("unchecked")
	@Override
	public List<ValueSpecificationImpl<UmlClass,Property,java.lang.Object>> getValues() {
        List result = new Vector();
        for (Object o: getValuesAsList()) {
            result.add(o);
        }
        return result;
    }

    public Property getDefiningFeature() {
        return getProperty();
    }

    @Override
	protected void myFinalize() {
        super.myFinalize();
        values.myFinalize();
    }
}
