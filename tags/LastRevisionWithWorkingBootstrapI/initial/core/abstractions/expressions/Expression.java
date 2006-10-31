package core.abstractions.expressions;


public interface Expression extends core.abstractions.expressions.ValueSpecification
{

    public String getSymbol();

    public void setSymbol(String value);

    public cmof.common.ReflectiveSequence<? extends core.abstractions.expressions.ValueSpecification> getOperand();

}

