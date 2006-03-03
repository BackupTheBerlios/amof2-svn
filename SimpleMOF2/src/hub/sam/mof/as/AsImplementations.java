package hub.sam.mof.as;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import as.Activity;

import cmof.Constraint;
import cmof.Feature;
import cmof.Operation;
import cmof.Property;

import hub.sam.mof.instancemodel.ClassifierSemantics;
import hub.sam.mof.reflection.ImplementationsImpl;
import hub.sam.mof.reflection.ObjectDlg;

public class AsImplementations extends ImplementationsImpl {

	private Map<Feature, AsBehavior> behaviors = new HashMap<Feature, AsBehavior>();
	private Set<Object> hasNoBehavior = new HashSet<Object>();
	private final AsExecutionEnvironment environment;
	
	public AsImplementations(List<ObjectDlg> delegates, AsExecutionEnvironment environment) {
		super(delegates);
		this.environment = environment;
	}
	
	@Override
	public boolean hasImplementationFor(Operation operatoin, ClassifierSemantics<Property, Operation, String> semantics) {
		boolean result = super.hasImplementationFor(operatoin, semantics);
		if (!result) {
			return getBehaviorFor(operatoin) != null;
		} else {
			return result;
		}
	}
	
	@Override
	public boolean hasImplementationFor(Property property, ClassifierSemantics<Property, Operation, String> semantics) {
		boolean result = super.hasImplementationFor(property, semantics);
		if (!result) {
			return getBehaviorFor(property) != null;
		} else {
			return result;
		}
	}
	
	private AsBehavior getBehaviorFor(Feature feature) {
		if (hasNoBehavior.contains(feature)) {
			return null;
		} else {
			AsBehavior behavior = behaviors.get(feature);
			if (behavior == null) {
				if (feature instanceof Operation) {
					if (((Operation)feature).getBodyCondition() != null) {
						behavior = new AsQuery(((Operation)feature).getBodyCondition());
					}
				}
				if (behavior == null) {				
					for (Object element: feature.getOwnedElement()) {
						if (element instanceof Activity) {
							behavior = new AsActivity((Activity)element);
						} else if (element instanceof Constraint) {
							behavior = new AsQuery((Constraint)element);
						}
					}
				}				
				if (behavior == null) {
					hasNoBehavior.add(feature);
					return null;
				} else {
					behaviors.put(feature, behavior);
					return behavior;
				}
			} else {
				return behavior;
			}
		}
	}
	
	@Override
	public Object invokeImplementationFor(Operation operation, cmof.reflection.Object object, Object[] args, ClassifierSemantics<Property, Operation, String> semantics) {
		if (super.hasImplementationFor(operation, semantics)) {
			return super.invokeImplementationFor(operation, object, args, semantics);
		} else {
			return getBehaviorFor(operation).invoke(object, args, environment);			
		}
	}

	@Override
	public Object invokeImplementationFor(Property property, cmof.reflection.Object object, ClassifierSemantics<Property, Operation, String> semantics) {
		if (super.hasImplementationFor(property, semantics)) {
			return super.invokeImplementationFor(property, object, semantics);
		} else {
			return getBehaviorFor(property).invoke(object, null, environment);
		}
	}
}
