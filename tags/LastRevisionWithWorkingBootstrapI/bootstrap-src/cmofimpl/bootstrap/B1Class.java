package cmofimpl.bootstrap;

import cmofimpl.util.*;
import cmof.*;
import cmof.common.ReflectiveSequence;
import cmof.common.ReflectiveCollection;

public class B1Class extends B1Element implements cmof.UmlClass {

    private ReflectiveSequence<cmof.Property> propertys;
    private ReflectiveSequence<B1Class> superClasses = new cmofimpl.util.ListImpl<B1Class>();
        
    public B1Class(String name) {
        super(name);
    }
    
    public B1Class init(cmof.Property[] propertys) {        
        this.propertys = cmofimpl.util.ListImpl.asList(propertys);
        for (cmof.Property property: propertys) {
            ((B1Property)property).setOwner(this);
        }
        return this;
    }

    public ReflectiveCollection<? extends NamedElement> getMember() {
        ReflectiveCollection<? extends NamedElement> result = new ListImpl<NamedElement>();
        for (cmof.UmlClass superClass: getSuperClass()) {
            result.addAll(superClass.getMember());
        }
        result.addAll(propertys);
        return result;
    }
    
    public cmof.Package getPackage() {        
        return null;
    }

    public void setPackage(cmof.Package value) {
        notInBootstrap();
    }

    public ReflectiveSequence<cmof.Property> getOwnedAttribute() {
        return propertys;        
    }

    public ReflectiveSequence<Operation> getOwnedOperation() {
        notInBootstrap();
        return null;
    }

    public ReflectiveSequence<? extends cmof.UmlClass> getSuperClass() {       
        return superClasses;
    }

    public boolean isAbstract() {
        return false;
    }

    public void setAbstract(boolean value) {
        notInBootstrap();
    }

    public ReflectiveCollection<? extends Classifier> getGeneral() {
        return getSuperClass();
    }

    public ReflectiveCollection<cmof.Feature> getFeature() {
        notInBootstrap();
        return null;
    }

    public ReflectiveCollection<NamedElement> getInheritedMember() {
        notInBootstrap();
        return null;
    }

    public ReflectiveCollection<Constraint> getOwnedRule() {
        return new SetImpl<Constraint>();
    }

    public ReflectiveCollection<PackageableElement> getImportedMemeber() {
        notInBootstrap();
        return null;
    }

    public ReflectiveCollection<? extends NamedElement> getOwnedMember() {
        return propertys;        
    }

    public ReflectiveCollection<ElementImport> getElementImport() {
        notInBootstrap();
        return null;
    }

    public ReflectiveCollection<PackageImportImpl> getPackageImport() {
        notInBootstrap();
        return null;
    }

    public void setIsAbstract(boolean value) {
    }

    public ReflectiveCollection<? extends Property> getAttribute() {
        return null;
    }

    public ReflectiveCollection<? extends PackageableElement> getImportedMember() {
        return null;
    }
    public void setPackage(core.basic.Package value) {
    }

    public NamedElement inherit(NamedElement inhs) {
        return null;
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

    public core.abstractions.namespaces.NamedElement inheritedMemberOperation() {
        return null;
    }

    public core.abstractions.umlsuper.Classifier parents() {
        return null;
    }

    public core.abstractions.umlsuper.Classifier allParents() {
        return null;
    }

    public core.abstractions.namespaces.NamedElement inheritableMembers(core.abstractions.umlsuper.Classifier c) {
        return null;
    }

    public boolean hasVisibilityOf(core.abstractions.namespaces.NamedElement n) {
        return false;
    }

    public core.abstractions.namespaces.NamedElement inherit(core.abstractions.namespaces.NamedElement inhs) {
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
