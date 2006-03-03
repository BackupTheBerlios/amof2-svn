package hub.sam.mof.instancemodel;


public interface SemanticsFactory<C,P,Names> {
    
    public ClassifierSemantics<P,Names> createClassifierSemantics(C classifier);
    
}
