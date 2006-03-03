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
 * <b>DataType</b>, superClass = {core.basic.DataType, cmof.Classifier}
 */
public interface DataType extends core.basic.DataType, cmof.Classifier
{

    /**
     * <b>ownedAttribute</b>, multiplicity=(0,*), isComposite
     */
    public cmof.common.ReflectiveSequence<? extends cmof.Property> getOwnedAttribute();

    /**
     * <b>ownedOperation</b>, multiplicity=(0,*), isComposite
     */
    public cmof.common.ReflectiveSequence<? extends cmof.Operation> getOwnedOperation();

    /**
     * <b>inherit</b>
     */
    public cmof.NamedElement inherit(cmof.NamedElement inhs) ;

    /**
     * <b>inherit</b>
     */
    public core.abstractions.namespaces.NamedElement inherit(core.abstractions.namespaces.NamedElement inhs) ;

}

