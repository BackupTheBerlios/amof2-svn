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

package core.abstractions.ownerships;


/**
 * <b>Element</b>, isAbstract, superClass = {core.abstractions.elements.Element}
 * <br>constraint - not_own_self : 
 * <pre>not self.allOwnedElements()->includes(self)</pre>
 * <br>constraint - has_owner : 
 * <pre>self.mustBeOwned() implies owner->notEmpty()</pre>
 */
public interface Element extends core.abstractions.elements.Element
{

    /**
     * <b>ownedElement</b>, multiplicity=(0,*), isDerived
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getOwnedElement();

    /**
     * <b>owner</b>, multiplicity=(0,1), isDerived
     */
    public core.abstractions.ownerships.Element getOwner();

    /**
     * <b>allOwnedElements</b>
     */
    public core.abstractions.ownerships.Element allOwnedElements() ;

    /**
     * <b>mustBeOwned</b>
     */
    public boolean mustBeOwned() ;

}

