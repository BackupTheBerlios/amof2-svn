package cmof.reflection;

import cmof.*;

public interface Link {
    public boolean equals(Link otherLink);
    
    public void delete();
    
    public Association getAssociation();
}
