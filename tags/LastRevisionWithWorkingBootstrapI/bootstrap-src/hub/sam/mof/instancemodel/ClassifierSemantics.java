package hub.sam.mof.instancemodel;

import java.util.*;

public interface ClassifierSemantics<P,Names> {
    
    public Collection<P> getProperties();
    
    public Collection<P> getFinalProperties();
    
    public P getFinalProperty(P forProperty);
    
    public Collection<P> getSupersettedProperties(P forProperty);
    
    public Collection<P> getSubsettedProperties(P forProperty);
    
    public Names getName(P forProperty);

    public P getProperty(Names forName);
    
    public boolean isCollectionProperty(P forProperty);
    
    public String getJavaGetMethodNameForProperty(P forProperty);
}
