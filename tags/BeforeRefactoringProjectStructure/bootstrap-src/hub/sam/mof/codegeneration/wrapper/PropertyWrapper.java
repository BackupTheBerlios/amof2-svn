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
import cmof.Property;
import cmof.Type;
import cmof.common.ReflectiveCollection;

public class PropertyWrapper extends TypedElementWrapper {
	   final Property property;
       final boolean isCollection;
       public PropertyWrapper(Property property, boolean isCollection) {            
           this.property = property;
           this.isCollection = isCollection;
       }
       @Override
		public Type getUmlType() {
           return property.getType();            
       }
       public String getUmlName() {
           return property.getName();
       }
       @Override
		public String getName() {
           return javaMapping.getJavaNameForProperty(property);
       } 
       public boolean isChangeable() {
           boolean notChangeble = (property.isReadOnly() || (isCollection));
           if (notChangeble) {
               return false;
           }
           if (property.isDerived()) {
               ReflectiveCollection<? extends Property> allRedefinedProperties = new hub.sam.mof.util.SetImpl<Property>();
               collectAllRedefined(allRedefinedProperties, property);
               for (Property redefinedProperty: allRedefinedProperties) {
                   if (redefinedProperty.getName().equals(property.getName())) {
                       if (!redefinedProperty.isDerived()) {
                           return true;
                       }
                   }                    
               }
               return false;
           }
           return true;            
       }
       public boolean isList() {
           return property.isOrdered();
       }
       public String getType() {
           String typeName = getPlainJavaType();            
           if (!isCollection) {
               return typeName;
           } else {
               if (!(property.getType() instanceof DataType)) {
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
           return ((!isCollection) && (
                       property.getType().getName().equals("Integer") ||
                       property.getType().getName().equals("UnlimitedNatural") ||
                       property.getType().getName().equals("Boolean"))
                   );
       }
       public boolean isJavaDataType() {
           return ((!isCollection) && (property.getType() instanceof cmof.DataType));                  
       }
       public boolean isJavaList() {
           return isCollection;
       }
       public String getGetterName() {
           return javaMapping.getJavaGetMethodNameForProperty(property);
       }
       public String getSetterName() {
           return javaMapping.getJavaSetMethodNameForProperty(property);            
       }
       public String getAttributeDocString() {
           StringBuffer result = new StringBuffer();
           if (property.isDerivedUnion()) {
               result.append(", isDerivedUnion");
           }
           if (property.isDerived()) {
               result.append(", isDerived");
           }
           if (property.isComposite() && property.getAssociation() != null) {
               result.append(", isComposite");
           }
           if (property.isReadOnly()) {
               result.append(", isReadOnly");
           }
           if (property.isUnique() && isCollection) {
               result.append(", isUnique");
           }
           if (property.isOrdered() && isCollection) {
               result.append(", isOrdered");
           }
           if (property.getSubsettedProperty().size() > 0) {
               result.append(", subsettedProperty = {" + docStringForElementList(property.getSubsettedProperty()) + "}");               
           }
           if (property.getRedefinedProperty().size() > 0) {
               result.append(", redefinedProperty = {" + docStringForElementList(property.getRedefinedProperty()) + "}");               
           }
           return result.toString();
       }      
       public String getMultiplicity() {
           return property.getLower() + "," + ((property.getUpper() == -1) ? "*" : new Long(property.getUpper()).toString());
       }
}
