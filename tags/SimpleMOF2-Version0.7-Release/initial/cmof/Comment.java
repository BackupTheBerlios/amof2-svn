package cmof;


public interface Comment extends core.abstractions.comments.Comment, cmof.Element
{

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getAnnotatedElement();

}

