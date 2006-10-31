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

package hub.sam.mof.javamapping;

import cmof.*;

public class JavaMapping {

    public final static JavaMapping mapping = new JavaMapping();
    
    private JavaMapping() {
        
    }
    
    public String getJavaIdentifierForName(String name) {
        if (name.equals("Class")) {
            return "UmlClass";
        } else if (name.equals("class")){
            return "umlClass";
        } else if (name.equals("super")){
            return "umlsuper";
        } else {
            return name;
        }
    }
    
    public String getJavaNameForProperty(Property property) {
        return getJavaIdentifierForName(property.getName());
    }    
    
    private String getUpperName(String name) {
        return name.substring(0,1).toUpperCase() + name.substring(1, name.length());
    }
    
    public String getJavaMethodNameForOperation(Operation op) {
        return getJavaIdentifierForName(op.getName());
    }
    
    public String getJavaSetMethodNameForProperty(Property property) {
        return "set" + getUpperName(getJavaNameForProperty(property));
    }
    
    public String getJavaGetMethodNameForProperty(String name, boolean isBoolean) {
        if (isBoolean) {
            return getJavaIdentifierForName(name);
        } else {
            return "get" + getUpperName(getJavaIdentifierForName(name));
        }
    }
    
    public String getJavaGetMethodNameForProperty(Property property) {
        Type type = property.getType();
        if (type instanceof PrimitiveType) {
            if (type.getName().equals(core.primitivetypes.Boolean.class.getSimpleName())) {
                return getJavaNameForProperty(property);
            }
        }
        return "get" + getUpperName(getJavaNameForProperty(property));   
    }
    
    private String getClassNameForMetaClass(UmlClass metaClass) {
        String className = getJavaIdentifierForName(metaClass.getName());
        cmof.Package owningPackage = metaClass.getPackage();
        while (owningPackage != null) {
            className = getJavaIdentifierForName(owningPackage.getName()) + "." + className;
            owningPackage = (cmof.Package)owningPackage.getOwner();
        }
        return className;
    }
    
    public String getImplClassNameForMetaClass(UmlClass metaClass) {
        return getClassNameForMetaClass(metaClass) + "Impl";
    }
    
    public String getCustomClassNameForMetaClass(UmlClass metaClass) {
        return getClassNameForMetaClass(metaClass) + "Custom";
    }
    
    public String getJavaIdentifier(cmof.NamedElement element) {
        return getJavaIdentifierForName(element.getName());
    }
    
    public String getFullQualifiedJavaIdentifier(NamedElement element) {
        String result = getJavaIdentifier(element);       
        cmof.Element actualElement = element.getOwner();
        while (actualElement != null) {
            if (actualElement instanceof cmof.NamedElement) {
                result = getJavaIdentifier((cmof.NamedElement)actualElement) + "." + result;
                actualElement = actualElement.getOwner();
            } else {
                throw new RuntimeException("assert");
            }
        }
        return result;
    }

    public String getFullQualifiedImplFactoryNameForPackage(cmof.Package thePackage) {
        return getFullQualifiedJavaIdentifier(thePackage) + "." + getImplFactoryNameForPackage(thePackage);
    }
    
    public String getFullQualifiedFactoryNameForPackage(cmof.Package thePackage) {
        return getFullQualifiedJavaIdentifier(thePackage) + "." + getFactoryNameForPackage(thePackage);
    }
    
    public String getImplFactoryNameForPackage(cmof.Package thePackage) {
        return getJavaIdentifier(thePackage) + "FactoryImpl";
    }
    public String getFactoryNameForPackage(cmof.Package thePackage) {
        return getJavaIdentifier(thePackage) + "Factory";
    }
}
