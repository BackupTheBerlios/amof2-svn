package core.basic;


public interface Parameter extends core.basic.TypedElement, core.abstractions.multiplicities.MultiplicityElement
{

    public core.basic.Operation getOperation();

    public void setOperation(core.basic.Operation value);

}

