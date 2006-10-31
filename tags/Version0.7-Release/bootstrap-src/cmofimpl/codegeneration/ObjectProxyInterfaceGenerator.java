package cmofimpl.codegeneration;

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
