package hub.sam.mof.as.layers;

import hub.sam.mof.reflection.Implementation;
import cmof.UmlClass;

public class DestroyImpl implements Implementation {
    final private UmlClass classifer;

    public DestroyImpl(UmlClass classifer) {
        this.classifer = classifer;
    }

    public Object invoke(cmof.reflection.Object object, Object[] args) {
        object.set(M1SemanticModel.getClassifierPropertyName((UmlClass)classifer.getMetaClassifier()), null);
        object.delete();
        return null;
    }
}
