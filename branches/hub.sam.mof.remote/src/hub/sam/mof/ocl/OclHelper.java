package hub.sam.mof.ocl;

import hub.sam.mof.management.MofModelManager;
import cmof.Constraint;
import cmof.OpaqueExpression;
import cmof.Operation;
import cmof.UmlClass;
import cmof.cmofFactory;
import cmof.reflection.Object;

public class OclHelper {
    
    private final MofModelManager modelManager;
    
    public OclHelper(MofModelManager modelManager) {
        this.modelManager = modelManager;
    }
    
    private enum ConditionType {PRECONDITION, POSTCONDITION};
    
    /**
     * Adds a precondition to a meta-model class's operation.
     * 
     * @param forClass the meta-model class that owns the specified operation
     * @param operationName the operations name (overloading is not allowed here)
     * @param expression OCL constraint
     */
    public void addPrecondition(UmlClass forClass, String operationName, String expression) {
        addConstraintToOperation(forClass, operationName, expression, ConditionType.PRECONDITION);
    }
    
    /**
     * Adds a postcondition to a meta-model class's operation.
     * 
     * @param forClass the meta-model class that owns the specified operation
     * @param operationName the operations name (overloading is not allowed here)
     * @param expression OCL constraint
     */
    public void addPostcondition(UmlClass forClass, String operationName, String expression) {
        addConstraintToOperation(forClass, operationName, expression, ConditionType.POSTCONDITION);
    }

    private void addConstraintToOperation(UmlClass forClass, String operationName, String expression, ConditionType conditionType) {
        for (Operation op: forClass.getOwnedOperation()) {
            if (op.getName().equals(operationName)) {
                cmofFactory m2factory = ((cmofFactory) modelManager.getM2Model().getFactory());
                Constraint constraint = m2factory.createConstraint();
                OpaqueExpression spefication = m2factory.createOpaqueExpression();
                constraint.setSpecification(spefication);
                spefication.setLanguage("OCL 2.0");
                spefication.setBody(expression);
                if (conditionType == ConditionType.PRECONDITION) {
                    op.getPrecondition().add(constraint);
                }
                else if (conditionType == ConditionType.POSTCONDITION) {
                    op.getPostcondition().add(constraint);
                }
            }
        }
    }
    
    /**
     * Adds an invariant to a meta-model class. The invariant must hold for all instances of that class.
     * 
     * @param forClass the meta-model class where the invariant should be added
     * @param expression OCL constraint
     */
    public void addInvariant(UmlClass forClass, String expression) {
        cmofFactory m2factory = ((cmofFactory) modelManager.getM2Model().getFactory());
        Constraint invariant = m2factory.createConstraint();
        invariant.getConstrainedElement().add(forClass);
        OpaqueExpression spefication = m2factory.createOpaqueExpression();
        invariant.setSpecification(spefication);
        spefication.setLanguage("OCL 2.0");
        spefication.setBody(expression);
        forClass.getMember().add(invariant);
    }
    
    public static void printOclExpressionResult(String expression, Object forObject) {
        System.out.println(forObject.getAdaptor(OclObjectEnvironment.class).execute(expression));
    }
    
    /**
     * Checks if all invariants of the meta-model classes hold for the current M1 model.
     * 
     * @return true on success, else false
     */
    public boolean checkAllInvariants() {
        boolean result = true;
        for (Object obj: modelManager.getM1Model().getExtent().getObject()) {
            for (Constraint invariant: obj.getAdaptor(OclObjectEnvironment.class).getAllUnsatisfiedInvariants()) {
                result = false;
                System.out.println("Found unsatisfied invariant: " + ((OpaqueExpression) invariant.getSpecification()).getBody());
            }
        }
        return result;
    }
    
}
