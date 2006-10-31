package core.abstractions.generalizations;


public interface Generalization extends core.abstractions.relationships.DirectedRelationship
{

    public core.abstractions.generalizations.Classifier getSpecific();

    public void setSpecific(core.abstractions.generalizations.Classifier value);

    public core.abstractions.generalizations.Classifier getGeneral();

    public void setGeneral(core.abstractions.generalizations.Classifier value);

}

