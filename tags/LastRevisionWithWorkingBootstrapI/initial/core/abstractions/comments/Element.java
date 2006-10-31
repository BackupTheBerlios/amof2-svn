package core.abstractions.comments;


public interface Element extends core.abstractions.ownerships.Element
{

    public cmof.common.ReflectiveCollection<? extends core.abstractions.comments.Comment> getOwnedComment();

}

