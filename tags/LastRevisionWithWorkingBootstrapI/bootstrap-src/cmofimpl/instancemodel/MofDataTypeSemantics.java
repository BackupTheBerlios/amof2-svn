package cmofimpl.instancemodel;

import java.util.*;
import cmof.*;

public class MofDataTypeSemantics extends MofClassifierSemantics {


    protected  MofDataTypeSemantics(cmof.DataType classifier) {
        super(classifier);
    }        
    
    public cmof.DataType getClassifier() {
        return (cmof.DataType)super.getClassifier();
    }
    
    protected Collection<? extends Property> ownedProperties(Classifier c) {        
        return new Vector<Property>();
    }
    
    protected Collection<? extends Classifier> superClasses(Classifier c) {        
        return new Vector<Classifier>();
    }
}
