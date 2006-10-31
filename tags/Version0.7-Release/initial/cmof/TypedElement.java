package cmof;


public interface TypedElement extends core.basic.TypedElement, core.abstractions.typedelements.TypedElement, cmof.NamedElement
{

    public cmof.Type getType();

    public void setType(cmof.Type value);

}

