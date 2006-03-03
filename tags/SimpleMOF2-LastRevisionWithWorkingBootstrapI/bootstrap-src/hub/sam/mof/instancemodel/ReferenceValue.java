package hub.sam.mof.instancemodel;

public class ReferenceValue<C,P,DataValue> extends InstanceValue<C,P,DataValue> {

    private final String id;
    private final InstanceModel<C,P,DataValue> extent;
    
    protected ReferenceValue(String id, InstanceModel<C,P,DataValue> extent) {
        super(null);
        this.id = id;
        this.extent = extent;        
    }                      
    
    public ClassInstance<C,P,DataValue> getInstance() {
        ClassInstance<C,P,DataValue> result = extent.getInstance(id);
        if (result == null) {
            throw new NullPointerException("broken reference id \"" + id + "\"");
        }
        return extent.getInstance(id);
    }
}
