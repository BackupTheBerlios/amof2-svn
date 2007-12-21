package core.abstractions.behavioralfeatures;


public class BehavioralFeatureDlg extends hub.sam.mof.reflection.ObjectDlg implements BehavioralFeature
{
    protected BehavioralFeature self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (BehavioralFeature)self;
    }

    public cmof.common.ReflectiveSequence<? extends core.abstractions.behavioralfeatures.Parameter> getParameter() {
        return (cmof.common.ReflectiveSequence<? extends core.abstractions.behavioralfeatures.Parameter>)(java.lang.Object)self.getParameter();
    }

    public boolean isDistinguishableFrom(core.abstractions.namespaces.NamedElement n, core.abstractions.namespaces.Namespace ns)  {
        return self.isDistinguishableFrom(n, ns);
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.classifiers.Classifier> getFeaturingClassifier() {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.classifiers.Classifier>)(java.lang.Object)self.getFeaturingClassifier();
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

    public core.abstractions.namespaces.Namespace getNamespace() {
        return (core.abstractions.namespaces.Namespace)(java.lang.Object)self.getNamespace();
    }

    public void setNamespace(core.abstractions.namespaces.Namespace value) {
        self.setNamespace(value);
    }

    public cmof.common.ReflectiveSequence<? extends core.abstractions.namespaces.Namespace> allNamespaces()  {
        return (cmof.common.ReflectiveSequence<? extends core.abstractions.namespaces.Namespace>)(java.lang.Object)self.allNamespaces();
    }

    public java.lang.String separator()  {
        return self.separator();
    }

    public java.lang.String qualifiedNameOperation()  {
        return self.qualifiedNameOperation();
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getOwnedElement() {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element>)(java.lang.Object)self.getOwnedElement();
    }

    public core.abstractions.ownerships.Element getOwner() {
        return (core.abstractions.ownerships.Element)(java.lang.Object)self.getOwner();
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

    public cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement> getMember() {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement>)(java.lang.Object)self.getMember();
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement> getOwnedMember() {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement>)(java.lang.Object)self.getOwnedMember();
    }

    public cmof.common.ReflectiveCollection<java.lang.String> getNamesOfMember(core.abstractions.namespaces.NamedElement element)  {
        return self.getNamesOfMember(element);
    }

    public boolean membersAreDistinguishable()  {
        return self.membersAreDistinguishable();
    }

}

