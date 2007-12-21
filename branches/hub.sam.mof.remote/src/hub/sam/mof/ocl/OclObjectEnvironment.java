package hub.sam.mof.ocl;

import hub.sam.mof.ocl.oslobridge.MofOclModelElementTypeImpl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.oslo.ocl20.synthesis.RuntimeEnvironment;

import cmof.Constraint;
import cmof.OpaqueExpression;
import cmof.Type;
import cmof.UmlClass;
import cmof.ValueSpecification;

public class OclObjectEnvironment {

	private final OclEnvironment fEnvironment;
	private final RuntimeEnvironment fRuntimeEnvironment;
	private final cmof.reflection.Object fSelf;
	
	private OclObjectEnvironment(final OclEnvironment environment, 
			final cmof.reflection.Object object) {
		super();
		fSelf = object;
		fEnvironment = environment;
		fRuntimeEnvironment = OclProcessor.createRuntimeEnvironment(object);
	}

	public static OclObjectEnvironment createObjectEnvironment(cmof.reflection.Object object, 
			OclEnvironment environment) {
		return new OclObjectEnvironment(environment, object);
	}
	
	public void analyse(String expression) throws OclException {
		fEnvironment.analyseOclExpression(expression, fSelf.getMetaClass());
	}
	
	public void analyse(String expression, Type requiredTyp, boolean collection, boolean unique, boolean ordered) 
			throws OclException {
		fEnvironment.analyseOclExpression(expression, fSelf.getMetaClass(), requiredTyp, collection, unique, ordered);
	}
	
	public Object execute(String expression) throws OclException {
		try {
            UmlClass metaClass = (fSelf == null ? null : fSelf.getMetaClass()); 
			return OclProcessor.evaluateExpression(fEnvironment.analyseOclExpression(expression, metaClass), 
					expression, metaClass, fRuntimeEnvironment);
		} catch (Exception ex) {
			throw new OclException("Exception during evaluation of ocl.", ex);
		}
	}
	
	/**
	 * Checks if there are invariants that do not hold for the current instance of the meta-model.
	 * 
	 * @return true if all invariants hold, else false
	 */
	public boolean checkAllInvariants() {
		return getAllUnsatisfiedInvariants().isEmpty();
	}
	
	/**
	 * Returns a collection of all invariants (specified in the meta-model class of this object) that do not hold
	 * for the current instance of the meta-model.
	 * 
	 * @return
	 */
	public Collection<Constraint> getAllUnsatisfiedInvariants() {
	    Collection<Constraint> unsatisfiedInvariants = new HashSet<Constraint>();
	    for (Constraint invariant: getInvariants()) {
            ValueSpecification specification = invariant.getSpecification();                       
            if (specification instanceof OpaqueExpression) {
                OpaqueExpression opaqueSpecification = (OpaqueExpression) specification;         
                String language = opaqueSpecification.getLanguage();
                if (language != null && language.startsWith("OCL")) {
                    String expression = opaqueSpecification.getBody();
                    try {
                        boolean result = (Boolean) execute(expression);
                        if (!result) {
                            unsatisfiedInvariants.add(invariant);
                        }
                    }
                    catch (ClassCastException ex) {
                        throw new OclException("Invariant '" + expression + "' is not of type boolean.");
                    }
                } 
            }                       
	    }
        return unsatisfiedInvariants;
	}
	
	/**
	 * Returns a collection of all invariants owned by the meta-model class of this object.
	 * 
	 * @return
	 */
	public Collection<Constraint> getInvariants() {
        Collection<Constraint> invariants = new HashSet<Constraint>();
        UmlClass metaClass = fSelf.getMetaClass();
        for (Object member: metaClass.getMember()) {
            if (member instanceof Constraint) {
                Constraint constraint = (Constraint) member;
                for (Object constrainedElement: constraint.getConstrainedElement()) {                    
                    if (constrainedElement == metaClass) {
                        invariants.add(constraint);
                    }
                }
            }
        }
        return invariants;
    }
	
	private Map<String, Type> additionalContextAttributes = new HashMap<String, Type>();
	
	public Object getAdditionalContextAttributeValue(String name) {
	    List<String> contextName = Arrays.asList(fSelf.getMetaClass().getQualifiedName().split("\\."));
	    MofOclModelElementTypeImpl contextOclModelElementType = (MofOclModelElementTypeImpl)fEnvironment.getEnvironment().lookupPathName(contextName);
	    return contextOclModelElementType.getAdditionalPropertyValue(name);
	}
	
	// contextType can be the type of the meta-class or the type of a runtime instance of the meta-class
	public void addAdditionalContextAttribute(String name, Object value, Type attributeType, Type contextType) {
		List<String> contextName = Arrays.asList(contextType.getQualifiedName().split("\\."));		
		MofOclModelElementTypeImpl contextOclModelElementType = (MofOclModelElementTypeImpl)fEnvironment.getEnvironment().lookupPathName(contextName);
		if (contextOclModelElementType == null) {
			throw new OclException("Cannot resolve context (" + contextName + ") of action " + this.toString());
		}
		if (!additionalContextAttributes.containsKey(name)) {
		    additionalContextAttributes.put(name, contextType);	
		    contextOclModelElementType.addAdditionalProperty(name, value, attributeType);
		}
	}
	
	public void removeAdditionalAttributes() {
		for (String name: additionalContextAttributes.keySet()) {
			List<String> contextName = Arrays.asList(additionalContextAttributes.get(name).getQualifiedName().split("\\."));		
			MofOclModelElementTypeImpl contextOclModelElementType = (MofOclModelElementTypeImpl)fEnvironment.getEnvironment().lookupPathName(contextName);
			if (contextOclModelElementType == null) {
				throw new OclException("Cannot resolve context of action " + this.toString());
			}			
			contextOclModelElementType.removeAdditionalProperty(name);			
		}
		additionalContextAttributes.clear();
	}
}
