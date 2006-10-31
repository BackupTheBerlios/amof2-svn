package cmofimpl.instancemodel;

public interface ClassInstance extends ElementInstance {

    public cmof.common.ReflectiveSequence<? extends cmof.UmlClass> getClassifier();
}
