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


public class ConstraintDlg extends hub.sam.mof.reflection.ObjectImpl implements Constraint
{
    public ConstraintDlg(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public ConstraintDlg(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }

    public boolean mustBeOwned()  {
        return (Boolean)invokeOperation("mustBeOwned", new java.lang.Object[] {  });
    }

    public String separator()  {
        return (String)invokeOperation("separator", new java.lang.Object[] {  });
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getOwnedElement() {
        java.lang.Object value = get("ownedElement");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<core.abstractions.ownerships.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveSequence<? extends core.abstractions.ownerships.Element> getConstrainedElement() {
        java.lang.Object value = get("constrainedElement");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperListImpl<core.abstractions.ownerships.Element>((cmof.common.ReflectiveSequence)value);
        }
    }

    public core.abstractions.constraints.Namespace getNamespace() {
        java.lang.Object value = get("namespace");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.constraints.Namespace)value;
        }
    }

    public void setNamespace(core.abstractions.constraints.Namespace value) {
        set("namespace", value);
    }

    public void setNamespace(core.abstractions.namespaces.Namespace value) {
        set("namespace", value);
    }

    public core.abstractions.namespaces.Namespace getContext() {
        java.lang.Object value = get("context");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.namespaces.Namespace)value;
        }
    }

    public core.abstractions.ownerships.Element allOwnedElements()  {
        return (core.abstractions.ownerships.Element)invokeOperation("allOwnedElements", new java.lang.Object[] {  });
    }

    public core.abstractions.expressions.ValueSpecification getSpecification() {
        java.lang.Object value = get("specification");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.expressions.ValueSpecification)value;
        }
    }

    public void setSpecification(core.abstractions.expressions.ValueSpecification value) {
        set("specification", value);
    }

    public core.abstractions.ownerships.Element getOwner() {
        java.lang.Object value = get("owner");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.ownerships.Element)value;
        }
    }

    public boolean isDistinguishableFrom(core.abstractions.namespaces.NamedElement n, core.abstractions.namespaces.Namespace ns)  {
        return (Boolean)invokeOperation("isDistinguishableFrom_n_ns", new java.lang.Object[] { n, ns });
    }

    public core.abstractions.namespaces.Namespace allNamespaces()  {
        return (core.abstractions.namespaces.Namespace)invokeOperation("allNamespaces", new java.lang.Object[] {  });
    }

    public String getQualifiedName() {
        java.lang.Object value = get("qualifiedName");
        if (value == null) {
           return null;
        } else {
            return (String)value;
        }
    }

    public String getName() {
        java.lang.Object value = get("name");
        if (value == null) {
           return null;
        } else {
            return (String)value;
        }
    }

    public void setName(String value) {
        set("name", value);
    }

    public String qualifiedNameOperation()  {
        return (String)invokeOperation("qualifiedName", new java.lang.Object[] {  });
    }

}

