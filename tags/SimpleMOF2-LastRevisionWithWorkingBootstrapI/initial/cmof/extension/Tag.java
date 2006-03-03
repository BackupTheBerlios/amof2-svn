package cmof.extension;


public interface Tag extends cmof.Element
{

    public String getName();

    public void setName(String value);

    public String getValue();

    public void setValue(String value);

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getElement();

}

