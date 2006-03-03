package hub.sam.mof.reflection.client;

public interface ClientFactory extends cmof.reflection.Factory {

    public cmof.reflection.Object create(String metaClassName);
    
}
