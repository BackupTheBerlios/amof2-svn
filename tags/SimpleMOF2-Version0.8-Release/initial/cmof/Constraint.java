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
 * <b>Constraint</b>, superClass = {core.abstractions.constraints.Constraint, cmof.PackageableElement}
 */
public interface Constraint extends core.abstractions.constraints.Constraint, cmof.PackageableElement
{

    /**
     * <b>context</b>, multiplicity=(0,1), isDerived
     */
    public cmof.Namespace getContext();

    /**
     * <b>namespace</b>, multiplicity=(0,1), subsettedProperty = {cmof.Constraint.context}
     */
    public cmof.Namespace getNamespace();

    public void setNamespace(cmof.Namespace value);

    public void setNamespace(core.abstractions.constraints.Namespace value);

    public void setNamespace(core.abstractions.namespaces.Namespace value);

    /**
     * <b>constrainedElement</b>, multiplicity=(0,*), redefinedProperty = {core.abstractions.constraints.Constraint.constrainedElement}
     */
    public cmof.common.ReflectiveSequence<? extends cmof.Element> getConstrainedElement();

    /**
     * <b>specification</b>, multiplicity=(1,1), isComposite
     */
    public cmof.ValueSpecification getSpecification();

    public void setSpecification(cmof.ValueSpecification value);

    public void setSpecification(core.abstractions.expressions.ValueSpecification value);

}

