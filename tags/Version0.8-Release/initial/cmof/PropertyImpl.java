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


public class PropertyImpl extends hub.sam.mof.reflection.ObjectImpl implements Property
{
    public PropertyImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public PropertyImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
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

    public boolean isUnique() {
        java.lang.Object value = get("isUnique");
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public void setIsUnique(boolean value) {
        set("isUnique", value);
    }

    public cmof.Classifier subsettingContext()  {
        return (cmof.Classifier)invokeOperation("", new java.lang.Object[] {  });
    }

    public core.abstractions.ownerships.Element allOwnedElements()  {
        return (core.abstractions.ownerships.Element)invokeOperation("", new java.lang.Object[] {  });
    }

    public cmof.Namespace getNamespace() {
        java.lang.Object value = get("namespace");
        if (value == null) {
           return null;
        } else {
            return (cmof.Namespace)value;
        }
    }

    public cmof.Property oppositeOperation()  {
        return (cmof.Property)invokeOperation("", new java.lang.Object[] {  });
    }

    public boolean isComposite() {
        java.lang.Object value = get("isComposite");
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public void setIsComposite(boolean value) {
        set("isComposite", value);
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

    public boolean isDerived() {
        java.lang.Object value = get("isDerived");
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public void setIsDerived(boolean value) {
        set("isDerived", value);
    }

    public cmof.UmlClass getUmlClass() {
        java.lang.Object value = get("umlClass");
        if (value == null) {
           return null;
        } else {
            return (cmof.UmlClass)value;
        }
    }

    public void setUmlClass(cmof.UmlClass value) {
        set("umlClass", value);
    }

    public void setUmlClass(core.basic.UmlClass value) {
        set("umlClass", value);
    }

    public boolean mustBeOwned()  {
        return (Boolean)invokeOperation("", new java.lang.Object[] {  });
    }

    public boolean includesMultiplicity(core.abstractions.multiplicities.MultiplicityElement M)  {
        return (Boolean)invokeOperation("_M", new java.lang.Object[] { M });
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

    public cmof.Property getOpposite() {
        java.lang.Object value = get("opposite");
        if (value == null) {
           return null;
        } else {
            return (cmof.Property)value;
        }
    }

    public void setOpposite(cmof.Property value) {
        set("opposite", value);
    }

    public void setOpposite(core.basic.Property value) {
        set("opposite", value);
    }

    public String getDefault() {
        java.lang.Object value = get("default");
        if (value == null) {
           return null;
        } else {
            return (String)value;
        }
    }

    public void setDefault(String value) {
        set("default", value);
    }

    public boolean isConsistentWith(core.abstractions.redefinitions.RedefinableElement redefinee)  {
        return (Boolean)invokeOperation("_redefinee", new java.lang.Object[] { redefinee });
    }

    public boolean isConsistentWith(cmof.RedefinableElement redefinee)  {
        return (Boolean)invokeOperation("_redefinee", new java.lang.Object[] { redefinee });
    }

    public cmof.DataType getDatatype() {
        java.lang.Object value = get("datatype");
        if (value == null) {
           return null;
        } else {
            return (cmof.DataType)value;
        }
    }

    public void setDatatype(cmof.DataType value) {
        set("datatype", value);
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

    public boolean isDerivedUnion() {
        java.lang.Object value = get("isDerivedUnion");
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public void setIsDerivedUnion(boolean value) {
        set("isDerivedUnion", value);
    }

    public long upperBound()  {
        return (Long)invokeOperation("", new java.lang.Object[] {  });
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

    public long getUpper() {
        java.lang.Object value = get("upper");
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Long)value;
        }
    }

    public void setUpper(long value) {
        set("upper", value);
    }

    public cmof.Association getAssociation() {
        java.lang.Object value = get("association");
        if (value == null) {
           return null;
        } else {
            return (cmof.Association)value;
        }
    }

    public void setAssociation(cmof.Association value) {
        set("association", value);
    }

    public cmof.Element getOwner() {
        java.lang.Object value = get("owner");
        if (value == null) {
           return null;
        } else {
            return (cmof.Element)value;
        }
    }

    public int getLower() {
        java.lang.Object value = get("lower");
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Integer)value;
        }
    }

    public void setLower(int value) {
        set("lower", value);
    }

    public int lowerBound()  {
        return (Integer)invokeOperation("", new java.lang.Object[] {  });
    }

    public cmof.common.ReflectiveCollection<? extends cmof.RedefinableElement> getRedefinedElement() {
        java.lang.Object value = get("redefinedElement");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.RedefinableElement>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Property> getRedefinedProperty() {
        java.lang.Object value = get("redefinedProperty");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Property>((cmof.common.ReflectiveCollection)value);
        }
    }

    public String separator()  {
        return (String)invokeOperation("", new java.lang.Object[] {  });
    }

    public String qualifiedNameOperation()  {
        return (String)invokeOperation("", new java.lang.Object[] {  });
    }

    public boolean isReadOnly() {
        java.lang.Object value = get("isReadOnly");
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public void setIsReadOnly(boolean value) {
        set("isReadOnly", value);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getRedefinitionContext() {
        java.lang.Object value = get("redefinitionContext");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Classifier>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.Association getOwningAssociation() {
        java.lang.Object value = get("owningAssociation");
        if (value == null) {
           return null;
        } else {
            return (cmof.Association)value;
        }
    }

    public void setOwningAssociation(cmof.Association value) {
        set("owningAssociation", value);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getFeaturingClassifier() {
        java.lang.Object value = get("featuringClassifier");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Classifier>((cmof.common.ReflectiveCollection)value);
        }
    }

    public boolean isRedefinitionContextValid(core.abstractions.redefinitions.RedefinableElement redefinable)  {
        return (Boolean)invokeOperation("_redefinable", new java.lang.Object[] { redefinable });
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getOwnedElement() {
        java.lang.Object value = get("ownedElement");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Property> getSubsettedProperty() {
        java.lang.Object value = get("subsettedProperty");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Property>((cmof.common.ReflectiveCollection)value);
        }
    }

    public String getDetails() {
        java.lang.Object value = get("details");
        if (value == null) {
           return null;
        } else {
            return (String)value;
        }
    }

    public void setDetails(String value) {
        set("details", value);
    }

    public boolean isOrdered() {
        java.lang.Object value = get("isOrdered");
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public void setIsOrdered(boolean value) {
        set("isOrdered", value);
    }

    public boolean isMultivalued()  {
        return (Boolean)invokeOperation("", new java.lang.Object[] {  });
    }

    public boolean includesCardinality(int C)  {
        return (Boolean)invokeOperation("_C", new java.lang.Object[] { C });
    }

}

