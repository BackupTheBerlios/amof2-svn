package cmof;


public interface DirectedRelationship extends cmof.Relationship, core.abstractions.relationships.DirectedRelationship
{

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getSource();

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getTarget();

}

