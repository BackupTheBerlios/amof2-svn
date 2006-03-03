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

import hub.sam.mof.reflection.*;
import hub.sam.mof.codegeneration.wrapper.*;

public class ObjectProxyImplementationGenerator extends AbstractObjectProxyGenerator {
     
    private final String classNameExtension;
    public ObjectProxyImplementationGenerator(StreamFactory streamFactory, String classNameExtension) {
        super(streamFactory);
        this.classNameExtension = classNameExtension;
    }
          
    @Override
	protected String getClassName(UmlClassWrapper umlClass) {
        return umlClass.getName() + classNameExtension;
    }
    
    @Override
	protected void addClassSignature(UmlClassWrapper umlClass) throws Throwable {
        add("public class " + getClassName(umlClass) + " extends " + hub.sam.mof.reflection.ObjectImpl.class.getName() + " $implements");
    }
    
    @Override
	protected void addGetterCode(PropertyWrapper property) throws Throwable {
        add("public $type $getterName() {");
        add("    java.lang.Object value = get(\"$name\");");
        add("    if (value == null) {");
        if (property.isJavaPrimitive()) {
        add("       throw new RuntimeException(\"assert\");");    
        } else {
        add("       return null;");
        }
        add("    } else {");               
        if (property.isJavaList()) {
            if (property.isList()) {
        add("        return new " + hub.sam.mof.util.TypeWrapperListImpl.class.getCanonicalName() + "<$plainJavaType>((" + cmof.common.ReflectiveSequence.class.getName() + ")value);");
            } else {
        add("        return new " + hub.sam.mof.util.TypeWrapperSetImpl.class.getCanonicalName() + "<$plainJavaType>((" + cmof.common.ReflectiveCollection.class.getName() + ")value);");
            }
        } else {
        add("        return ($javaObjectType)value;"); 
        }
        add("    }");
        add("}");
    }
    
    @Override
	protected void addSetterCode(PropertyWrapper property) throws Throwable {
        add("public void $setterName($type value) {");    
        add("    set(\"$name\", value);");
        add("}");        
    }
    
    @Override
	protected void addOperationCode(OperationWrapper operation) throws Throwable {      
        add("public $type $name($parameters) $exceptions {");
        if (operation.hasReturn()) {         
            add("    java.lang.Object value = invokeOperation(\"$unambigousName\", new java.lang.Object[] { $parameterNames });");        
            add("    if (value == null) {");
            if (operation.isJavaPrimitive()) {
            add("       throw new RuntimeException(\"assert\");");    
            } else {
            add("       return null;");
            }
            add("    } else {");               
            if (operation.isJavaList()) {
                if (operation.isList()) {
            add("        return new " + hub.sam.mof.util.TypeWrapperListImpl.class.getCanonicalName() + "<$plainJavaType>((" + cmof.common.ReflectiveSequence.class.getName() + ")value);");
                } else {
            add("        return new " + hub.sam.mof.util.TypeWrapperSetImpl.class.getCanonicalName() + "<$plainJavaType>((" + cmof.common.ReflectiveCollection.class.getName() + ")value);");
                }
            } else {
            add("        return ($javaObjectType)value;"); 
            }
            add("    }");            
        } else {
            add("    invokeOperation(\"$unambigousName\", new java.lang.Object[] { $parameterNames });");
        }        
        add("}");
    }
    
    @Override
	protected void addGeneralClassBodyCode(UmlClassWrapper umlClass) throws Throwable {
        add("public " + getClassName(umlClass) + "(" + hub.sam.mof.instancemodel.ClassInstance.class.getName() +                 
                " instance, " + hub.sam.mof.reflection.ExtentImpl.class.getName() + " extent) {");
        add("    super(instance, extent);");
        add("}");
        add("public " + getClassName(umlClass) + "(" + 
                hub.sam.mof.reflection.Identifier.class.getName() + " id, " +
                hub.sam.mof.reflection.Identifier.class.getName() + " metaId, " +
                "String implementationClassName, String[] delegateClassNames) {");                
        add("    super(id, metaId, implementationClassName, delegateClassNames);");
        add("}");
        add("protected $name self = null;");
        add("protected void setSelf(" + ObjectImpl.class.getCanonicalName() + " self) {");
        add("    this.self = ($name)self;");
        add("}");
    }

    @Override
	protected boolean generateOnlyForOwnedMember() { return false; }
}
