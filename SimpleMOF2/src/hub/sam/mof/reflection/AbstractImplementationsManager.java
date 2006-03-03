package hub.sam.mof.reflection;

import cmof.exception.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public abstract class AbstractImplementationsManager<ClassifierType> {

	protected abstract Collection<? extends ClassifierType> getSuperClassForClassifier(ClassifierType subClass);
	protected abstract String getJavaImplementationClassNameForClassifier(ClassifierType classifier);

	private Map<ClassifierType, Implementations> implementations = new HashMap<ClassifierType, Implementations>();

	@SuppressWarnings("unchecked")
	private List<ClassifierType> getNextLevelSuperClassifier(List<ClassifierType> priorLevel) {
    	List<ClassifierType> result = new Vector<ClassifierType>();
    	for (ClassifierType instance: priorLevel) {
    		result.addAll(getSuperClassForClassifier(instance));
    	}
    	return result;
    }

	public Implementations getImplementationsForClassifier(ClassifierType metaClass) {
		Implementations result = implementations.get(metaClass);
		if (result == null) {
			List<ClassifierType> sortedSuperClassifier = new Vector<ClassifierType>();
	        List<ClassifierType> actualSuperClassifierLevel = new Vector<ClassifierType>();
	        actualSuperClassifierLevel.add(metaClass);
	        while (actualSuperClassifierLevel.size() > 0) {
	        	for (ClassifierType superClassifier: actualSuperClassifierLevel) {
	        		if (sortedSuperClassifier.contains(superClassifier)) {
	        			sortedSuperClassifier.remove(superClassifier);
	        		}
	        		sortedSuperClassifier.add(superClassifier);
	        	}
	        	actualSuperClassifierLevel = getNextLevelSuperClassifier(actualSuperClassifierLevel);
	        }

	        List<ObjectDlg> delegates = new Vector<ObjectDlg>();
	        for(ClassifierType umlClass: sortedSuperClassifier) {
	            ObjectDlg delegate = null;
	            try {
	                delegate = (ObjectDlg)createAnObjectImplInstance(getJavaImplementationClassNameForClassifier(umlClass));
	            } catch (ClassNotFoundException ex) {
	            	// empty
	            } catch (Exception ex) {
	                throw new cmof.exception.IllegalArgumentException(ex);
	            }
	            if (delegate != null) {
	                delegates.add(delegate);
	            }
	        }
	        result = createImplementations(delegates);
	        implementations.put(metaClass, result);
		}
		return result;
	}

	protected Implementations createImplementations(List<ObjectDlg> delegates) {
		return new ImplementationsImpl(delegates);
	}

	/**
	 * Creates an object based on an existing instance and a given java class name.
	 */
	public cmof.reflection.Object createAnObjectImplInstance(String className) throws Exception {
	    Boolean exists = FactoryImpl.javaClassExists.get(className);
	    if (exists == null) {
	        java.lang.reflect.Constructor implementation = null;
	        try {
	     	   implementation = Thread.currentThread().getContextClassLoader().loadClass(className).getConstructor(new java.lang.Class[] {});
	        } catch (Exception e) {
	            exists = Boolean.FALSE;
	        }
	        if (implementation == null) {
	            exists = Boolean.FALSE;
	        } else {
	            exists = Boolean.TRUE;
	            FactoryImpl.javaClasses.put(className, implementation);
	        }
	        FactoryImpl.javaClassExists.put(className, exists);
	    }
	    if (exists.booleanValue()) {
	    		return (cmof.reflection.Object)FactoryImpl.javaClasses.get(className).newInstance();
	    } else {
	        return null;
	    }
	}
}
