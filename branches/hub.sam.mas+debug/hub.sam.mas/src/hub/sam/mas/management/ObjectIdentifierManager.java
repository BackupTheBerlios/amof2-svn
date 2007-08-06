package hub.sam.mas.management;

import java.util.Collection;
import java.util.HashSet;

import hub.sam.mas.model.mas.ObjectIdentifier;
import hub.sam.mof.management.MofModel;
import cmof.UmlClass;

public class ObjectIdentifierManager {
    
    private int id = 0;
    private final MofModel masModel;
    
    public ObjectIdentifierManager(MofModel masModel) {
        this.masModel = masModel;
        for (ObjectIdentifier objectId: getAllIdObjects()) {
            int masId;
            try {
                masId = Integer.parseInt(objectId.getObjectId());
            }
            catch (NumberFormatException e) {
                continue;
            }
            id = Math.max(id, masId);
        }
    }
    
    public Collection<ObjectIdentifier> getAllIdObjects() {
        UmlClass objectClass = (UmlClass) masModel.getMetaModel().getExtent().query("Package:mas/Class:ObjectIdentifier");
        Collection<ObjectIdentifier> idObjects = new HashSet<ObjectIdentifier>();
        for (Object obj: masModel.getExtent().objectsOfType(objectClass, true)) {
            if (obj instanceof ObjectIdentifier) {
                idObjects.add((ObjectIdentifier) obj);
            }
        }
        return idObjects;
    }
    
    public void addId(ObjectIdentifier object, String id) {
        object.setObjectId(id);
    }

    public String addId(ObjectIdentifier object) {
        id++;
        String idAsString = Integer.toString(id);
        object.setObjectId(idAsString);
        return idAsString;
    }

}
