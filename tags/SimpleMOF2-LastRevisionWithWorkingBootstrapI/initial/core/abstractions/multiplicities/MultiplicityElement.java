package core.abstractions.multiplicities;


public interface MultiplicityElement extends core.abstractions.elements.Element
{

    public boolean isOrdered();

    public void setIsOrdered(boolean value);

    public boolean isUnique();

    public void setIsUnique(boolean value);

    public int getLower();

    public void setLower(int value);

    public long getUpper();

    public void setUpper(long value);

}

