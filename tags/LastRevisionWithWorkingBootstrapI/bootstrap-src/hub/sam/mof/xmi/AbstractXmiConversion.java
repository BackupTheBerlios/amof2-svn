package hub.sam.mof.xmi;

import java.util.*;
import hub.sam.mof.instancemodel.*;
import hub.sam.mof.instancemodel.conversion.*;

public abstract class AbstractXmiConversion<Ci,Pi,T,D,DataValuei> implements Conversion<XmiClassifier,String,String,Ci,Pi,T,D,DataValuei> {

    private Map<Ci, Map<String,Pi>> properties = new HashMap<Ci, Map<String,Pi>>();      
                         
    protected abstract Ci getClassForTagName(String xmiName, String ns);        
        
    public Ci getClassifier(XmiClassifier element) throws MetaModelException {
        if (element.isDefinedByContext()) {
            T result = getPropertyType(getProperty(element.getContextProperty(),getClassifier(element.getContextClass())));
            if (asDataType(result) == null) {
                return (Ci)result;
            } else {
                throw new MetaModelException("Property \"" + element.getContextProperty() + "\" is used with wrong type.");
            }
        } else {
            Ci result = getClassForTagName(element.getName(), element.getNamespacePrefix());
            if (result == null) {
                throw new MetaModelException("Classifier \"" + element + "\" does not exist.");
            } else {
                return result;
            }       
        }
    }              
}
