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

import java.util.*;
import cmof.*;
import cmof.common.ReflectiveCollection;
import cmof.exception.MetaModelException;
import hub.sam.mof.mofinstancemodel.*;
import hub.sam.mof.codegeneration.wrapper.*;

public abstract class AbstractObjectProxyGenerator extends AbstractGenerator {
    
    public AbstractObjectProxyGenerator(StreamFactory streamFactory) {
        super(streamFactory);
    }
                      
    public final void generate(java.util.List<String> packageName, UmlClass umlClass) throws GenerationException {        
        try {
            UmlClassWrapper umlClassWrapper = new UmlClassWrapper(umlClass);
            MofClassSemantics semantics = new MofClassSemantics(umlClass);
            init(packageName, getClassName(umlClassWrapper));
            
            indent = 0;        
            addClassSignature(umlClassWrapper);
            add("{"); 
            indent = 1;
            addGeneralClassBodyCode(umlClassWrapper);
            print(umlClassWrapper);
            ReflectiveCollection<? extends NamedElement> members = null;
            
            
            if (generateOnlyForOwnedMember()) {
                members = umlClass.getOwnedMember();
            } else {
                members = allMember(umlClass);                               
            }
                        
            hub.sam.util.MultiMap<String,RedefinableElement> redefinableElementForJavaNames = new hub.sam.util.MultiMap<String,RedefinableElement>();
            for (Element member: members) {
                if (member instanceof RedefinableElement) {
                    redefinableElementForJavaNames.put(javaMapping.getJavaIdentifier((RedefinableElement)member), (RedefinableElement)member);
                }
            }
                        
            ReflectiveCollection<? extends Operation> operationsWritten = new hub.sam.mof.util.SetImpl<Operation>();            
            Map<String, Property> getterWritten = new HashMap<String, Property>();
                        
            memberloop: for (NamedElement member : members) {
                if (member instanceof Property) {                   
                    if (getterWritten.get(javaMapping.getJavaGetMethodNameForProperty((Property)member)) != null) {
                        continue memberloop;
                    }                  
                    Property property = (Property)getFinalRedefinableElement(redefinableElementForJavaNames.get(javaMapping.getJavaIdentifier(member)), (Property)member);
                    boolean generateSetters = false; 
                        
                    try {
                        generateSetters = javaMapping.getJavaIdentifier(semantics.getFinalProperty((Property)member)).equals(
                                javaMapping.getJavaIdentifier(property));
                    } catch (Exception ex) {
                        // ignore, this might happen when a redefined property is not member of the classifier!!!
                    }
                                        
                    String getterName = javaMapping.getJavaGetMethodNameForProperty(property);
                    PropertyWrapper propertyWrapper = new PropertyWrapper(property, semantics.isCollectionProperty(property));                                        
                    if (getterWritten.get(getterName) != null) {
                        Type writtenType = getterWritten.get(getterName).getType();
                        if (writtenType == property.getType()) {                        
                            throw new MetaModelException("There is another property with same name and type as " + property.getQualifiedName() + 
                                    ", it is " + getterWritten.get(getterName).getQualifiedName());                        
                        } else {
                            System.out.println("WARNING getter for property " + property.getQualifiedName() + " with type " + property.getType().getQualifiedName() + " was already written for other type. This property will be ignored. For " + umlClass.getQualifiedName());
                            continue memberloop;
                        }
                    } 
                    addGetterCode(propertyWrapper);
                    getterWritten.put(getterName, property);
                    print(propertyWrapper);
                    if (propertyWrapper.isChangeable() && generateSetters) {
                        addSetterCode(propertyWrapper);
                        print(propertyWrapper);
                        java.util.Set<Type> coveredTypes = new java.util.HashSet<Type>();
                        coveredTypes.add(property.getType());
                        ReflectiveCollection<? extends Property> allRedefinedProperties = new hub.sam.mof.util.SetImpl<Property>();
                        collectAllRedefined(allRedefinedProperties, property);                        
                        for (Property redefinedProperty: allRedefinedProperties) {
                            if (!coveredTypes.contains(redefinedProperty.getType())) {
                                PropertyWrapper redefinedPropertyWrapper = new PropertyWrapper(redefinedProperty, semantics.isCollectionProperty(redefinedProperty));
                                addSetterCode(redefinedPropertyWrapper);
                                print(redefinedPropertyWrapper);
                                coveredTypes.add(redefinedProperty.getType());
                            }
                        }
                    }                    
                } else if (member instanceof Operation) {
                    for (RedefinableElement redefinableMember: getFinalRedefinableElements(redefinableElementForJavaNames.get(javaMapping.getJavaIdentifier(member)), (RedefinableElement)member)) {
                        Operation operation = (Operation)redefinableMember;                                       
                        ReflectiveCollection<? extends Operation> allRedefinedOperations = new hub.sam.mof.util.SetImpl<Operation>();
                        allRedefinedOperations.add(operation);
                        collectAllRedefined(allRedefinedOperations, operation);                                                           
                        loop: for (Operation redefinedOperation: allRedefinedOperations) {  
                            for (Operation writtenOperation: operationsWritten) {
                               if (hasSameSignature(redefinedOperation, writtenOperation)) {                            
                                   continue loop;
                               }
                            }                        
                            OperationWrapper wrapper = new OperationWrapper(redefinedOperation);
                            
                            addOperationCode(wrapper);
                            print(wrapper);
                            operationsWritten.add(redefinedOperation);                     
                        }
                    }
                }
            }
            indent = 0;
            add("}");
            print(this);
        } catch (Throwable ex) {
            ex.printStackTrace(System.out);
            throw new GenerationException(ex);
        }
    }        
    
    private RedefinableElement getFinalRedefinableElement(Collection<RedefinableElement> properties, RedefinableElement forProperty) {        
        Collection<RedefinableElement> finalProperties = getFinalRedefinableElements(properties, forProperty);
        if (finalProperties.size() > 1) {
            throw new RuntimeException(new MetaModelException("Property with more then one final property in one classifier with one name"));
        }
        return finalProperties.iterator().next();
    }
    
    private Collection<RedefinableElement> getFinalRedefinableElements(Collection<RedefinableElement> properties, RedefinableElement forProperty) {
        hub.sam.util.Tree<RedefinableElement> redefinitions = new hub.sam.util.Tree<RedefinableElement>();
        for (RedefinableElement property: properties) {
            for (RedefinableElement redefined: property.getRedefinedElement()) {
                redefinitions.put(redefined, property);
            }
        }
        Collection<RedefinableElement> finalProperties = redefinitions.getLeaves(forProperty);        
        return finalProperties;
    }
    
    private boolean hasSameSignature(Operation op1, Operation op2) {                
        if (!op1.getName().equals(op2.getName())) {
            return false;
        }
        cmof.common.ReflectiveSequence<? extends Parameter> op1p, op2p;
        op1p = op1.getFormalParameter();
        op2p = op2.getFormalParameter();
        if (op1p.size() != op2p.size()) {
            return false;
        }        
        for (int i = 0; i < op1p.size(); i++) {
            if (op1p.get(i).getType() != op2p.get(i).getType() && (op1p.get(i).getUpper() == 1)) {
                // when the type is a list, it will have the same java erasure
                return false;
            } 
        }
        return true;
    } 
    
    @SuppressWarnings("unchecked")
	private <E extends RedefinableElement> void collectAllRedefined(ReflectiveCollection<? extends E> redefinedProperties, E property) {
        redefinedProperties.addAll(property.getRedefinedElement());
        for (E redefinedProperty: (ReflectiveCollection<E>)property.getRedefinedElement()) {
            collectAllRedefined(redefinedProperties, redefinedProperty);
        }
    }
    
    private ReflectiveCollection<NamedElement> allMember(UmlClass forClass) {
        ReflectiveCollection<NamedElement> result = new hub.sam.mof.util.ListImpl<NamedElement>();
        result.addAll(forClass.getOwnedMember());
        for(UmlClass superClass: forClass.getSuperClass()) {
            result.addAll(allMember(superClass));
        }
        return result;
    }
    
    protected String getClassName(UmlClassWrapper umlClass) {
        return umlClass.getName();
    }

    protected boolean generateOnlyForOwnedMember() { return true; }
    protected void addGeneralClassBodyCode(UmlClassWrapper umlClass) throws Throwable {       
    	//empty
    }
    protected abstract void addGetterCode(PropertyWrapper property) throws Throwable;
    protected abstract void addSetterCode(PropertyWrapper property) throws Throwable;
    protected abstract void addOperationCode(OperationWrapper operation) throws Throwable;
    protected abstract void addClassSignature(UmlClassWrapper umlClass) throws Throwable;
}
