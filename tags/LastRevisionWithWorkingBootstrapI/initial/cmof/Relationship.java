package cmof;


public interface Relationship extends cmof.Element, core.abstractions.relationships.Relationship
{

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getRelatedElement();

}

