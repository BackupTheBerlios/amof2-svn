package hub.sam.mof.reflection;

import cmof.Operation;
import cmof.Parameter;
import cmof.ParameterDirectionKind;
import cmof.Property;
import cmof.exception.IllegalArgumentException;
import hub.sam.mof.instancemodel.ClassifierSemantics;
import hub.sam.mof.javamapping.JavaMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ImplementationsImpl implements Implementations {

	private final List<ObjectDlg> delegates;
	private final Map<Object, Implementation> implementations = new HashMap<Object, Implementation>();
	private final Set<Object> hasNoImplementation = new HashSet<Object>();

	public ImplementationsImpl(List<ObjectDlg> delegates, Map<Object, Implementation> predefined) {
        super();
        this.delegates = delegates;
        if (predefined != null) {
            for (Object key: predefined.keySet()) {
                implementations.put(key, predefined.get(key));
            }
        }
    }

	public List<ObjectDlg> getDelegates() {
		return delegates;
	}

	public boolean hasImplementationFor(Property property, ClassifierSemantics<Property,Operation,String> semantics) {
		return getCustomImplementation(property, semantics) != null;
	}

	public boolean hasImplementationFor(Operation operatoin, ClassifierSemantics<Property,Operation,String> semantics) {
		return getCustomImplementation(operatoin) != null;
	}

	public Object invokeImplementationFor(Property property, cmof.reflection.Object object, ClassifierSemantics<Property,Operation,String> semantics) {
		return getCustomImplementation(property, semantics).invoke(object, null);
	}

	public Object invokeImplementationFor(Operation operation, cmof.reflection.Object object, Object[] args, ClassifierSemantics<Property,Operation,String> semantics) {
		return getCustomImplementation(operation).invoke(object, args);
	}

	private Implementation getCustomImplementation(Property boundFeature, ClassifierSemantics<Property,Operation,String> semantics) {
		Implementation result = implementations.get(boundFeature);
		if (result == null && !hasNoImplementation.contains(boundFeature)) {
			loop: for (ObjectDlg delegate: delegates) {
				Method method;
		        try {
		            method = delegate.getClass().getDeclaredMethod(semantics.getJavaGetMethodNameForProperty(boundFeature), new java.lang.Class[]{});
		            if (method.getDeclaringClass() != delegate.getClass()) {
		                method = null;
		            }
		        } catch (NoSuchMethodException ex) {
		            method = null;
		        }
		        if (method != null) {
		        	result = new ImplementationImpl(delegate, method);
		        	break loop;
		        }
			}
			if (result == null) {
				hasNoImplementation.add(boundFeature);
			} else {
				implementations.put(boundFeature, result);
			}
		}
		return result;
    }

    private Implementation getCustomImplementation(Operation op) {
    	Implementation result = implementations.get(op);
		if (result == null && !hasNoImplementation.contains(op)) {
			delegateLoop: for (ObjectDlg delegate: delegates) {
			    Method resultMethod = null;
				methodLoop: for (Method method: delegate.getClass().getDeclaredMethods()) {
			        int parametersSize = 0;
                    for(Parameter parameter: op.getFormalParameter()) {
                        if (parameter.getDirection() != ParameterDirectionKind.RETURN) {
                            parametersSize++;
                        }
                    }
                    // TODO actually check parameter types, not only their numbers
                    if (method.getName().equals(JavaMapping.mapping.getJavaMethodNameForOperation(op)) &&
                            method.getParameterTypes().length == parametersSize) {
                        resultMethod = method;
                        break methodLoop;
                    }
                }
                if (resultMethod != null) {
			    	result = new ImplementationImpl(delegate, resultMethod);
			    	break delegateLoop;
			    }
			}
			if (result == null) {
				hasNoImplementation.add(op);
			} else {
				implementations.put(op, result);
			}
		}
		return result;
    }

    private class ImplementationImpl implements Implementation {
    	private final ObjectDlg delegate;
    	private final Method method;

    	ImplementationImpl(ObjectDlg delegat, Method method) {
            super();
            this.delegate = delegat;
            this.method = method;
        }

    	@Override
		public boolean equals(Object other) {
    		if (other instanceof ImplementationImpl) {
    			return ((ImplementationImpl)other).method.equals(method);
    		} else {
    			return false;
    		}
    	}

    	@Override
		public int hashCode() {
    		return method.hashCode();
    	}

    	public Object invoke(cmof.reflection.Object object, Object[] args) {
    		cmof.reflection.Object save = delegate.getSelf();
    		delegate.setSelf(object);
    		Object result;
    		try {
    			result = method.invoke(delegate, args);
    		} catch (Exception ex) {
                ex.printStackTrace(System.out);
                throw new IllegalArgumentException(ex);
            }
    		delegate.setSelf(save);
    		return result;
    	}
    }

    protected void myFinalize() {
    	  for (cmof.reflection.Object delegate: delegates) {
              if (delegate instanceof ObjectImpl) {
                  ((ObjectImpl)delegate).myFinalize();
              }
          }
          delegates.clear();
          implementations.clear();
    }
}
