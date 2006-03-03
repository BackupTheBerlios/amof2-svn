package cmof;


public interface Type extends core.basic.Type, core.abstractions.typedelements.Type, cmof.NamedElement, cmof.PackageableElement
{

    public cmof.Package getPackage();

    public void setPackage(cmof.Package value);

    public void setPackage(core.basic.Package value);

}

