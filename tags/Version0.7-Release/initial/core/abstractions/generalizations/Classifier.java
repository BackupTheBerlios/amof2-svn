package core.abstractions.generalizations;


public interface Classifier extends core.abstractions.umlsuper.Classifier, core.abstractions.typedelements.Type
{

    public cmof.common.ReflectiveCollection<? extends core.abstractions.generalizations.Generalization> getGeneralization();

    public cmof.common.ReflectiveCollection<? extends core.abstractions.generalizations.Classifier> getGeneral();

}

