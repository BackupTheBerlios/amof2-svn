package cmofimpl.reflection;

import cmof.*;
import cmofimpl.instancemodel.*;

public class ClassImpl {

    private final MofClassSemantics classClass;
    private final ExtentImpl extent;
    
    protected ClassImpl(ExtentImpl extent, MofClassSemantics classClass) {
        this.classClass = classClass;
        this.extent = extent;
    }
    
    public UmlClass getMetaClass() {
        return classClass.getClassifier();
    }

    public cmof.reflection.Object create() {
        return extent.getFactory().create(getMetaClass());
    }
    
    public MofClassifierSemantics getClassClassifier() {
        return classClass;
    }
}
