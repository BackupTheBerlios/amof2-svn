package core.abstractions.visibilities;


public interface NamedElement extends core.abstractions.namespaces.NamedElement
{

    public core.abstractions.visibilities.VisibilityKind getVisibility();

    public void setVisibility(core.abstractions.visibilities.VisibilityKind value);

}

