package cmof;


public interface EnumerationLiteral extends core.basic.EnumerationLiteral, cmof.NamedElement
{

    public cmof.Enumeration getEnumeration();

    public void setEnumeration(cmof.Enumeration value);

    public void setEnumeration(core.basic.Enumeration value);

}

