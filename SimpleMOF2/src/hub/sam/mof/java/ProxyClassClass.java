package hub.sam.mof.java;

import cmof.UmlClass;
import cmof.Tag;
import hub.sam.mof.mofinstancemodel.MofClassInstance;

public class ProxyClassClass extends MofClassInstance {

    private final UmlClass classifier;
    private Class theClass = null;

    protected ProxyClassClass(UmlClass classifier, ProxyModelContext context) {
        super(classifier, context.getModel());
        this.classifier = classifier;
        for(Tag aTag: classifier.getTag()) {
            if (aTag.getName().equals(JavaModelGenerationDoclet.CLASS_PROXY_TAG)) {
                try {
                    theClass = Thread.currentThread().getContextClassLoader().loadClass(aTag.getValue());
                } catch (ClassNotFoundException e) {
                    throw new ProxyModelException("Class not found", e);
                }
            }
        }
        if (theClass == null) {
            throw new ProxyModelException("assert");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ProxyClassClass) {
            return ((ProxyClassClass)obj).classifier.equals(classifier);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return classifier.hashCode();
    }

    @Override
    public String toString() {
        return "ClassClass of " + classifier.toString();
    }

    Class getTheClass() {
        return theClass;
    }
}
