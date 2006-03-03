package cmofimpl.codegeneration;

import cmofimpl.codegeneration.AbstractObjectProxyGenerator.OperationWrapper;

public class ObjectProxyImplementationGenerator extends AbstractObjectProxyGenerator {
     
    private final String classNameExtension;
    public ObjectProxyImplementationGenerator(StreamFactory streamFactory, String classNameExtension) {
        super(streamFactory);
        this.classNameExtension = classNameExtension;
    }
          
    protected String getClassName(UmlClassWrapper umlClass) {
        return umlClass.getName() + classNameExtension;
    }
    
    protected void addClassSignature(UmlClassWrapper umlClass) throws Throwable {
        add("public class " + getClassName(umlClass) + " extends " + cmofimpl.reflection.ObjectImpl.class.getName() + " $implements");
    }
    
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
        add("        return new " + cmofimpl.util.TypeWrapperListImpl.class.getCanonicalName() + "<$plainJavaType>((" + cmof.common.ReflectiveSequence.class.getName() + ")value);");
            } else {
        add("        return new " + cmofimpl.util.TypeWrapperSetImpl.class.getCanonicalName() + "<$plainJavaType>((" + cmof.common.ReflectiveCollection.class.getName() + ")value);");
            }
        } else {
        add("        return ($javaObjectType)value;"); 
        }
        add("    }");
        add("}");
    }
    
    protected void addSetterCode(PropertyWrapper property) throws Throwable {
        add("public void $setterName($type value) {");    
        add("    set(\"$name\", value);");
        add("}");        
    }
    
    protected void addOperationCode(OperationWrapper operation) throws Throwable {      
        add("public $plainJavaType $name($parameters) $exceptions {");
        add("    throw new RuntimeException(\"not implemented\");");
        add("}");
    }
    
    protected void addGeneralClassBodyCode(UmlClassWrapper umlClass) throws Throwable {
        add("public " + getClassName(umlClass) + "(" + cmofimpl.instancemodel.ClassInstanceImpl.class.getName() + " instance, " +
                cmofimpl.reflection.ExtentImpl.class.getName() + " extent) {");
        add("    super(instance, extent);");
        add("}");
        add("public " + getClassName(umlClass) + "(" + 
                cmofimpl.reflection.Identifier.class.getName() + " id, " +
                cmofimpl.reflection.Identifier.class.getName() + " metaId, " +
                "String implementationClassName, String[] delegateClassNames) {");                
        add("    super(id, metaId, implementationClassName, delegateClassNames);");
        add("}");
    }

    protected boolean generateOnlyForOwnedMember() { return false; }
}
