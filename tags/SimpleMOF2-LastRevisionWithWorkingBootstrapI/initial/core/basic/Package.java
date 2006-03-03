package core.basic;


public interface Package extends core.basic.NamedElement
{

    public cmof.common.ReflectiveCollection<? extends core.basic.Package> getNestedPackage();

    public core.basic.Package getNestingPackage();

    public void setNestingPackage(core.basic.Package value);

    public cmof.common.ReflectiveCollection<? extends core.basic.Type> getOwnedType();

}

