package hub.sam.mof.java;

import hub.sam.mof.mofinstancemodel.MofInstanceModel;
import hub.sam.mof.mofinstancemodel.MofClassInstance;
import cmof.UmlClass;

import java.util.HashMap;
import java.util.Map;

public class ProxyModel extends MofInstanceModel {

    private ProxyModelContext context = null;
    private Map<UmlClass, ProxyClassClass> classes = new HashMap<UmlClass, ProxyClassClass>();

    @Override
    public MofClassInstance createAInstance(String id, UmlClass classifier) {
        if (context == null) {
            return super.createAInstance(id, classifier);
        } else {
            if (context.isProxyInstanceClass(classifier)) {
                return new ProxyClassInstance(classifier, context);
            } else if (context.isProxyClassClass(classifier)) {
                ProxyClassClass result = classes.get(classifier);
                if (result == null) {
                    result = new ProxyClassClass(classifier, context);
                    classes.put(classifier, result);
                }
                return result;
            } else {
                return super.createAInstance(id, classifier);
            }
        }
    }

    public void setContext(ProxyModelContext context) {
        this.context = context;
    }
}
