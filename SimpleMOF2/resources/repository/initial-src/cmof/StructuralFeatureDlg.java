package cmof;


public class StructuralFeatureDlg extends hub.sam.mof.reflection.ObjectDlg implements StructuralFeature
{
    protected StructuralFeature self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (StructuralFeature)self;
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getFeaturingClassifier() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Classifier>)(java.lang.Object)self.getFeaturingClassifier();
    }

    public java.lang.String getName() {
        return self.getName();
    }

    public void setName(java.lang.String value) {
        self.setName(value);
    }

    public java.lang.String getQualifiedName() {
        return self.getQualifiedName();
    }

    public void setQualifiedName(java.lang.String value) {
        self.setQualifiedName(value);
    }

    public cmof.Namespace getNamespace() {
        return (cmof.Namespace)(java.lang.Object)self.getNamespace();
    }

    public void setNamespace(cmof.Namespace value) {
        self.setNamespace(value);
    }

    public void setNamespace(core.abstractions.namespaces.Namespace value) {
        self.setNamespace(value);
    }

    public cmof.common.ReflectiveSequence<? extends core.abstractions.namespaces.Namespace> allNamespaces()  {
        return (cmof.common.ReflectiveSequence<? extends core.abstractions.namespaces.Namespace>)(java.lang.Object)self.allNamespaces();
    }

    public boolean isDistinguishableFrom(core.abstractions.namespaces.NamedElement n, core.abstractions.namespaces.Namespace ns)  {
        return self.isDistinguishableFrom(n, ns);
    }

    public java.lang.String separator()  {
        return self.separator();
    }

    public java.lang.String qualifiedNameOperation()  {
        return self.qualifiedNameOperation();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getOwnedElement() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Element>)(java.lang.Object)self.getOwnedElement();
    }

    public cmof.Element getOwner() {
        return (cmof.Element)(java.lang.Object)self.getOwner();
    }

    public void setOwner(cmof.Element value) {
        self.setOwner(value);
    }

    public void setOwner(core.abstractions.ownerships.Element value) {
        self.setOwner(value);
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> allOwnedElements()  {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element>)(java.lang.Object)self.allOwnedElements();
    }

    public boolean mustBeOwned()  {
        return self.mustBeOwned();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getRedefinitionContext() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Classifier>)(java.lang.Object)self.getRedefinitionContext();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.RedefinableElement> getRedefinedElement() {
        return (cmof.common.ReflectiveCollection<? extends cmof.RedefinableElement>)(java.lang.Object)self.getRedefinedElement();
    }

    public boolean isConsistentWith(core.abstractions.redefinitions.RedefinableElement redefinee)  {
        return self.isConsistentWith(redefinee);
    }

    public boolean isRedefinitionContextValid(core.abstractions.redefinitions.RedefinableElement redefinable)  {
        return self.isRedefinitionContextValid(redefinable);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Tag> getTag() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Tag>)(java.lang.Object)self.getTag();
    }

    public java.lang.String getUri() {
        return self.getUri();
    }

    public void setUri(java.lang.String value) {
        self.setUri(value);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Comment> getOwnedComment() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Comment>)(java.lang.Object)self.getOwnedComment();
    }

    public core.abstractions.visibilities.VisibilityKind getVisibility() {
        return (core.abstractions.visibilities.VisibilityKind)(java.lang.Object)self.getVisibility();
    }

    public void setVisibility(core.abstractions.visibilities.VisibilityKind value) {
        self.setVisibility(value);
    }

    public boolean isReadOnly() {
        return self.isReadOnly();
    }

    public void setIsReadOnly(boolean value) {
        self.setIsReadOnly(value);
    }

    public cmof.Type getType() {
        return (cmof.Type)(java.lang.Object)self.getType();
    }

    public void setType(cmof.Type value) {
        self.setType(value);
    }

    public void setType(core.abstractions.typedelements.Type value) {
        self.setType(value);
    }

    public boolean isOrdered() {
        return self.isOrdered();
    }

    public void setIsOrdered(boolean value) {
        self.setIsOrdered(value);
    }

    public boolean isUnique() {
        return self.isUnique();
    }

    public void setIsUnique(boolean value) {
        self.setIsUnique(value);
    }

    public int getLower() {
        return self.getLower();
    }

    public void setLower(int value) {
        self.setLower(value);
    }

    public long getUpper() {
        return self.getUpper();
    }

    public void setUpper(long value) {
        self.setUpper(value);
    }

    public int lowerBound()  {
        return self.lowerBound();
    }

    public long upperBound()  {
        return self.upperBound();
    }

    public boolean isMultivalued()  {
        return self.isMultivalued();
    }

    public boolean includesCardinality(int C)  {
        return self.includesCardinality(C);
    }

    public boolean includesMultiplicity(core.abstractions.multiplicities.MultiplicityElement M)  {
        return self.includesMultiplicity(M);
    }

}

