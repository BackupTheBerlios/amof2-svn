package cmofimpl.codegeneration;

import cmof.*;
import cmof.common.ReflectiveCollection;

public abstract class AbstractObjectProxyGenerator extends AbstractGenerator {
    
    public AbstractObjectProxyGenerator(StreamFactory streamFactory) {
        super(streamFactory);
    }
    
    class UmlClassWrapper {
        final cmof.UmlClass umlClass;
        public UmlClassWrapper(UmlClass umlClass) {
            this.umlClass = umlClass;
        }
        public String getName() {
            return getJavaIdentifier(umlClass);
        }
        public String getUmlName() {
            return umlClass.getName();
        }
        public String getImplName() {
            return getName() + "Impl";
        }
        public String getImplements() {
            return "implements " + getName();
        }
        public String getExtends() {
            String result = "";      
            boolean first = true;
            for (UmlClass superClass: umlClass.getSuperClass()) {
                if (first) {
                    first = false;
                    result += "extends ";
                } else {
                    result += ", ";
                }
                result += getFullQualifiedJavaIdentifier(superClass);
            }
            return result;
        }
        public String getAttributeDocString() {
            String result = "";
            if (umlClass.isAbstract()) {
                result += ", isAbstract";
            }
            if (umlClass.getSuperClass().size() > 0) {
                result += ", superClass = {" + docStringForElementList(umlClass.getSuperClass()) + "}";               
            }            
            return result; 
        }
    }
    
    abstract class TypedElementWrapper {
        public abstract String getName();
        public abstract Type getUmlType();
        public String getUpperName() {            
            String name = getName();
            return name.substring(0,1).toUpperCase() + name.substring(1, name.length());
        }
        public String getPlainJavaType() {
            String typeName = null;
            Type type = getUmlType();
            if (type == null) {
                return "void";
            }
            if (type instanceof PrimitiveType) {
                if (type.getName().equals(core.primitivetypes.String.class.getSimpleName())) {
                    typeName = "String";
                } else if (type.getName().equals(core.primitivetypes.Integer.class.getSimpleName())) {
                    typeName = "int";
                } else if (type.getName().equals(core.primitivetypes.Boolean.class.getSimpleName())) {
                    typeName = "boolean";
                } else if (type.getName().equals(core.primitivetypes.UnlimitedNatural.class.getSimpleName())) {
                    typeName = "long";
                } else {
                    throw new RuntimeException("assert");
                }
            } else {
                typeName = getFullQualifiedJavaIdentifier(type);
            }
            return typeName;
        }
        public String getJavaObjectType() {
            String typeName = null;
            Type type = getUmlType();
            if (type instanceof PrimitiveType) {
                if (type.getName().equals(core.primitivetypes.String.class.getSimpleName())) {
                    typeName = "String";
                } else if (type.getName().equals(core.primitivetypes.Integer.class.getSimpleName())) {
                    typeName = "Integer";
                } else if (type.getName().equals(core.primitivetypes.Boolean.class.getSimpleName())) {
                    typeName = "Boolean";
                } else if (type.getName().equals(core.primitivetypes.UnlimitedNatural.class.getSimpleName())) {
                    typeName = "Long";
                } else {
                    throw new RuntimeException("assert");
                }
            } else {
                typeName = getFullQualifiedJavaIdentifier(type);
            }
            return typeName;
        }         
    }

    class PropertyWrapper extends TypedElementWrapper {
        final Property property;
        public PropertyWrapper(Property property) {            
            this.property = property;
        }
        public Type getUmlType() {
            return property.getType();            
        }
        public String getUmlName() {
            return property.getName();
        }
        public String getName() {
            return javaMapping.getJavaNameForProperty(property);
        } 
        public boolean isChangeable() {
            boolean notChangeble = (property.isReadOnly() || (property.getUpper() != 1));
            if (notChangeble) {
                return false;
            }
            if (property.isDerived()) {
                ReflectiveCollection<? extends Property> allRedefinedProperties = new cmofimpl.util.SetImpl<Property>();
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
            if (property.getUpper() == 1) {
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
            return ((property.getUpper() == 1) && (
                        property.getType().getName().equals("Integer") ||
                        property.getType().getName().equals("UnlimitedNatural") ||
                        property.getType().getName().equals("Boolean"))
                    );
        }
        public boolean isJavaDataType() {
            return ((property.getUpper() == 1) && (property.getType() instanceof cmof.DataType));                  
        }
        public boolean isJavaList() {
            return property.getUpper() != 1;
        }
        public String getGetterName() {
            return javaMapping.getJavaGetMethodNameForProperty(property);
        }
        public String getSetterName() {
            return javaMapping.getJavaSetMethodNameForProperty(property);            
        }
        public String getAttributeDocString() {
            if (property.isDerived()) {
                return ", isDerived";
            }
            if (property.isDerivedUnion()) {
                return ", isDerivedUnion";
            }
            if (property.isComposite() && property.getAssociation() != null) {
                return ", isComposite";
            }
            if (property.isReadOnly()) {
                return ", isReadOnly";
            }
            if (property.isUnique() && property.getUpper() > 1) {
                return ", isUnique";
            }
            if (property.isOrdered() && property.getUpper() > 1) {
                return ", isOrdered";
            }
            if (property.getSubsettedProperty().size() > 0) {
                return ", subsettedProperty = {" + docStringForElementList(property.getSubsettedProperty()) + "}";               
            }
            if (property.getRedefinedProperty().size() > 0) {
                return ", redefinedProperty = {" + docStringForElementList(property.getRedefinedProperty()) + "}";               
            }
            return "";
        }      
        public String getMultiplicity() {
            return property.getLower() + "," + ((property.getUpper() == -1) ? "*" : new Long(property.getUpper()).toString());
        }
    }
    
    class OperationWrapper extends TypedElementWrapper {
        private final Operation operation;        
        private final ReflectiveCollection<? extends Element> membersOfOwningClasss;
        OperationWrapper(Operation operation, ReflectiveCollection<? extends Element> membersOfOwningClass) {
            this.operation = operation;
            this.membersOfOwningClasss = membersOfOwningClass;
        }
        public String getName() {
            String name = javaMapping.getJavaIdentifierForName(operation.getName());
            for(Element element: ((UmlClass)operation.getOwner()).getMember()) {
                if (element instanceof Property) {
                    if (new PropertyWrapper((Property)element).getName().equals(name)) {
                        name += "Operation";
                    }
                }
            }
            return name;
        }
        public String getUmlName() {
            return operation.getName();
        }
        public Type getUmlType() {
            return operation.getType();
        }
        public String getParameters() throws GenerationException {
            String result = "";
            boolean first = true;
            cmof.common.ReflectiveSequence<? extends Parameter> parameters = operation.getFormalParameter();
            for (Parameter parameter: parameters) {
                if (first) first = false; else result += ", ";
                ParameterWrapper parameterWrapper = new ParameterWrapper(parameter);
                result += parameterWrapper.getPlainJavaType() + " " + parameterWrapper.getName();
            }
            return result;
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
            return "";
        }
    }
    
    class ParameterWrapper extends TypedElementWrapper {
        final private Parameter parameter;
        ParameterWrapper(Parameter parameter) {
            this.parameter = parameter;
        }
      
        public String getName() {
            return javaMapping.getJavaIdentifierForName(parameter.getName());
        }
        public Type getUmlType() {
            return parameter.getType();
        }       
    }
    private String docStringForElementList(ReflectiveCollection<? extends NamedElement> elements) {
        StringBuffer result = new StringBuffer();
        boolean first = true;
        for (NamedElement element: elements) {                
            if (first) {
                first = false;
            } else {
                result.append(", ");
            }
            result.append(element.getQualifiedName());
        }
        return result.toString();
    }
    
    
    public final void generate(java.util.List<String> packageName, UmlClass umlClass) throws GenerationException {
        try {
            UmlClassWrapper umlClassWrapper = new UmlClassWrapper(umlClass);
            init(packageName, getClassName(umlClassWrapper));
            
            indent = 0;        
            addClassSignature(umlClassWrapper);
            add("{"); 
            indent = 1;
            addGeneralClassBodyCode(umlClassWrapper);
            print(umlClassWrapper);
            ReflectiveCollection<? extends NamedElement> members = null;
            ReflectiveCollection<? extends NamedElement> allMembers = umlClass.getMember();
            if (generateOnlyForOwnedMember()) {
                members = umlClass.getOwnedMember();
            } else {
                members = allMembers;
            }
            ReflectiveCollection<? extends Operation> operationsWritten = new cmofimpl.util.SetImpl<Operation>();

            for (NamedElement member : members) {
                if (member instanceof Property) {
                    PropertyWrapper propertyWrapper = new PropertyWrapper((Property)member);
                    Property property = (Property)member;
                    addGetterCode(propertyWrapper);
                    print(propertyWrapper);
                    if (propertyWrapper.isChangeable()) {
                        addSetterCode(propertyWrapper);
                        print(propertyWrapper);
                        java.util.Set<Type> coveredTypes = new java.util.HashSet<Type>();
                        coveredTypes.add(property.getType());
                        ReflectiveCollection<? extends Property> allRedefinedProperties = new cmofimpl.util.SetImpl<Property>();
                        collectAllRedefined(allRedefinedProperties, property);
                        for (Property redefinedProperty: allRedefinedProperties) {
                            if (!coveredTypes.contains(redefinedProperty.getType())) {
                                PropertyWrapper redefinedPropertyWrapper = new PropertyWrapper(redefinedProperty);
                                addSetterCode(redefinedPropertyWrapper);
                                print(redefinedPropertyWrapper);
                                coveredTypes.add(redefinedProperty.getType());
                            }
                        }
                    }                    
                } else if (member instanceof Operation) {
                    Operation operation = (Operation)member;
                    ReflectiveCollection<? extends Operation> allRedefinedOperations = new cmofimpl.util.SetImpl<Operation>();
                    allRedefinedOperations.add(operation);
                    collectAllRedefined(allRedefinedOperations, operation);                                                           
                    loop: for (Operation redefinedOperation: allRedefinedOperations) {  
                        for (Operation writtenOperation: operationsWritten) {
                           if (hasSameSignature(redefinedOperation, writtenOperation)) {                            
                               continue loop;
                           }
                        }                        
                        OperationWrapper wrapper = new OperationWrapper(redefinedOperation, allMembers);
                        
                        addOperationCode(wrapper);
                        print(wrapper);
                        operationsWritten.add(redefinedOperation);                     
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
            if (op1p.get(i).getType() != op2p.get(i).getType()) {
                return false;
            } 
        }
        return true;
    } 
    
    private <E extends RedefinableElement> void collectAllRedefined(ReflectiveCollection<? extends E> redefinedProperties, E property) {
        redefinedProperties.addAll(property.getRedefinedElement());
        for (E redefinedProperty: (ReflectiveCollection<E>)property.getRedefinedElement()) {
            collectAllRedefined(redefinedProperties, redefinedProperty);
        }
    }
    
    protected String getClassName(UmlClassWrapper umlClass) {
        return umlClass.getName();
    }

    protected boolean generateOnlyForOwnedMember() { return true; }
    protected void addGeneralClassBodyCode(UmlClassWrapper umlClass) throws Throwable {       
    }
    protected abstract void addGetterCode(PropertyWrapper property) throws Throwable;
    protected abstract void addSetterCode(PropertyWrapper property) throws Throwable;
    protected abstract void addOperationCode(OperationWrapper operation) throws Throwable;
    protected abstract void addClassSignature(UmlClassWrapper umlClass) throws Throwable;
}
