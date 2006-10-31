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


public class BehavioralFeatureDlg extends hub.sam.mof.reflection.ObjectImpl implements BehavioralFeature
{
    public BehavioralFeatureDlg(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public BehavioralFeatureDlg(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }

    public String separator()  {
        return (String)invokeOperation("separator", new java.lang.Object[] {  });
    }

    public cmof.PackageableElement importMembers(cmof.PackageableElement imps)  {
        return (cmof.PackageableElement)invokeOperation("importMembers_imps", new java.lang.Object[] { imps });
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

    public cmof.Element getOwner() {
        java.lang.Object value = get("owner");
        if (value == null) {
           return null;
        } else {
            return (cmof.Element)value;
        }
    }

    public boolean isConsistentWith(core.abstractions.redefinitions.RedefinableElement redefinee)  {
        return (Boolean)invokeOperation("isConsistentWith_redefinee", new java.lang.Object[] { redefinee });
    }

    public core.abstractions.ownerships.Element allOwnedElements()  {
        return (core.abstractions.ownerships.Element)invokeOperation("allOwnedElements", new java.lang.Object[] {  });
    }

    public cmof.common.ReflectiveSequence<? extends cmof.Parameter> getReturnResult() {
        java.lang.Object value = get("returnResult");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperListImpl<cmof.Parameter>((cmof.common.ReflectiveSequence)value);
        }
    }

    public boolean isDistinguishableFrom(cmof.NamedElement n, cmof.Namespace ns)  {
        return (Boolean)invokeOperation("isDistinguishableFrom_n_ns", new java.lang.Object[] { n, ns });
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

    public cmof.common.ReflectiveCollection<? extends cmof.ElementImport> getElementImport() {
        java.lang.Object value = get("elementImport");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.ElementImport>((cmof.common.ReflectiveCollection)value);
        }
    }

    public boolean mustBeOwned()  {
        return (Boolean)invokeOperation("mustBeOwned", new java.lang.Object[] {  });
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getFeaturingClassifier() {
        java.lang.Object value = get("featuringClassifier");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Classifier>((cmof.common.ReflectiveCollection)value);
        }
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

    public cmof.common.ReflectiveCollection<? extends cmof.Comment> getOwnedComment() {
        java.lang.Object value = get("ownedComment");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Comment>((cmof.common.ReflectiveCollection)value);
        }
    }

    public boolean isRedefinitionContextValid(core.abstractions.redefinitions.RedefinableElement redefinable)  {
        return (Boolean)invokeOperation("isRedefinitionContextValid_redefinable", new java.lang.Object[] { redefinable });
    }

    public cmof.common.ReflectiveSequence<? extends cmof.Parameter> getParameter() {
        java.lang.Object value = get("parameter");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperListImpl<cmof.Parameter>((cmof.common.ReflectiveSequence)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Type> getRaisedException() {
        java.lang.Object value = get("raisedException");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Type>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveSequence<? extends cmof.Parameter> getFormalParameter() {
        java.lang.Object value = get("formalParameter");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperListImpl<cmof.Parameter>((cmof.common.ReflectiveSequence)value);
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

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getRedefinitionContext() {
        java.lang.Object value = get("redefinitionContext");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Classifier>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.PackageableElement importedMemberOperation()  {
        return (cmof.PackageableElement)invokeOperation("importedMember", new java.lang.Object[] {  });
    }

    public boolean membersAreDistinguishable()  {
        return (Boolean)invokeOperation("membersAreDistinguishable", new java.lang.Object[] {  });
    }

    public cmof.common.ReflectiveCollection<? extends cmof.RedefinableElement> getRedefinedElement() {
        java.lang.Object value = get("redefinedElement");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.RedefinableElement>((cmof.common.ReflectiveCollection)value);
        }
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

}

