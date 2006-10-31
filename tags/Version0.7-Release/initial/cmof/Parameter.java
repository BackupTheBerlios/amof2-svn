package cmof;


public interface Parameter extends core.basic.Parameter, core.abstractions.behavioralfeatures.Parameter, cmof.TypedElement, cmof.MultiplicityElement
{

    public String getDefault();

    public void setDefault(String value);

    public cmof.Operation getOperation();

    public void setOperation(cmof.Operation value);

    public void setOperation(core.basic.Operation value);

}

