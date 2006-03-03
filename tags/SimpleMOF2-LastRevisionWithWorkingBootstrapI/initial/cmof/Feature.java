package cmof;


public interface Feature extends core.abstractions.classifiers.Feature, cmof.RedefinableElement
{

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getFeaturingClassifier();

}

