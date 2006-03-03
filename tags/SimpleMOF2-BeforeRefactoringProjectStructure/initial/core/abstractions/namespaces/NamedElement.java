package core.abstractions.namespaces;


/**
 * <b>NamedElement</b>, isAbstract, superClass = {core.abstractions.ownerships.Element}
 * <br>constraint - no_name : 
 * <pre>self.name->isEmpty() or self.allNamespaces()->select(ns | ns.name->isEmpty())->notEmpty() implies self.qualifiedName->isEmpty()</pre>
 * <br>constraint - qualified_name : 
 * <pre>(self.name->notEmpty() and self.allNamespaces()->select(ns | ns.name->isEmpty())->isEmpty()) implies self.qualifiedName = self.allNamespaces()->iterate( ns : Namespace; result: String = self.name | ns.name->union(self.separator())->union(result))</pre>
 */
public interface NamedElement extends core.abstractions.ownerships.Element
{

    /**
     * <b>allNamespaces</b>, multiplicity=(0,*)
     */
    public cmof.common.ReflectiveSequence<? extends core.abstractions.namespaces.Namespace> allNamespaces() ;

    /**
     * <b>isDistinguishableFrom</b>, multiplicity=(1,1)
     */
    public boolean isDistinguishableFrom(core.abstractions.namespaces.NamedElement n, core.abstractions.namespaces.Namespace ns) ;

    /**
     * <b>separator</b>, multiplicity=(1,1)
     */
    public String separator() ;

    /**
     * <b>qualifiedName</b>, multiplicity=(0,1)
     */
    public String qualifiedNameOperation() ;

    /**
     * <b>name</b>, multiplicity=(0,1)
     */
    public String getName();

    public void setName(String value);

    /**
     * <b>qualifiedName</b>, multiplicity=(0,1), isDerived
     */
    public String getQualifiedName();

    /**
     * <b>namespace</b>, multiplicity=(0,1), isDerivedUnion, isDerived, subsettedProperty = {core.abstractions.ownerships.Element.owner}
     */
    public core.abstractions.namespaces.Namespace getNamespace();

}

