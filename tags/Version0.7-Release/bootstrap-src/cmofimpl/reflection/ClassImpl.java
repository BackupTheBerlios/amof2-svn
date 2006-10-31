package cmofimpl.reflection;

import cmof.*;
import cmofimpl.instancemodel.*;

public class ClassImpl {

    private final InstanceClassImpl classClass;
    private final ExtentImpl extent;
    
    protected ClassImpl(ExtentImpl extent, InstanceClassImpl classClass) {
        this.classClass = classClass;
        this.extent = extent;
    }
    
    public UmlClass getMetaClass() {
        return classClass.getClassifier();
    }

    public cmof.reflection.Object create() {
        return extent.getFactory().create(getMetaClass());
    }
    
    public InstanceClassifierImpl getClassClassifier() {
        return classClass;
    }
}
