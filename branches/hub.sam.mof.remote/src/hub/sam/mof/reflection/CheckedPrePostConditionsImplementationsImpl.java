package hub.sam.mof.reflection;

import hub.sam.mof.instancemodel.ClassifierSemantics;
import hub.sam.mof.ocl.OclException;
import hub.sam.mof.ocl.OclObjectEnvironment;

import java.util.List;
import java.util.Map;

import cmof.Constraint;
import cmof.OpaqueExpression;
import cmof.Operation;
import cmof.Parameter;
import cmof.ParameterDirectionKind;
import cmof.Property;

public class CheckedPrePostConditionsImplementationsImpl extends ImplementationsImpl {

    public CheckedPrePostConditionsImplementationsImpl(List<ObjectDlg> delegates, Map<Object, Implementation> predefined) {
        super(delegates, predefined);
    }
    
    private enum ConditionKind {PRECONDITION, POSTCONDITION};

    @Override
    public Object invokeImplementationFor(Operation operation, cmof.reflection.Object object, Object[] args,
            ClassifierSemantics<Property, Operation, String> semantics) {
        OclObjectEnvironment env = object.getAdaptor(OclObjectEnvironment.class);
        int i = 0;
        cmof.Type returnType = null;
        for (Parameter parameter: operation.getFormalParameter()) {
            if (parameter.getDirection() != ParameterDirectionKind.RETURN) {
                env.addAdditionalContextAttribute(parameter.getName(), args[i], parameter.getType(), object.getMetaClass());
                i++;
            }
            else {
                returnType = parameter.getType();
            }
        }
        checkConstraints(env, operation.getPrecondition(), ConditionKind.PRECONDITION);
        Object result = super.invokeImplementationFor(operation, object, args, semantics);
        if (returnType != null) {
            env.addAdditionalContextAttribute("result", result, returnType, object.getMetaClass());
        }
        checkConstraints(env, operation.getPostcondition(), ConditionKind.POSTCONDITION);
        env.removeAdditionalAttributes();
        return result;
    }
    
    private boolean checkConstraints(OclObjectEnvironment env, cmof.common.ReflectiveCollection<? extends cmof.Constraint> constraints,
            ConditionKind conditionKind) {
        for (Constraint constraint: constraints) {
            Object specificationAsObject = constraint.getSpecification();
            if (specificationAsObject instanceof OpaqueExpression) {
                OpaqueExpression specification = (OpaqueExpression) specificationAsObject;
                if (specification.getLanguage().startsWith("OCL")) {
                    String expression = specification.getBody();
                    Object result = env.execute(expression);
                    try {
                        if (!(Boolean) result) {
                            throw new OclException("Unsatisfied invariant (" + conditionKind.toString() + "): " + expression + ".");
                        }
                    }
                    catch (ClassCastException e) {
                        throw new OclException("Invariant (" + conditionKind.toString() + ") " + expression + " is not of type boolean.");
                    }
                }
            }
        }
        return false;
    }

}
