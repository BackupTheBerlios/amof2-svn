package cmof;


/**
 * <b>Parameter</b>, superClass = {core.basic.Parameter, core.abstractions.behavioralfeatures.Parameter, cmof.TypedElement, cmof.MultiplicityElement}
 */
public interface Parameter extends core.basic.Parameter, core.abstractions.behavioralfeatures.Parameter, cmof.TypedElement, cmof.MultiplicityElement
{

    /**
     * <b>default</b>, multiplicity=(0,1)
     */
    public String getDefault();

    public void setDefault(String value);

    /**
     * <b>operation</b>, multiplicity=(0,1), subsettedProperty = {cmof.null.ownerFormalParam}, redefinedProperty = {core.basic.Parameter.operation}
     */
    public cmof.Operation getOperation();

    public void setOperation(cmof.Operation value);

    public void setOperation(core.basic.Operation value);

}

