package cmofimpl.bootstrap;

import cmofimpl.util.*;
import cmof.*;
import cmof.common.ReflectiveSequence;
import cmof.common.ReflectiveCollection;

public class B1Association extends B1Element implements cmof.Association {

    private final ReflectiveSequence<cmof.Property> ends;
    
    public B1Association(String name, cmof.Property one, cmof.Property two) {
        super(name);
        ends = ListImpl.asList(new cmof.Property[] {one, two});  
        for (cmof.Property end: ends) {
            if (end.getOwner() == null) {
                end.setOwningAssociation(this);
            }
            end.setAssociation(this);
        }
    }

    public ReflectiveSequence<cmof.Property> getMemberEnd() {
        return ends;
    }
    
    public cmof.Package getPackage() {
        return null;
    }

    public void setPackage(cmof.Package value) {
        notInBootstrap();
    }

    public ReflectiveSequence<cmof.Property> getOwnedEnd() {
        ReflectiveSequence<cmof.Property> result = new ListImpl<cmof.Property>();
        for (cmof.Property end: ends) {
            if (end.getClass() == null) {
                result.add(end);
            }
        }        
        return result;
    }

    public ReflectiveCollection<Type> getEndType() {
        notInBootstrap();
        return null;
    }

    public ReflectiveCollection<Element> getRelatedElement() {
        notInBootstrap();
        return null;
    }

    public boolean isAbstract() {
        notInBootstrap();
        return false;
    }

    public void setAbstract(boolean value) {
        notInBootstrap();
    }

    public ReflectiveCollection<Classifier> getGeneral() {
        notInBootstrap();
        return null;
    }

    public ReflectiveCollection<Feature> getFeature() {
        notInBootstrap();
        return null;
    }

    public ReflectiveCollection<NamedElement> getInheritedMember() {
        notInBootstrap();
        return null;
    }

    public ReflectiveCollection<Constraint> getOwnedRule() {
        notInBootstrap();
        return null;
    }

    public ReflectiveCollection<PackageableElement> getImportedMemeber() {
        notInBootstrap();
        return null;
    }

    public ReflectiveCollection<NamedElement> getMember() {
        notInBootstrap();
        return null;
    }

    public ReflectiveCollection<? extends NamedElement> getOwnedMember() {
        notInBootstrap();
        return null;
    }

    public ReflectiveCollection<ElementImport> getElementImport() {
        notInBootstrap();
        return null;
    }

    public ReflectiveCollection<PackageImportImpl> getPackageImport() {
        notInBootstrap();
        return null;
    }

    public boolean isDerived() {
        return false;
    }

    public void setIsDerived(boolean value) {
    }

    public ReflectiveCollection<? extends Property> getAttribute() {
        return null;
    }

    public ReflectiveCollection<? extends PackageableElement> getImportedMember() {
        return null;
    }

    public void setIsAbstract(boolean value) {
    }

    public void setPackage(core.basic.Package value) {
    }

    public boolean conformsTo(Classifier other) {
        return false;
    }

    public PackageableElement importedMemberOperation() {
        return null;
    }

    public String getNamesOfMember() {
        return null;
    }

    public PackageableElement importMembers(PackageableElement imps) {
        return null;
    }

    public PackageableElement excludeCollisions(PackageableElement imps) {
        return null;
    }

    public String getNamesOfMember(core.abstractions.namespaces.NamedElement element) {
        return null;
    }

    public boolean membersAreDistinguishable() {
        return false;
    }

    public NamedElement inheritedMemberOperation() {
        return null;
    }

    public core.abstractions.umlsuper.Classifier parents() {
        return null;
    }

    public core.abstractions.umlsuper.Classifier allParents() {
        return null;
    }

    public NamedElement inheritableMembers(core.abstractions.umlsuper.Classifier c) {
        return null;
    }

    public boolean hasVisibilityOf(core.abstractions.namespaces.NamedElement n) {
        return false;
    }

    public NamedElement inherit(core.abstractions.namespaces.NamedElement inhs) {
        return null;
    }

    public boolean maySpecializeType(core.abstractions.umlsuper.Classifier c) {
        return false;
    }

    public Feature allFeatures() {
        return null;
    }

    public boolean conformsTo(core.abstractions.typedelements.Type other) {
        return false;
    }  
}
