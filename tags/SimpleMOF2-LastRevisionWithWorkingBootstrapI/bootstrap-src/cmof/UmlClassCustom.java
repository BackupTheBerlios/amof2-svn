package cmof;

import cmof.common.ReflectiveCollection;
import hub.sam.mof.instancemodel.*;
import cmofimpl.reflection.*;
import cmofimpl.util.*;

public class UmlClassCustom extends UmlClassImpl {

    public UmlClassCustom(ClassInstance instance, ExtentImpl extent) {
        super(instance, extent);
    }
    
    public UmlClassCustom(Identifier a1, Identifier a2, String a3, String[] a4) {
        super(a1, a2, a3, a4);
    }

    public ReflectiveCollection<? extends NamedElement> getMember() {
        ReflectiveCollection<? extends NamedElement> members = new SetImpl<NamedElement>(getOwnedMember());
        ReflectiveCollection<? extends NamedElement> inheritedMembers = getInheritedMember();
        members.addAll(inheritedMembers);
        return members;
    }
    
    public ReflectiveCollection<? extends NamedElement> getInheritedMember() {
        ReflectiveCollection<? extends NamedElement> inheritedMembers = new SetImpl<NamedElement>();
        for(cmof.UmlClass superClass: getSuperClass()) {
            inheritedMembers.addAll(superClass.getMember());
        }
        ReflectiveCollection<? extends NamedElement> result = new SetImpl<NamedElement>(inheritedMembers);
        for(NamedElement inheritedMember: inheritedMembers) {
            if (inheritedMember instanceof RedefinableElement) {      
                for (RedefinableElement redefinedInheritedMember: ((RedefinableElement)inheritedMember).getRedefinedElement()) {
                    if (redefinedInheritedMember.getName().equals(inheritedMember.getName())) {
                        result.remove(redefinedInheritedMember);
                    }
                }                
            }
        }
        for(NamedElement ownedMember: getOwnedMember()) {
            if (ownedMember instanceof RedefinableElement) {
                for (RedefinableElement redefinedOwnedElement: ((RedefinableElement)ownedMember).getRedefinedElement()) {
                    if (redefinedOwnedElement.getName().equals(ownedMember.getName())) {
                        result.remove(redefinedOwnedElement);
                    }
                }                
            }
        }
        return result;
    }
    
}
