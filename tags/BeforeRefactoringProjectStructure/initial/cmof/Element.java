package cmof;


/**
 * <b>Element</b>, isAbstract, superClass = {core.abstractions.comments.Element}
 */
public interface Element extends core.abstractions.comments.Element
{

    /**
     * <b>ownedElement</b>, multiplicity=(0,*), isDerivedUnion, isDerived, isComposite, isUnique, redefinedProperty = {core.abstractions.ownerships.Element.ownedElement}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Element> getOwnedElement();

    /**
     * <b>owner</b>, multiplicity=(0,1), isDerivedUnion, isDerived, redefinedProperty = {core.abstractions.ownerships.Element.owner}
     */
    public cmof.Element getOwner();

    /**
     * <b>ownedComment</b>, multiplicity=(0,*), isComposite, isUnique, subsettedProperty = {cmof.Element.ownedElement}, redefinedProperty = {core.abstractions.comments.Element.ownedComment}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Comment> getOwnedComment();

}

