package cmof;


public interface NamedElement extends cmof.Element, core.abstractions.visibilities.NamedElement, core.basic.NamedElement
{

    public String getName();

    public void setName(String value);

    public cmof.Namespace getNamespace();

}

