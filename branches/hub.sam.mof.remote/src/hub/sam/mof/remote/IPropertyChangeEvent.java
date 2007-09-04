package hub.sam.mof.remote;

public interface IPropertyChangeEvent {
    String getPropertyName();
    Object getNewValue();
    Object getOldValue();
    void setPropagationId(Object propagationId);
    Object getPropagationId();
    Object getSource();
    String toString();
}
