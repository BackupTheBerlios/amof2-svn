package cmof;


public interface Package extends cmof.PackageableElement, cmof.Namespace, core.basic.Package
{

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> getOwnedMember();

    public cmof.common.ReflectiveCollection<? extends cmof.Type> getOwnedType();

    public cmof.common.ReflectiveCollection<? extends cmof.Package> getNestedPackage();

    public cmof.Package getNestingPackage();

    public void setNestingPackage(cmof.Package value);

    public void setNestingPackage(core.basic.Package value);

    public cmof.common.ReflectiveCollection<? extends cmof.PackageMerge> getPackageMerge();

}

