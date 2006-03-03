package hub.sam.mof.instancemodel.conversion;

import hub.sam.mof.instancemodel.MetaModelException;

public interface Conversion<Co,Po,DataValueo,Ci,Pi,T,D,DataValuei> {
    
    public Ci getClassifier(Co identifier) throws MetaModelException;   
    
    public Pi getProperty(Po property, Ci classifier) throws MetaModelException;
    
    public T getPropertyType(Pi property) throws MetaModelException;
   
    public DataValuei createFromString(D type, DataValueo stringRepresentation) throws MetaModelException;                
    
    public D asDataType(T type);

}
