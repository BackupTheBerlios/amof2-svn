package cmof;


public interface PackageImport extends cmof.DirectedRelationship
{

    public core.abstractions.visibilities.VisibilityKind getVisibility();

    public void setVisibility(core.abstractions.visibilities.VisibilityKind value);

    public cmof.Package getImportedPackage();

    public void setImportedPackage(cmof.Package value);

    public cmof.Namespace getImportingNamespace();

    public void setImportingNamespace(cmof.Namespace value);

}

