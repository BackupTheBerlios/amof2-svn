package cmof;


/**
 * <b>NamedElement</b>, isAbstract, superClass = {cmof.Element, core.abstractions.visibilities.NamedElement, core.basic.NamedElement}
 */
public interface NamedElement extends cmof.Element, core.abstractions.visibilities.NamedElement, core.basic.NamedElement
{

    /**
     * <b>name</b>, multiplicity=(0,1), redefinedProperty = {core.abstractions.namespaces.NamedElement.name, core.basic.NamedElement.name}
     */
    public String getName();

    public void setName(String value);

    /**
     * <b>namespace</b>, multiplicity=(0,1), isDerivedUnion, isDerived, subsettedProperty = {cmof.Element.owner}, redefinedProperty = {core.abstractions.namespaces.NamedElement.namespace}
     */
    public cmof.Namespace getNamespace();

}

