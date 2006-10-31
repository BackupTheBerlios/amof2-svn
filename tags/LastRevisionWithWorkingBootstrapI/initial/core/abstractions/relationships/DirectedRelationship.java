package core.abstractions.relationships;


public interface DirectedRelationship extends core.abstractions.relationships.Relationship
{

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getSource();

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getTarget();

}

