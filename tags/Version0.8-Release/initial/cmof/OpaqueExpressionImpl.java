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


public class OpaqueExpressionImpl extends hub.sam.mof.reflection.ObjectImpl implements OpaqueExpression
{
    public OpaqueExpressionImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public OpaqueExpressionImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Comment> getOwnedComment() {
        java.lang.Object value = get("ownedComment");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Comment>((cmof.common.ReflectiveCollection)value);
        }
    }

    public core.abstractions.ownerships.Element allOwnedElements()  {
        return (core.abstractions.ownerships.Element)invokeOperation("", new java.lang.Object[] {  });
    }

    public String stringValue()  {
        return (String)invokeOperation("", new java.lang.Object[] {  });
    }

    public cmof.Namespace getNamespace() {
        java.lang.Object value = get("namespace");
        if (value == null) {
           return null;
        } else {
            return (cmof.Namespace)value;
        }
    }

    public cmof.Element getOwner() {
        java.lang.Object value = get("owner");
        if (value == null) {
           return null;
        } else {
            return (cmof.Element)value;
        }
    }

    public long unlimitedValue()  {
        return (Long)invokeOperation("", new java.lang.Object[] {  });
    }

    public String getQualifiedName() {
        java.lang.Object value = get("qualifiedName");
        if (value == null) {
           return null;
        } else {
            return (String)value;
        }
    }

    public core.abstractions.namespaces.Namespace allNamespaces()  {
        return (core.abstractions.namespaces.Namespace)invokeOperation("", new java.lang.Object[] {  });
    }

    public boolean booleanValue()  {
        return (Boolean)invokeOperation("", new java.lang.Object[] {  });
    }

    public String separator()  {
        return (String)invokeOperation("", new java.lang.Object[] {  });
    }

    public String getBody() {
        java.lang.Object value = get("body");
        if (value == null) {
           return null;
        } else {
            return (String)value;
        }
    }

    public void setBody(String value) {
        set("body", value);
    }

    public String qualifiedNameOperation()  {
        return (String)invokeOperation("", new java.lang.Object[] {  });
    }

    public boolean mustBeOwned()  {
        return (Boolean)invokeOperation("", new java.lang.Object[] {  });
    }

    public cmof.Type getType() {
        java.lang.Object value = get("type");
        if (value == null) {
           return null;
        } else {
            return (cmof.Type)value;
        }
    }

    public void setType(cmof.Type value) {
        set("type", value);
    }

    public void setType(core.abstractions.typedelements.Type value) {
        set("type", value);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getOwnedElement() {
        java.lang.Object value = get("ownedElement");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

    public core.abstractions.visibilities.VisibilityKind getVisibility() {
        java.lang.Object value = get("visibility");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.visibilities.VisibilityKind)value;
        }
    }

    public void setVisibility(core.abstractions.visibilities.VisibilityKind value) {
        set("visibility", value);
    }

    public int integerValue()  {
        return (Integer)invokeOperation("", new java.lang.Object[] {  });
    }

    public String getLanguage() {
        java.lang.Object value = get("language");
        if (value == null) {
           return null;
        } else {
            return (String)value;
        }
    }

    public void setLanguage(String value) {
        set("language", value);
    }

    public boolean isNull()  {
        return (Boolean)invokeOperation("", new java.lang.Object[] {  });
    }

    public boolean isComputable()  {
        return (Boolean)invokeOperation("", new java.lang.Object[] {  });
    }

    public boolean isDistinguishableFrom(core.abstractions.namespaces.NamedElement n, core.abstractions.namespaces.Namespace ns)  {
        return (Boolean)invokeOperation("_nisDistinguishableFrom_ns", new java.lang.Object[] { n, ns });
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

}

