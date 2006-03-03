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
import cmof.common.*;
import hub.sam.mof.instancemodel.*;
import hub.sam.mof.javamapping.*;

/**
 * This class is not specified within MOF or UML. It offers helper functionalty for analysis of inter property relations 
 * with in a single UML classifier.
 */
public abstract class MofClassifierSemantics extends CommonClassifierSemantics<Classifier,Property,Operation,String> implements ClassifierSemantics<Property, Operation, String> {

    private JavaMapping javaMapping = hub.sam.mof.javamapping.JavaMapping.mapping;
    
    protected MofClassifierSemantics(Classifier classifier) {
        super(classifier);
        initialize();
    }    
    
    private static final Map<UmlClass, MofClassSemantics> classInstances = new HashMap<UmlClass, MofClassSemantics>();
       
    /**
     * Creates an instance that will represent the given class.
     * @param classifier the UmlClass
     * @return A new instance that represents the given class.
     */
    public static MofClassSemantics createClassClassifierForUmlClass(UmlClass classifier) {
        MofClassSemantics result = classInstances.get(classifier);
        if (result == null) {
            result = new MofClassSemantics(classifier);
            classInstances.put(classifier, result);
        }
        return result;
    }
    
	/**
     * Creates an instance that will represent the given data type.
     * @param classifier the data type
     * @return A new instance that represents the given data type.
     */
    public static MofDataTypeSemantics createDataValueTypeForDataType(DataType dataType) {
        return new MofDataTypeSemantics(dataType);
    }
   
    public String getName(Property forProperty) {
        return forProperty.getName();
    }
    
    public boolean isCollectionProperty(Property forProperty) {
        return forProperty.getUpper() != 1;
    }

    public String getJavaGetMethodNameForProperty(Property forProperty) {
        return javaMapping.getJavaGetMethodNameForProperty(forProperty);
    }
    
    protected <E> Collection<E> toCollection(ReflectiveCollection<E> reflectiveCollection) {
        Collection<E> result = new Vector<E>(reflectiveCollection.size());
        for (E e: reflectiveCollection) {
            result.add(e);
        }        
        return result;
    }
    
    protected Collection<? extends Property> redefinedProperties(Property p) {
        return toCollection(p.getRedefinedProperty());
    }

    protected Collection<? extends Property> subsettedProperties(Property p) {
        return toCollection(p.getSubsettedProperty());
    }      
}
