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

import hub.sam.mof.codegeneration.GenerationException;
import cmof.DataType;
import cmof.Element;
import cmof.Operation;
import cmof.Parameter;
import cmof.Property;
import cmof.Type;
import cmof.UmlClass;

public class OperationWrapper extends TypedElementWrapper {
    private final Operation operation;                
    public OperationWrapper(Operation operation) {
        this.operation = operation;            
    }
    @Override
	public String getName() {
        String name = javaMapping.getJavaIdentifier(operation);
        for(Element element: ((UmlClass)operation.getOwner()).getMember()) {
            if (element instanceof Property) {
                if (new PropertyWrapper((Property)element, false).getName().equals(name)) {
                    name += "Operation";
                }
            }
        }
        return name;
    }
    public boolean hasReturn() {
        return operation.getType() != null;
    }
    public String getUmlName() {
        return operation.getName();
    }
    public String getType() {
        String typeName = getPlainJavaType();     
        if (typeName.equals("void")) {
            return typeName;
        }
        if (operation.getUpper() == 1) {
            return typeName;
        } else {
            if (!(operation.getType() instanceof DataType)) {
                typeName = "? extends " + typeName;
            }
            if (isList()) {
                return cmof.common.ReflectiveSequence.class.getCanonicalName() + "<" + typeName + ">";
            } else {
                return cmof.common.ReflectiveCollection.class.getCanonicalName() + "<" + typeName + ">";
            }
        }
    }
    public boolean isJavaPrimitive() {
        return ((operation.getUpper() == 1) && (
                    operation.getType().getName().equals("Integer") ||
                    operation.getType().getName().equals("UnlimitedNatural") ||
                    operation.getType().getName().equals("Boolean"))
                );
    }        
    public boolean isJavaList() {
        return operation.getUpper() != 1;
    }
    
    public boolean isList() {
        return operation.isOrdered();
    }

    @Override
	public Type getUmlType() {
        return operation.getType();
    }

	public String getParameters() throws GenerationException {
        StringBuffer result = new StringBuffer();
        boolean first = true;
        cmof.common.ReflectiveSequence<? extends Parameter> parameters = operation.getFormalParameter();
        for (Parameter parameter: parameters) {
            if (first) first = false; else result.append(", ");
            ParameterWrapper parameterWrapper = new ParameterWrapper(parameter);
            result.append(parameterWrapper.getType());
            result.append(" ");
            result.append(parameterWrapper.getName());
        }
        return result.toString();
    }
	
	public String getParameterNames() throws GenerationException {
        StringBuffer result = new StringBuffer();
        boolean first = true;
        cmof.common.ReflectiveSequence<? extends Parameter> parameters = operation.getFormalParameter();
        for (Parameter parameter: parameters) {
            if (first) first = false; else result.append(", ");
            ParameterWrapper parameterWrapper = new ParameterWrapper(parameter);                
            result.append(parameterWrapper.getName());
        }
        return result.toString();
    }

	public String getUnambigousName() throws GenerationException {
        StringBuffer result = new StringBuffer();
        result.append(operation.getName());            
        for (Parameter parameter: operation.getFormalParameter()) {            
            ParameterWrapper parameterWrapper = new ParameterWrapper(parameter);                
            result.append("_");
            result.append(parameterWrapper.getName());
        }
        return result.toString();
    }
    public String getExceptions() {
        String result = "";
        boolean first = true;
        for (Type exception: operation.getRaisedException()) {
            if (first) {
                result += "throws ";
                first = false;
            } else {
                result += ", ";
            }
            result += getFullQualifiedJavaIdentifier(exception);
        }
        return result;
    }
    public String getAttributeDocString() {
        StringBuffer result = new StringBuffer();
        if (operation.isUnique() && operation.getUpper() > 1) {
            result.append(", isUnique");
        }
        if (operation.isOrdered() && operation.getUpper() > 1) {
            result.append(", isOrdered");
        }            
        if (operation.isQuery()) {
            result.append(", isQuery");
        }
        return result.toString();
    }
    public String getMultiplicity() {
        return operation.getLower() + "," + ((operation.getUpper() == -1) ? "*" : new Long(operation.getUpper()).toString());
    }
}
