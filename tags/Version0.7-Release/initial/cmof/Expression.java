package cmof;


public interface Expression extends cmof.ValueSpecification, core.abstractions.expressions.Expression
{

    public cmof.common.ReflectiveSequence<? extends cmof.ValueSpecification> getOperand();

}

