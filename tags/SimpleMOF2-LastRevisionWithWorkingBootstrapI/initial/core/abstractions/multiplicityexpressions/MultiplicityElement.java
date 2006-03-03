package core.abstractions.multiplicityexpressions;


public interface MultiplicityElement extends core.abstractions.multiplicities.MultiplicityElement, core.abstractions.ownerships.Element
{

    public int getLower();

    public void setLower(int value);

    public long getUpper();

    public void setUpper(long value);

    public core.abstractions.expressions.ValueSpecification getUpperValue();

    public void setUpperValue(core.abstractions.expressions.ValueSpecification value);

    public core.abstractions.expressions.ValueSpecification getLowerValue();

    public void setLowerValue(core.abstractions.expressions.ValueSpecification value);

}

