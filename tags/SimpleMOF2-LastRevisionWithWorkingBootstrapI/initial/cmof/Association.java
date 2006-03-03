package cmof;


public interface Association extends cmof.Classifier, cmof.Relationship
{

    public boolean isDerived();

    public void setIsDerived(boolean value);

    public cmof.common.ReflectiveSequence<? extends cmof.Property> getOwnedEnd();

    public cmof.common.ReflectiveCollection<? extends cmof.Type> getEndType();

    public cmof.common.ReflectiveSequence<? extends cmof.Property> getMemberEnd();

}

