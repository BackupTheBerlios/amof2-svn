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


public class EnumerationDlg extends hub.sam.mof.reflection.ObjectImpl implements Enumeration
{
    public EnumerationDlg(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public EnumerationDlg(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }

    public cmof.common.ReflectiveSequence<? extends cmof.Property> getOwnedAttribute() {
        java.lang.Object value = get("ownedAttribute");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperListImpl<cmof.Property>((cmof.common.ReflectiveSequence)value);
        }
    }

    public String separator()  {
        return (String)invokeOperation("separator", new java.lang.Object[] {  });
    }

    public cmof.common.ReflectiveSequence<? extends cmof.EnumerationLiteral> getOwnedLiteral() {
        java.lang.Object value = get("ownedLiteral");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperListImpl<cmof.EnumerationLiteral>((cmof.common.ReflectiveSequence)value);
        }
    }

    public boolean isAbstract() {
        java.lang.Object value = get("isAbstract");
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public void setIsAbstract(boolean value) {
        set("isAbstract", value);
    }

    public cmof.PackageableElement importMembers(cmof.PackageableElement imps)  {
        return (cmof.PackageableElement)invokeOperation("importMembers_imps", new java.lang.Object[] { imps });
    }

    public core.abstractions.classifiers.Feature allFeatures()  {
        return (core.abstractions.classifiers.Feature)invokeOperation("allFeatures", new java.lang.Object[] {  });
    }

    public core.abstractions.umlsuper.Classifier allParents()  {
        return (core.abstractions.umlsuper.Classifier)invokeOperation("allParents", new java.lang.Object[] {  });
    }

    public cmof.Element getOwner() {
        java.lang.Object value = get("owner");
        if (value == null) {
           return null;
        } else {
            return (cmof.Element)value;
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getGeneral() {
        java.lang.Object value = get("general");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Classifier>((cmof.common.ReflectiveCollection)value);
        }
    }

    public boolean mustBeOwned()  {
        return (Boolean)invokeOperation("mustBeOwned", new java.lang.Object[] {  });
    }

    public boolean maySpecializeType(core.abstractions.umlsuper.Classifier c)  {
        return (Boolean)invokeOperation("maySpecializeType_c", new java.lang.Object[] { c });
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Constraint> getOwnedRule() {
        java.lang.Object value = get("ownedRule");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Constraint>((cmof.common.ReflectiveCollection)value);
        }
    }

    public String getNamesOfMember()  {
        return (String)invokeOperation("getNamesOfMember", new java.lang.Object[] {  });
    }

    public String getNamesOfMember(core.abstractions.namespaces.NamedElement element)  {
        return (String)invokeOperation("getNamesOfMember_element", new java.lang.Object[] { element });
    }

    public cmof.common.ReflectiveCollection<? extends cmof.NamedElement> getOwnedMember() {
        java.lang.Object value = get("ownedMember");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.NamedElement>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.PackageableElement excludeCollisions(cmof.PackageableElement imps)  {
        return (cmof.PackageableElement)invokeOperation("excludeCollisions_imps", new java.lang.Object[] { imps });
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> getImportedMember() {
        java.lang.Object value = get("importedMember");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.PackageableElement>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.Package getPackage() {
        java.lang.Object value = get("package");
        if (value == null) {
           return null;
        } else {
            return (cmof.Package)value;
        }
    }

    public void setPackage(cmof.Package value) {
        set("package", value);
    }

    public void setPackage(core.basic.Package value) {
        set("package", value);
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

    public boolean isDistinguishableFrom(core.abstractions.namespaces.NamedElement n, core.abstractions.namespaces.Namespace ns)  {
        return (Boolean)invokeOperation("isDistinguishableFrom_n_ns", new java.lang.Object[] { n, ns });
    }

    public String qualifiedNameOperation()  {
        return (String)invokeOperation("qualifiedName", new java.lang.Object[] {  });
    }

    public cmof.common.ReflectiveCollection<? extends cmof.NamedElement> getMember() {
        java.lang.Object value = get("member");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.NamedElement>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.Namespace getNamespace() {
        java.lang.Object value = get("namespace");
        if (value == null) {
           return null;
        } else {
            return (cmof.Namespace)value;
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Property> getAttribute() {
        java.lang.Object value = get("attribute");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Property>((cmof.common.ReflectiveCollection)value);
        }
    }

    public boolean hasVisibilityOf(core.abstractions.namespaces.NamedElement n)  {
        return (Boolean)invokeOperation("hasVisibilityOf_n", new java.lang.Object[] { n });
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

    public core.abstractions.ownerships.Element allOwnedElements()  {
        return (core.abstractions.ownerships.Element)invokeOperation("allOwnedElements", new java.lang.Object[] {  });
    }

    public core.abstractions.namespaces.NamedElement inheritedMemberOperation()  {
        return (core.abstractions.namespaces.NamedElement)invokeOperation("inheritedMember", new java.lang.Object[] {  });
    }

    public core.abstractions.umlsuper.Classifier parents()  {
        return (core.abstractions.umlsuper.Classifier)invokeOperation("parents", new java.lang.Object[] {  });
    }

    public cmof.common.ReflectiveSequence<? extends cmof.Operation> getOwnedOperation() {
        java.lang.Object value = get("ownedOperation");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperListImpl<cmof.Operation>((cmof.common.ReflectiveSequence)value);
        }
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

    public cmof.common.ReflectiveCollection<? extends cmof.ElementImport> getElementImport() {
        java.lang.Object value = get("elementImport");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.ElementImport>((cmof.common.ReflectiveCollection)value);
        }
    }

    public core.abstractions.namespaces.NamedElement inherit(core.abstractions.namespaces.NamedElement inhs)  {
        return (core.abstractions.namespaces.NamedElement)invokeOperation("inherit_inhs", new java.lang.Object[] { inhs });
    }

    public cmof.NamedElement inherit(cmof.NamedElement inhs)  {
        return (cmof.NamedElement)invokeOperation("inherit_inhs", new java.lang.Object[] { inhs });
    }

    public core.abstractions.namespaces.NamedElement inheritableMembers(core.abstractions.umlsuper.Classifier c)  {
        return (core.abstractions.namespaces.NamedElement)invokeOperation("inheritableMembers_c", new java.lang.Object[] { c });
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Comment> getOwnedComment() {
        java.lang.Object value = get("ownedComment");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Comment>((cmof.common.ReflectiveCollection)value);
        }
    }

    public boolean conformsTo(cmof.Classifier other)  {
        return (Boolean)invokeOperation("conformsTo_other", new java.lang.Object[] { other });
    }

    public boolean conformsTo(core.abstractions.typedelements.Type other)  {
        return (Boolean)invokeOperation("conformsTo_other", new java.lang.Object[] { other });
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement> getInheritedMember() {
        java.lang.Object value = get("inheritedMember");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<core.abstractions.namespaces.NamedElement>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Feature> getFeature() {
        java.lang.Object value = get("feature");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Feature>((cmof.common.ReflectiveCollection)value);
        }
    }

    public boolean membersAreDistinguishable()  {
        return (Boolean)invokeOperation("membersAreDistinguishable", new java.lang.Object[] {  });
    }

    public cmof.PackageableElement importedMemberOperation()  {
        return (cmof.PackageableElement)invokeOperation("importedMember", new java.lang.Object[] {  });
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getOwnedElement() {
        java.lang.Object value = get("ownedElement");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageImport> getPackageImport() {
        java.lang.Object value = get("packageImport");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.PackageImport>((cmof.common.ReflectiveCollection)value);
        }
    }

}

