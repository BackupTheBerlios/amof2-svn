package cmofimpl.instancemodel;

import java.util.Collection;
import cmof.*;

public class MofClassSemantics extends MofClassifierSemantics {

    protected  MofClassSemantics(cmof.UmlClass classifier) {
        super(classifier);
    }        
    
    public cmof.UmlClass getClassifier() {
        return (cmof.UmlClass)super.getClassifier();
    }

    protected Collection<? extends Property> ownedProperties(Classifier c) {
        return toCollection(((UmlClass)c).getOwnedAttribute());
    }
    
    protected Collection<? extends Classifier> superClasses(Classifier c) {        
        return toCollection(((UmlClass)c).getSuperClass());
    }
}
