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

package core.basic;


/**
 * <b>Operation</b>, superClass = {core.basic.TypedElement, core.abstractions.multiplicities.MultiplicityElement}
 */
public interface Operation extends core.basic.TypedElement, core.abstractions.multiplicities.MultiplicityElement
{

    /**
     * <b>raisedException</b>, multiplicity=(0,*)
     */
    public cmof.common.ReflectiveCollection<? extends core.basic.Type> getRaisedException();

    /**
     * <b>ownedParameter</b>, multiplicity=(0,*), isComposite
     */
    public cmof.common.ReflectiveSequence<? extends core.basic.Parameter> getOwnedParameter();

    /**
     * <b>class</b>, multiplicity=(0,1)
     */
    public core.basic.UmlClass getUmlClass();

    public void setUmlClass(core.basic.UmlClass value);

}

