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
 * <b>Package</b>, superClass = {core.basic.NamedElement}
 */
public interface Package extends core.basic.NamedElement
{

    /**
     * <b>nestedPackage</b>, multiplicity=(0,*), isComposite
     */
    public cmof.common.ReflectiveCollection<? extends core.basic.Package> getNestedPackage();

    /**
     * <b>nestingPackage</b>, multiplicity=(0,1)
     */
    public core.basic.Package getNestingPackage();

    public void setNestingPackage(core.basic.Package value);

    /**
     * <b>ownedType</b>, multiplicity=(0,*), isComposite
     */
    public cmof.common.ReflectiveCollection<? extends core.basic.Type> getOwnedType();

}
