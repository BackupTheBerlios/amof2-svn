package hub.sam.mof.as.layers;

import hub.sam.mof.reflection.Implementation;

public class DestroyImpl implements Implementation {
    public Object invoke(cmof.reflection.Object object, Object[] args) {
        object.set("metaClassifier", null);
        object.delete();
        return null;
    }
}
