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

package hub.sam.mof.codegeneration;

public class ObjectProxyInterfaceGenerator extends AbstractObjectProxyGenerator {
      
    public ObjectProxyInterfaceGenerator(StreamFactory streamFactory) {
        super(streamFactory);
    }

    protected void addClassSignature(UmlClassWrapper umlClass) throws Throwable {
        add("/**");
        add(" * <b>$umlName</b>" + umlClass.getAttributeDocString());
        for (cmof.Constraint constraint: umlClass.umlClass.getOwnedRule()) {
            add(" * <br>constraint - " + constraint.getName() + " : ");
            add(" * <pre>" + ((cmof.OpaqueExpression)constraint.getSpecification()).getBody() + "</pre>");
        }
        add(" */");
        add("public interface $name $extends");
    }
    
    protected void addGetterCode(PropertyWrapper property) throws Throwable {
        add("/**");
        add(" * <b>$umlName</b>, multiplicity=($multiplicity)" + property.getAttributeDocString());
        add(" */");
        add("public $type $getterName();");        
    }
    
    protected void addSetterCode(PropertyWrapper property) throws Throwable {
        add("public void $setterName($type value);");
    }
    
    protected void addOperationCode(OperationWrapper operation) throws Throwable {
        add("/**");
        add(" * <b>$umlName</b>" + operation.getAttributeDocString());
        add(" */");
        add("public $plainJavaType $name($parameters) $exceptions;");
    }
}
