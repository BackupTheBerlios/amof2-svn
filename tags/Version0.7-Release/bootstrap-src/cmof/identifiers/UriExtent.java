package cmof.identifiers;

public interface UriExtent {
    public String contextUri();       
    
    public String uri(cmof.reflection.Object object);   
    
    public cmof.reflection.Object object(String uri);
}
