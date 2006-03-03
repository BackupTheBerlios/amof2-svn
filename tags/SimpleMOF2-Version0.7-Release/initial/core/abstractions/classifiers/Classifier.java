package core.abstractions.classifiers;


public interface Classifier extends core.abstractions.namespaces.Namespace
{

    public cmof.common.ReflectiveCollection<? extends core.abstractions.classifiers.Feature> getFeature();

}

