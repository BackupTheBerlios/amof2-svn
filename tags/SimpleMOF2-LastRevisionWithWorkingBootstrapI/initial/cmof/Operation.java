package cmof;


public interface Operation extends core.basic.Operation, cmof.BehavioralFeature
{

    public boolean isQuery();

    public void setIsQuery(boolean value);

    public boolean isOrdered();

    public void setIsOrdered(boolean value);

    public boolean isUnique();

    public void setIsUnique(boolean value);

    public int getLower();

    public void setLower(int value);

    public long getUpper();

    public void setUpper(long value);

    public cmof.UmlClass getUmlClass();

    public void setUmlClass(cmof.UmlClass value);

    public void setUmlClass(core.basic.UmlClass value);

    public cmof.DataType getDatatype();

    public void setDatatype(cmof.DataType value);

    public cmof.common.ReflectiveCollection<? extends cmof.Type> getRaisedException();

    public cmof.common.ReflectiveSequence<? extends cmof.Parameter> getFormalParameter();

    public cmof.common.ReflectiveCollection<? extends cmof.Constraint> getPrecondition();

    public cmof.common.ReflectiveCollection<? extends cmof.Constraint> getPostcondition();

    public cmof.common.ReflectiveCollection<? extends cmof.Operation> getRedefinedOperation();

    public cmof.Type getType();

    public cmof.Constraint getBodyCondition();

    public void setBodyCondition(cmof.Constraint value);

}

