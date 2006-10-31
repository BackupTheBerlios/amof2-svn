package core.abstractions.ownerships;


public interface Element extends core.abstractions.elements.Element
{

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getOwnedElement();

    public core.abstractions.ownerships.Element getOwner();

}

