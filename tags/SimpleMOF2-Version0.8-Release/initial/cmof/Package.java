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
 * <b>Package</b>, superClass = {cmof.PackageableElement, cmof.Namespace, core.basic.Package}
 * <br>constraint - elements_public_or_private : 
 * <pre>self.ownedElements->forAll(e | e.visibility->notEmpty() implies e.visbility = #public or e.visibility = #private)</pre>
 */
public interface Package extends cmof.PackageableElement, cmof.Namespace, core.basic.Package
{

    /**
     * <b>ownedMember</b>, multiplicity=(0,*), isComposite
     */
    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> getOwnedMember();

    /**
     * <b>ownedType</b>, multiplicity=(0,*), isComposite
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Type> getOwnedType();

    /**
     * <b>nestedPackage</b>, multiplicity=(0,*), isComposite
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Package> getNestedPackage();

    /**
     * <b>nestingPackage</b>, multiplicity=(0,1), subsettedProperty = {cmof.NamedElement.namespace}
     */
    public cmof.Package getNestingPackage();

    public void setNestingPackage(cmof.Package value);

    public void setNestingPackage(core.basic.Package value);

    /**
     * <b>packageMerge</b>, multiplicity=(0,*), isComposite
     */
    public cmof.common.ReflectiveCollection<? extends cmof.PackageMerge> getPackageMerge();

    /**
     * <b>mustBeOwned</b>
     */
    public boolean mustBeOwned() ;

    /**
     * <b>visibleMembers</b>
     */
    public cmof.PackageableElement visibleMembers() ;

    /**
     * <b>makesVisible</b>
     */
    public boolean makesVisible(cmof.NamedElement el) ;

}

