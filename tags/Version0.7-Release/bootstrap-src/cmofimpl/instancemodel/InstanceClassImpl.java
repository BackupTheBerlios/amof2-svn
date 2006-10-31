package cmofimpl.instancemodel;

public class InstanceClassImpl extends InstanceClassifierImpl {

    protected  InstanceClassImpl(cmof.UmlClass classifier) {
        super(classifier);
    }        
    
    public cmof.UmlClass getClassifier() {
        return (cmof.UmlClass)super.getClassifier();
    }
}
