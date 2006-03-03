package cmof;


public interface Enumeration extends cmof.DataType, core.basic.Enumeration
{

    public cmof.common.ReflectiveSequence<? extends cmof.EnumerationLiteral> getOwnedLiteral();

}

