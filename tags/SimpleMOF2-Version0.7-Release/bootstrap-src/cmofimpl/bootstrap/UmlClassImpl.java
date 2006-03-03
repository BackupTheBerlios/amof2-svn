package cmofimpl.bootstrap;

import cmofimpl.instancemodel.ClassInstanceImpl;
import cmofimpl.reflection.ExtentImpl;
import cmofimpl.reflection.Identifier;
import cmof.*;
import cmof.common.ReflectiveSequence;
import cmof.common.ReflectiveCollection;
import cmofimpl.util.*;

public class UmlClassImpl extends cmof.UmlClassImpl {

    public UmlClassImpl(ClassInstanceImpl instance, ExtentImpl extent) {
        super(instance, extent);
    }

    public UmlClassImpl(Identifier a1, Identifier a2, String a3, String[] a4) {
        super(a1, a2, a3, a4);
    }

    public String getName() {
        return (String)get("name");
    }
    
    public String getQualifiedName() {
        return getName();
    }
    
    public void setName(String value) {
        set("name", value);
    }
    
    public ReflectiveCollection<? extends NamedElement> getMember() {
        ReflectiveCollection<? extends NamedElement> members = new SetImpl<NamedElement>(getOwnedMember());
        ReflectiveCollection<? extends NamedElement> inheritedMembers = getInheritedMember();
        members.addAll(inheritedMembers);
        return members;
    }
    
    public cmof.Package getPackage() {
        return (cmof.Package)getOwner();
    }
    
    public cmof.Element getOwner() {
        return (cmof.Element)get("owner");
    }
    
    public ReflectiveCollection<? extends NamedElement> getOwnedMember() {
        return (ReflectiveCollection<? extends NamedElement>)get("ownedMember");
    }
    
    public ReflectiveCollection<? extends NamedElement> getInheritedMember() {
        ReflectiveCollection<? extends NamedElement> inheritedMembers = new SetImpl<NamedElement>();
        for(cmof.UmlClass superClass: getSuperClass()) {
            inheritedMembers.addAll(superClass.getMember());
        }
        ReflectiveCollection<? extends NamedElement> result = new SetImpl<NamedElement>(inheritedMembers);
        for(NamedElement inheritedMember: inheritedMembers) {
            if (inheritedMember instanceof cmof.Property) {      
                for (Property redefinedInheritedProperty: ((cmof.Property)inheritedMember).getRedefinedProperty()) {
                    if (redefinedInheritedProperty.getName().equals(inheritedMember.getName())) {
                        result.remove(redefinedInheritedProperty);
                    }
                }                
            }
        }
        for(NamedElement ownedMember: getOwnedMember()) {
            if (ownedMember instanceof cmof.Property) {
                for (Property redefinedOwnedProperty: ((cmof.Property)ownedMember).getRedefinedProperty()) {
                    if (redefinedOwnedProperty.getName().equals(ownedMember.getName())) {
                        result.remove(redefinedOwnedProperty);
                    }
                }                
            }
        }
        return result;
    }
    
    public ReflectiveSequence<? extends cmof.UmlClass> getSuperClass() {
        return (ReflectiveSequence<? extends cmof.UmlClass>)get("superClass");
    }
    
    public ReflectiveSequence<? extends cmof.Classifier> getGeneral() {
        return getSuperClass();
    }
        
    public boolean isAbstract() {
        return (Boolean)get("isAbstract");
    }
    
    public ReflectiveSequence<? extends cmof.Property> getOwnedAttribute() {
        return (ReflectiveSequence<? extends cmof.Property>)get("ownedAttribute");
    }
    
    public ReflectiveCollection<? extends Constraint> getOwnedRule() {
        return new SetImpl<Constraint>();
    }
}
