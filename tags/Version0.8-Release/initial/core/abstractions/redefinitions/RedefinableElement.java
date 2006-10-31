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

package core.abstractions.redefinitions;


/**
 * <b>RedefinableElement</b>, isAbstract, superClass = {core.abstractions.namespaces.NamedElement}
 * <br>constraint - redefinition_context_valid : 
 * <pre>self.redefinedElement->forAll(e | self.isRedefinitionContextValid(e))</pre>
 * <br>constraint - redefinition_consistent : 
 * <pre>self.redefinedElement->forAll(re | re.isConsistentWith(self))</pre>
 */
public interface RedefinableElement extends core.abstractions.namespaces.NamedElement
{

    /**
     * <b>redefinedElement</b>, multiplicity=(0,*), isDerived
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.redefinitions.RedefinableElement> getRedefinedElement();

    /**
     * <b>redefinitionContext</b>, multiplicity=(0,*), isDerived
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.umlsuper.Classifier> getRedefinitionContext();

    /**
     * <b>isConsistentWith</b>
     */
    public boolean isConsistentWith(core.abstractions.redefinitions.RedefinableElement redefinee) ;

    /**
     * <b>isRedefinitionContextValid</b>
     */
    public boolean isRedefinitionContextValid(core.abstractions.redefinitions.RedefinableElement redefinable) ;

}

