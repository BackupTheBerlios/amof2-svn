package hub.sam.mof.codegeneration;

import cmof.PrimitiveType;
import hub.sam.mof.codegeneration.wrapper.OperationWrapper;
import hub.sam.mof.codegeneration.wrapper.PropertyWrapper;
import hub.sam.mof.codegeneration.wrapper.UmlClassWrapper;

public class ObjectProxyDelegatorGenerator extends
		ObjectProxyImplementationGenerator {

	public ObjectProxyDelegatorGenerator(StreamFactory streamFactory) {
		super(streamFactory, "Dlg");
	}

    @Override
	protected void addClassSignature(UmlClassWrapper umlClass) throws Throwable {
        add("public class " + getClassName(umlClass) + " extends " + hub.sam.mof.reflection.ObjectDlg.class.getName() + " $implements");
    }
	
	@Override
	protected void addGeneralClassBodyCode(UmlClassWrapper umlClass) throws Throwable {		
        add("protected $name self = null;");
        add("@Override");
        add("protected void setSelf(" + cmof.reflection.Object.class.getCanonicalName() + " self) {");
        add("    super.setSelf(self);");
        add("    this.self = ($name)self;");
        add("}");
	}

	@Override
	protected void addGetterCode(PropertyWrapper property) throws Throwable {
		add("public $type $getterName() {");
		if (property.getUmlType() instanceof PrimitiveType) {
			add("    return self.$getterName();");
		} else {
			add("    return ($type)(java.lang.Object)self.$getterName();");
		}
	    add("}");
	}

	@Override
	protected void addOperationCode(OperationWrapper operation) throws Throwable {
		add("public $type $name($parameters) $exceptions {");
        if (operation.hasReturn()) {         
        	if (operation.getUmlType() instanceof PrimitiveType) {
        		add("    return self.$name($parameterNames);");
        	} else {
        		add("    return ($type)(java.lang.Object)self.$name($parameterNames);");
        	}
        } else {
            add("    self.$name($parameterNames);");
        }        
        add("}");
	}

	@Override
	protected void addSetterCode(PropertyWrapper property) throws Throwable {
        add("public void $setterName($type value) {");    
        add("    self.$setterName(value);");
        add("}");   
	}
}
