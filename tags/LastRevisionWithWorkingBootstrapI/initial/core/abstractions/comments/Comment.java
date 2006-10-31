package core.abstractions.comments;


public interface Comment extends core.abstractions.comments.Element
{

    public String getBody();

    public void setBody(String value);

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getAnnotatedElement();

}

