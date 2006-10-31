package cmofimpl.instancemodel;

import cmof.UmlClass;
import cmof.common.ReflectiveSequence;
import cmofimpl.reflection.*;

public class ClassInstanceImpl extends ElementInstanceImpl implements ClassInstance, Cloneable {

    public ClassInstanceImpl(ExtentImpl extent, UmlClass classifier) {
        super(extent, InstanceClassifierImpl.createClassClassifierForUmlClass(classifier));
    }
	
    public ReflectiveSequence<? extends UmlClass> getClassifier() {
        return (ReflectiveSequence<? extends UmlClass>)super.getClassifier();
    }
    
    public InstanceClassImpl getInstanceClassifier() {
        return (InstanceClassImpl)super.getInstanceClassifier();        
    } 
    
    public ClassInstanceImpl clone() {
        return (ClassInstanceImpl)super.clone();
    }
}
