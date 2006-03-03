package cmof;


public interface Element extends core.abstractions.comments.Element
{

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getOwnedElement();

    public cmof.Element getOwner();

    public cmof.common.ReflectiveCollection<? extends cmof.Comment> getOwnedComment();

}

