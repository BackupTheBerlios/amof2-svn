package cmof;


public interface Namespace extends core.abstractions.constraints.Namespace, cmof.NamedElement
{

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> getImportedMember();

    public cmof.common.ReflectiveCollection<? extends cmof.ElementImport> getElementImport();

    public cmof.common.ReflectiveCollection<? extends cmof.PackageImport> getPackageImport();

    public cmof.common.ReflectiveCollection<? extends cmof.Constraint> getOwnedRule();

    public cmof.common.ReflectiveCollection<? extends cmof.NamedElement> getOwnedMember();

    public cmof.common.ReflectiveCollection<? extends cmof.NamedElement> getMember();

}

