package cmofimpl.instancemodel;

public class DataValueTypeImpl extends InstanceClassifierImpl {


    protected  DataValueTypeImpl(cmof.DataType classifier) {
        super(classifier);
    }        
    
    public cmof.DataType getClassifier() {
        return (cmof.DataType)super.getClassifier();
    }
}
