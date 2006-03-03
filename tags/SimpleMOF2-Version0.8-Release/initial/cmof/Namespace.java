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


/**
 * <b>Namespace</b>, isAbstract, superClass = {core.abstractions.constraints.Namespace, cmof.NamedElement}
 * <br>constraint - importedMember_derived : 
 * <pre>self.importedMember->includesAll(self.importedMembers(self.elementImport.importedElement.asSet()->union(self.packageImport.importedPackage->collect(p | p.visibleMembers()))))</pre>
 */
public interface Namespace extends core.abstractions.constraints.Namespace, cmof.NamedElement
{

    /**
     * <b>importedMember</b>, multiplicity=(0,*), isDerived
     */
    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> getImportedMember();

    /**
     * <b>elementImport</b>, multiplicity=(0,*), isComposite
     */
    public cmof.common.ReflectiveCollection<? extends cmof.ElementImport> getElementImport();

    /**
     * <b>packageImport</b>, multiplicity=(0,*), isComposite
     */
    public cmof.common.ReflectiveCollection<? extends cmof.PackageImport> getPackageImport();

    /**
     * <b>ownedRule</b>, multiplicity=(0,*), isComposite
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Constraint> getOwnedRule();

    /**
     * <b>ownedMember</b>, multiplicity=(0,*), isDerived
     */
    public cmof.common.ReflectiveCollection<? extends cmof.NamedElement> getOwnedMember();

    /**
     * <b>member</b>, multiplicity=(0,*), isDerived
     */
    public cmof.common.ReflectiveCollection<? extends cmof.NamedElement> getMember();

    /**
     * <b>importedMember</b>
     */
    public cmof.PackageableElement importedMemberOperation() ;

    /**
     * <b>getNamesOfMember</b>
     */
    public String getNamesOfMember(core.abstractions.namespaces.NamedElement element) ;

    /**
     * <b>getNamesOfMember</b>
     */
    public String getNamesOfMember() ;

    /**
     * <b>importMembers</b>
     */
    public cmof.PackageableElement importMembers(cmof.PackageableElement imps) ;

    /**
     * <b>excludeCollisions</b>
     */
    public cmof.PackageableElement excludeCollisions(cmof.PackageableElement imps) ;

}

