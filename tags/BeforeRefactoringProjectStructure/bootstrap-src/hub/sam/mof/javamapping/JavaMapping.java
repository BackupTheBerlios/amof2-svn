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

import java.util.*;
import cmof.*;
import cmof.extension.*;

public class JavaMapping {

    public final static JavaMapping mapping = new JavaMapping();
    
    
    private JavaMapping() {
       // empty 
    }
    
    private final Map<Element, String> renames = new HashMap<Element, String>();
    
    public void addSubstituteNameTag(Tag tag) {        
        if (tag.getName().equals("javax.jmi.SubstituteName")) {
            for (Element el: tag.getElement()) {
                renames.put(el, tag.getValue());
            }
        }
    }
    
    // public just for bootstrapping, private for all others    
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
        return getJavaIdentifier(property);
    }    
    
    private String getUpperName(String name) {
        return name.substring(0,1).toUpperCase() + name.substring(1, name.length());
    }
    
    public String getJavaMethodNameForOperation(Operation op) {
        return getJavaIdentifier(op);
    }
    
    public String getJavaSetMethodNameForProperty(Property property) {
        return "set" + getUpperName(getJavaNameForProperty(property));
    }
    
    // only for bootstrap
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
    
    private StringBuffer getClassNameForMetaClass(NamedElement metaClass) {
        List<String> qualifiedName = new Vector<String>(10);
        String name = getJavaIdentifier(metaClass);
        int size = name.length() + 1;
        qualifiedName.add(name);                               
        cmof.NamedElement owningPackage = metaClass.getNamespace();
        while (owningPackage != null) {
            name = getJavaIdentifier(owningPackage);
            qualifiedName.add(name);
            size += name.length() + 1;
            owningPackage = (cmof.Package)owningPackage.getOwner();
        }
        StringBuffer className = new StringBuffer(size + 10);
        boolean first = true;
        for (int i = qualifiedName.size() - 1; i >= 0; i--) {            
            if (first) {
            	first = false;
            } else {
                className.append(".");
            }       
            className.append(qualifiedName.get(i));                 
        }        
        return className;
    }
    
    public String getImplClassNameForMetaClass(UmlClass metaClass) {
        return getClassNameForMetaClass(metaClass).append("Impl").toString();
    }
    
    public String getCustomClassNameForMetaClass(UmlClass metaClass) {
        return getClassNameForMetaClass(metaClass).append("Custom").toString();
    }
    
    public String getJavaIdentifier(cmof.NamedElement element) {
        String name = element.getName();
        String substituteName = renames.get(element);
        if (substituteName == null) {
            return getJavaIdentifierForName(name);
        } else {            
            return getJavaIdentifierForName(substituteName);
        }           
    }
    
    public String getFullQualifiedJavaIdentifier(NamedElement element) {
        return getClassNameForMetaClass(element).toString();
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

    public String getJavaEnumConstantForLiteral(EnumerationLiteral literal) {
        return literal.getName().toUpperCase();
    }
}
