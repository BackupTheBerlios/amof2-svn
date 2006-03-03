package core.basic;


public interface Operation extends core.basic.TypedElement, core.abstractions.multiplicities.MultiplicityElement
{

    public cmof.common.ReflectiveCollection<? extends core.basic.Type> getRaisedException();

    public cmof.common.ReflectiveSequence<? extends core.basic.Parameter> getOwnedParameter();

    public core.basic.UmlClass getUmlClass();

    public void setUmlClass(core.basic.UmlClass value);

}

