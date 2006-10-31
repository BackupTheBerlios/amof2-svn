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

package hub.sam.mof.codegeneration.wrapper;

import cmof.DataType;
import cmof.Parameter;
import cmof.Type;

public class ParameterWrapper extends TypedElementWrapper {
	final private Parameter parameter;
    ParameterWrapper(Parameter parameter) {
        this.parameter = parameter;
    }
  
    @Override
	public String getName() {
        return javaMapping.getJavaIdentifier(parameter);
    }
    @Override
	public Type getUmlType() {
        return parameter.getType();
    } 
    public boolean isList() {
        return parameter.isOrdered();
    }
    public String getType() {
        String typeName = getPlainJavaType();            
        if (parameter.getUpper() == 1) {
            return typeName;
        } else {
            if (!(parameter.getType() instanceof DataType)) {
                typeName = "? extends " + typeName;
            }
            if (isList()) {
                return cmof.common.ReflectiveSequence.class.getCanonicalName() + "<" + typeName + ">";
            } else {
                return cmof.common.ReflectiveCollection.class.getCanonicalName() + "<" + typeName + ">";
            }
        }
    }
}
