package hub.sam.mof.as.actions;

import java.util.Collections;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.CPInstruction;

import hub.sam.mof.as.AsAnalysisEnvironment;
import hub.sam.mof.as.AsExecutionEnvironment;
import hub.sam.mof.as.AsExecutionFrame;
import hub.sam.mof.as.AsSemanticException;
import hub.sam.mof.mofinstancemodel.MofClassSemantics;
import hub.sam.mof.mofinstancemodel.MofClassifierSemantics;
import hub.sam.mof.reflection.ArgumentImpl;
import hub.sam.mof.util.ListImpl;
import hub.sam.mof.util.ReadOnlyListWrapper;
import hub.sam.mof.util.Wrapper;
import as.Action;
import as.ContextPin;
import as.InputPin;
import as.OutputPin;
import cmof.Operation;
import cmof.Property;
import cmof.Type;
import cmof.UmlClass;
import cmof.common.ReflectiveSequence;
import cmof.reflection.Argument;

public class CallAction extends AbstractAction {
	
	private MofClassSemantics semantics = null;
	
	protected Operation getFeature(UmlClass contextType) {
		Operation operation = resolveFeature(contextType);
		return operation;
	}
	
	protected Operation resolveFeature(UmlClass context) {
		if (semantics == null) {
			semantics = MofClassifierSemantics.createClassClassifierForUmlClass(context);
		}
		String operationName = getAction().getBody().get(1); 
		for (InputPin inputPin: getAction().getInput()) {
			if (!(inputPin instanceof ContextPin)) {
				operationName += "_" + inputPin.getType().getQualifiedName();
			}
		}
		Operation operation = semantics.getFinalOperation(operationName);
		if (operation == null) {
			throw new AsSemanticException("The arguments of action " + toString() + " does not match to an operation in the given context.");
		}
		return operation;
	}
	
	
	@Override
	public void staticSemantics(Action action, Type contextType, AsAnalysisEnvironment environment) throws AsSemanticException {
		setAction(action);
		if (getAction().getBody().size() != 2) {
			throw new AsSemanticException("Action " + toString() + " has wrong number of arguments.");
		}
		contextType = getContextType(contextType);
		if (!(contextType instanceof UmlClass)) {
			throw new SecurityException("Context type for action " + toString() + " must be an Class");
		}
		
		Operation operation = getFeature((UmlClass)contextType);

		Type requiredType = null;
		for (OutputPin outputPin: getAction().getOutput()) {
			if (requiredType != null) {
				throw new AsSemanticException("Action " + toString() + " has wrong number of arguments.");
			} else {
				requiredType = outputPin.getType();
			}
		}		
		if (operation.getType() == null && requiredType != null) {
			throw new AsSemanticException("Action " + toString() + " has wrong number of arguments.");
		}
		if (operation.getType() != null && requiredType != null && !operation.getType().conformsTo(requiredType)) {
			throw new AsSemanticException("The return type of action " + toString() + " does not match its output parameter " +
					"(" + operation.getType() + " vs. " + requiredType + ").");
		}
	}
			
	@SuppressWarnings("unchecked")
	@Override
	public void invoke(Action action, List in, List out, Object context, AsExecutionEnvironment environment, AsExecutionFrame frame) {
		setAction(action);
		Operation opToCall = getFeature(((cmof.reflection.Object)context).getMetaClass());		
		ReflectiveSequence<Argument> args = new ReadOnlyListWrapper<Argument, Object>(in, new Wrapper<Argument, Object>() {
			public Argument getE(Object forO) {
				return new ArgumentImpl(null, forO);
			}
			public Object getO(Argument forE) {
				return null;
			}
		});
		Object result = ((cmof.reflection.Object)context).invokeOperation(opToCall, args);
		if (opToCall.getType() != null) {
			out.add(result);
		}		
	}
}
