package core.abstractions.relationships;


public interface Relationship extends core.abstractions.ownerships.Element
{

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getRelatedElement();

}

