/*
A MOF 2 Java -- The MOF Repository tool for Java
Copyright (C) 2005 Markus Scheidgen

    This library is free software; you can redistribute it and/or modify it
under the terms of the GNU Lesser General Public License as published by the
Free Software Foundation; either version 2.1 of the License, or any later
version.

    This library is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
details.

    You should have received a copy of the GNU Lesser General Public License
along with this library; if not, write to the Free Software Foundation, Inc.,
59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
*/

package cmof;

import cmof.common.ReflectiveCollection;
import hub.sam.mof.instancemodel.*;
import hub.sam.mof.reflection.*;
import hub.sam.mof.util.SetImpl;

public class PackageCustom extends cmof.PackageDlg {
    public PackageCustom(ClassInstance instance, ExtentImpl extent) {
        super(instance, extent);
    }
    
    public PackageCustom(Identifier a1, Identifier a2, String a3, String[] a4) {
        super(a1, a2, a3, a4);
    }
  
    public ReflectiveCollection<? extends NamedElement> getMember() {
        ReflectiveCollection<? extends NamedElement> members = new SetImpl<NamedElement>(getOwnedMember());
        ReflectiveCollection<? extends NamedElement> importedMembers = getImportedMember();
        members.addAll(importedMembers);
        return members;
    }
}
