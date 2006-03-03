package hub.sam.mof.reflection;

import hub.sam.mof.javamapping.JavaMapping;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import cmof.UmlClass;

public class ImplementationsManager extends AbstractImplementationsManager<UmlClass> {
	@Override
	protected Collection<? extends UmlClass> getSuperClassForClassifier(UmlClass subClass) {
		return getSuperClasses(subClass);
	}
	
	private Map<UmlClass, Collection<UmlClass>> superClassesCache = new HashMap<UmlClass,Collection<UmlClass>>();		
	private Collection<UmlClass> getSuperClasses(UmlClass umlClass) {
        Collection<UmlClass> result = superClassesCache.get(umlClass);        
        if (result == null) {
            result = new HashSet<UmlClass>();
            for (UmlClass superClass: umlClass.getSuperClass()) {
                result.add(superClass);
            }
            superClassesCache.put(umlClass, result);            
        }
        return result;
	} 

	@Override
	protected String getJavaImplementationClassNameForClassifier(UmlClass classifier) {
		return JavaMapping.mapping.getCustomClassNameForMetaClass(classifier);
	}    
	
	@Override
	protected Implementations createImplementations(List<ObjectDlg> delegates) {
		return new ImplementationsImpl(delegates);
	}
}
