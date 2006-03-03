package hub.sam.mof.instancemodel;

import java.util.*;

public class InstanceModel<C,P,DataValue> {

    private final Map<String, ClassInstance<C,P,DataValue>> instanceForId = new HashMap<String, ClassInstance<C,P,DataValue>>();    
    private final Collection<ClassInstance<C,P,DataValue>> outermostComposites = new HashSet<ClassInstance<C,P,DataValue>>();
    
    public final ClassInstance<C,P,DataValue> createInstance(String id, C classifier) {
        ClassInstance<C,P,DataValue> instance = null;
        if (id == null || id.equals("")) {
            instance = createAInstance(id, classifier);            
        } else {
            instance = instanceForId.get(id);
            if (instance == null) {
                instance = createAInstance(id, classifier);
                instanceForId.put(id, instance);
            }            
        }        
        if (instance.getComposite() == null) {
            outermostComposites.add(instance);
        }
        return instance;
    }
    
    protected ClassInstance<C,P,DataValue> createAInstance(String id, C classifier) {
        return new ClassInstance<C, P, DataValue>(id, classifier, this);
    }
    
    public Collection<ReferenceValue<C,P,DataValue>> createReferences(String idString) {
        String[] ids = idString.split(" ");
        Collection<ReferenceValue<C,P,DataValue>> result = new Vector<ReferenceValue<C,P,DataValue>>(ids.length);
        for (String id: ids) {
            ReferenceValue<C,P,DataValue> ref = new ReferenceValue<C,P,DataValue>(id, this);
            result.add(ref);
        }         
        return result;
    }
    
    public UnspecifiedValue<C,P,DataValue> createUnspecifiedValue(Object unspecifiedData) {
        UnspecifiedValue<C,P,DataValue> result = new UnspecifiedValue<C,P,DataValue>(unspecifiedData);
        return result;
    }
        
    public InstanceValue<C,P,DataValue> createInstanceValue(ClassInstance<C,P,DataValue> instance) {
        return new InstanceValue<C,P,DataValue>(instance);        
    }
    
    public PrimitiveDataValue<C,P,DataValue> createPrimitiveValue(DataValue primitiveData) {
        PrimitiveDataValue<C,P,DataValue> result = new PrimitiveDataValue<C,P,DataValue>(primitiveData);
        return result;
    }
      
    public ClassInstance<C,P,DataValue> getInstance(String id) {
        return instanceForId.get(id);
    }
    
    public Collection<ClassInstance<C,P,DataValue>> getInstances() {
        return instanceForId.values();
    }
    
    public Collection<ClassInstance<C,P,DataValue>> getOutermostComposites() {
        return outermostComposites;
    }
    
    protected void removeOutermostComposite(ClassInstance<C,P,DataValue> instance) {
        outermostComposites.remove(instance);
    }
    
    protected void addOutermostComposite(ClassInstance<C,P,DataValue> instance) {
        outermostComposites.add(instance);
    }
}
