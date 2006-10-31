package cmof.extension;


/**
 * <b>Tag</b>, superClass = {cmof.Element}
 */
public interface Tag extends cmof.Element
{

    /**
     * <b>name</b>, multiplicity=(1,1)
     */
    public String getName();

    public void setName(String value);

    /**
     * <b>value</b>, multiplicity=(1,1)
     */
    public String getValue();

    public void setValue(String value);

    /**
     * <b>element</b>, multiplicity=(0,*), isUnique
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Element> getElement();

}

