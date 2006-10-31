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
 * <b>PackageImport</b>, superClass = {cmof.DirectedRelationship}
 * <br>constraint - public_or_private : 
 * <pre>self.visibility = #public or self.visibility = #private</pre>
 */
public interface PackageImport extends cmof.DirectedRelationship
{

    /**
     * <b>visibility</b>, multiplicity=(1,1)
     */
    public core.abstractions.visibilities.VisibilityKind getVisibility();

    public void setVisibility(core.abstractions.visibilities.VisibilityKind value);

    /**
     * <b>importedPackage</b>, multiplicity=(1,1), subsettedProperty = {cmof.DirectedRelationship.target}
     */
    public cmof.Package getImportedPackage();

    public void setImportedPackage(cmof.Package value);

    /**
     * <b>importingNamespace</b>, multiplicity=(1,1), subsettedProperty = {cmof.DirectedRelationship.source, cmof.Element.owner}
     */
    public cmof.Namespace getImportingNamespace();

    public void setImportingNamespace(cmof.Namespace value);

}

