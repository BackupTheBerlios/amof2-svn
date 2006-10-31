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

package hub.sam.mof.xmi;

import java.util.*;
import hub.sam.mof.instancemodel.*;
import hub.sam.mof.mofinstancemodel.*;
import hub.sam.mof.instancemodel.conversion.*;
import cmof.*;

public class CMOFToXmi implements Conversion<UmlClass, Property, java.lang.Object, XmiClassifier, String, String, String, String> {

    private final Map<cmof.Package, String> packageNsPrefixes = new HashMap<cmof.Package, String>();
    private final String defaultNsPrefix;
    
    public CMOFToXmi(cmof.Package metaModel, String defaulNsPrefix) {
        this.defaultNsPrefix = defaulNsPrefix;
        searchForNsPrefixes(metaModel);
    }
    
    private void searchForNsPrefixes(cmof.Package inPackage) {
        for (Element element: inPackage.getOwnedElement()) {
            if (element instanceof cmof.Package) {
                searchForNsPrefixes((cmof.Package)element);
            } else if (element instanceof cmof.extension.Tag) {
                cmof.extension.Tag tag = (cmof.extension.Tag)element;
                if (new String("org.omg.xmi.nsPrefix").equals(tag.getName())) {
                    packageNsPrefixes.put(inPackage, tag.getValue());    
                }                
            } else if (element instanceof PackageImport) {
                searchForNsPrefixes(((PackageImport)element).getImportedPackage());
            } 
        }            
    }
    
    private String getNsPrefixForPackage(cmof.Package forPackage) {
        String result = null;
        result = packageNsPrefixes.get(forPackage);
        loop: while (forPackage != null && result == null) {
            forPackage = forPackage.getNestingPackage();
            result = packageNsPrefixes.get(forPackage);
            break loop;
        }
        if (result == null) {
            return defaultNsPrefix;
        } else {
            return result;
        }
    }
    
    public Collection<String> getNsPrefixes() {
        return packageNsPrefixes.values();
    }
    
    public XmiClassifier getClassifier(UmlClass identifier) throws MetaModelException {
        return new XmiClassifier(identifier.getName(), getNsPrefixForPackage(identifier.getPackage()));        
    }

    public String getProperty(Property property, XmiClassifier classifier) throws MetaModelException {
        return property.getName();        
    }

    public String getPropertyType(String property) throws MetaModelException {
        return "ignore";
    }

    public String createFromString(String type, Object stringRepresentation) throws MetaModelException {
        return stringRepresentation.toString();
    }

    public String asDataType(String type) {
        return "ignore";
    }

    /**
     * Checks wheathe a value should be exported as value of the given property. Due to the subsetting of propertys,
     * it is possible that a single value is value for multiple propertys. But it should only be exported once.
     * The method returns true if the given value is only stored in the given property and in subsetted propertys of
     * that property within the given instance (object).
     */
    public boolean doConvert(ValueSpecificationImpl<UmlClass, Property, Object> value, StructureSlot<UmlClass, Property, Object> slot, 
            ClassInstance<UmlClass, Property, Object> instance) {
        
        java.util.Collection<Property> allSubsets = ((MofClassInstance)instance).getInstanceClassifier().getSupersettedProperties(slot.getProperty());
        for (Property subset: allSubsets) {
            StructureSlot<UmlClass,Property,Object> subsetValues = instance.get(subset);
            if (subsetValues != null) {
                if (subsetValues.getValuesAsList().contains(value)) {
                    return false;
                }
            }            
        }
        return true;
    }    
}
