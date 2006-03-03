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

package core.abstractions.umlsuper;


/**
 * <b>Classifier</b>, isAbstract, superClass = {core.abstractions.classifiers.Classifier}
 * <br>constraint - no_cycles_in_generalization : 
 * <pre>not self.allParents()->includes(self)</pre>
 * <br>constraint - specialize_type : 
 * <pre>self.parents()->forAll(c | self.maySpecializeType(c))</pre>
 * <br>constraint - inherited_member : 
 * <pre>self.inheritedMember->includesAll(self.inherit(self.parents()->collect(p | p.inheritableMembers(self)))</pre>
 */
public interface Classifier extends core.abstractions.classifiers.Classifier
{

    /**
     * <b>isAbstract</b>, multiplicity=(1,1)
     */
    public boolean isAbstract();

    public void setIsAbstract(boolean value);

    /**
     * <b>inheritedMember</b>, multiplicity=(0,*), isDerived
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement> getInheritedMember();

    /**
     * <b>general</b>, multiplicity=(0,*)
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.umlsuper.Classifier> getGeneral();

    /**
     * <b>inheritedMember</b>
     */
    public core.abstractions.namespaces.NamedElement inheritedMemberOperation() ;

    /**
     * <b>parents</b>
     */
    public core.abstractions.umlsuper.Classifier parents() ;

    /**
     * <b>allParents</b>
     */
    public core.abstractions.umlsuper.Classifier allParents() ;

    /**
     * <b>inheritableMembers</b>
     */
    public core.abstractions.namespaces.NamedElement inheritableMembers(core.abstractions.umlsuper.Classifier c) ;

    /**
     * <b>hasVisibilityOf</b>
     */
    public boolean hasVisibilityOf(core.abstractions.namespaces.NamedElement n) ;

    /**
     * <b>inherit</b>
     */
    public core.abstractions.namespaces.NamedElement inherit(core.abstractions.namespaces.NamedElement inhs) ;

    /**
     * <b>maySpecializeType</b>
     */
    public boolean maySpecializeType(core.abstractions.umlsuper.Classifier c) ;

}

