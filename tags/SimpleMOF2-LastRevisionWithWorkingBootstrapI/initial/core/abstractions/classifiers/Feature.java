package core.abstractions.classifiers;


public interface Feature extends core.abstractions.namespaces.NamedElement
{

    public cmof.common.ReflectiveCollection<? extends core.abstractions.classifiers.Classifier> getFeaturingClassifier();

}

