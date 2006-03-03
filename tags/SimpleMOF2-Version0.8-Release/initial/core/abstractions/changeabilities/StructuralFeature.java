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

package core.abstractions.changeabilities;


/**
 * <b>StructuralFeature</b>, isAbstract, superClass = {core.abstractions.structuralfeatures.StructuralFeature}
 */
public interface StructuralFeature extends core.abstractions.structuralfeatures.StructuralFeature
{

    /**
     * <b>isReadOnly</b>, multiplicity=(1,1)
     */
    public boolean isReadOnly();

    public void setIsReadOnly(boolean value);

}

