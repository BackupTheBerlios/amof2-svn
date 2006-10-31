package core.basic;


public interface Enumeration extends core.basic.DataType
{

    public cmof.common.ReflectiveSequence<? extends core.basic.EnumerationLiteral> getOwnedLiteral();

}

