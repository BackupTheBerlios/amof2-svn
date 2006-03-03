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

package core.abstractions.namespaces;


/**
 * <b>NamedElement</b>, isAbstract, superClass = {core.abstractions.ownerships.Element}
 * <br>constraint - no_name : 
 * <pre>self.name->isEmpty() or self.allNamespaces()->select(ns | ns.name->isEmpty())->notEmpty() implies self.qualifiedName->isEmpty()</pre>
 * <br>constraint - qualified_name : 
 * <pre>(self.name->notEmpty() and self.allNamespaces()->select(ns | ns.name->isEmpty())->isEmpty()) implies self.qualifiedName = self.allNamespaces()->iterate( ns : Namespace; result: String = self.name | ns.name->union(self.separator())->union(result))</pre>
 */
public interface NamedElement extends core.abstractions.ownerships.Element
{

    /**
     * <b>name</b>, multiplicity=(0,1)
     */
    public String getName();

    public void setName(String value);

    /**
     * <b>qualifiedName</b>, multiplicity=(0,1), isDerived
     */
    public String getQualifiedName();

    /**
     * <b>namespace</b>, multiplicity=(0,1), isDerived
     */
    public core.abstractions.namespaces.Namespace getNamespace();

    /**
     * <b>allNamespaces</b>
     */
    public core.abstractions.namespaces.Namespace allNamespaces() ;

    /**
     * <b>isDistinguishableFrom</b>
     */
    public boolean isDistinguishableFrom(core.abstractions.namespaces.NamedElement n, core.abstractions.namespaces.Namespace ns) ;

    /**
     * <b>separator</b>
     */
    public String separator() ;

    /**
     * <b>qualifiedName</b>
     */
    public String qualifiedNameOperation() ;

}

