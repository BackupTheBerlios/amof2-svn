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

package core.abstractions.constraints;


/**
 * <b>Constraint</b>, superClass = {core.abstractions.namespaces.NamedElement}
 * <br>constraint - not_apply_to_self : 
 * <pre>not constrainedElement->includes(self)</pre>
 */
public interface Constraint extends core.abstractions.namespaces.NamedElement
{

    /**
     * <b>context</b>, multiplicity=(0,1), isDerived
     */
    public core.abstractions.namespaces.Namespace getContext();

    /**
     * <b>namespace</b>, multiplicity=(0,1), subsettedProperty = {core.abstractions.constraints.Constraint.context}
     */
    public core.abstractions.constraints.Namespace getNamespace();

    public void setNamespace(core.abstractions.constraints.Namespace value);

    public void setNamespace(core.abstractions.namespaces.Namespace value);

    /**
     * <b>specification</b>, multiplicity=(1,1), isComposite
     */
    public core.abstractions.expressions.ValueSpecification getSpecification();

    public void setSpecification(core.abstractions.expressions.ValueSpecification value);

    /**
     * <b>constrainedElement</b>, multiplicity=(0,*)
     */
    public cmof.common.ReflectiveSequence<? extends core.abstractions.ownerships.Element> getConstrainedElement();

}

