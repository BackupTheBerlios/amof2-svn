package cmof;


public interface ElementImport extends cmof.DirectedRelationship
{

    public core.abstractions.visibilities.VisibilityKind getVisibility();

    public void setVisibility(core.abstractions.visibilities.VisibilityKind value);

    public String getAlias();

    public void setAlias(String value);

    public cmof.PackageableElement getImportedElement();

    public void setImportedElement(cmof.PackageableElement value);

    public cmof.Namespace getImportingNamespace();

    public void setImportingNamespace(cmof.Namespace value);

}

