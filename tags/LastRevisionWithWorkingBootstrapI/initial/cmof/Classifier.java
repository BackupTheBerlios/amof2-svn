package cmof;


public interface Classifier extends cmof.Namespace, core.abstractions.umlsuper.Classifier, cmof.Type
{

    public cmof.common.ReflectiveCollection<? extends cmof.Property> getAttribute();

    public cmof.common.ReflectiveCollection<? extends cmof.Feature> getFeature();

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getGeneral();

}

