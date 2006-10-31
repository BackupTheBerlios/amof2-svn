package cmof;


public class OperationDlg extends hub.sam.mof.reflection.ObjectImpl implements Operation
{
    public OperationDlg(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public OperationDlg(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }
    protected Operation self = null;
    protected void setSelf(hub.sam.mof.reflection.ObjectImpl self) {
        this.self = (Operation)self;
    }

    public boolean isQuery() {
        java.lang.Object value = get("isQuery");
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public void setIsQuery(boolean value) {
        set("isQuery", value);
    }

    public cmof.common.ReflectiveSequence<? extends cmof.Parameter> getFormalParameter() {
        java.lang.Object value = get("formalParameter");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperListImpl<cmof.Parameter>((cmof.common.ReflectiveSequence)value);
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

    public boolean isRedefinitionContextValid(core.abstractions.redefinitions.RedefinableElement redefinable)  {
        java.lang.Object value = invokeOperation("isRedefinitionContextValid_redefinable", new java.lang.Object[] { redefinable });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
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

    public cmof.common.ReflectiveCollection<? extends cmof.Comment> getOwnedComment() {
        java.lang.Object value = get("ownedComment");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Comment>((cmof.common.ReflectiveCollection)value);
        }
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

    public String getQualifiedName() {
        java.lang.Object value = get("qualifiedName");
        if (value == null) {
           return null;
        } else {
            return (String)value;
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

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getRedefinitionContext() {
        java.lang.Object value = get("redefinitionContext");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Classifier>((cmof.common.ReflectiveCollection)value);
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

    public cmof.Classifier typeOperation()  {
        java.lang.Object value = invokeOperation("type", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return (cmof.Classifier)value;
        }
    }

    public boolean includesMultiplicity(core.abstractions.multiplicities.MultiplicityElement M)  {
        java.lang.Object value = invokeOperation("includesMultiplicity_M", new java.lang.Object[] { M });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.NamedElement> getMember() {
        java.lang.Object value = get("member");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.NamedElement>((cmof.common.ReflectiveCollection)value);
        }
    }

    public boolean membersAreDistinguishable()  {
        java.lang.Object value = invokeOperation("membersAreDistinguishable", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> getImportedMember() {
        java.lang.Object value = get("importedMember");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.PackageableElement>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveSequence<? extends core.basic.Parameter> getOwnedParameter() {
        java.lang.Object value = get("ownedParameter");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperListImpl<core.basic.Parameter>((cmof.common.ReflectiveSequence)value);
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

    public cmof.common.ReflectiveCollection<? extends cmof.Operation> getRedefinedOperation() {
        java.lang.Object value = get("redefinedOperation");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Operation>((cmof.common.ReflectiveCollection)value);
        }
    }

    public boolean isConsistentWith(cmof.RedefinableElement redefinee)  {
        java.lang.Object value = invokeOperation("isConsistentWith_redefinee", new java.lang.Object[] { redefinee });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public boolean isConsistentWith(core.abstractions.redefinitions.RedefinableElement redefinee)  {
        java.lang.Object value = invokeOperation("isConsistentWith_redefinee", new java.lang.Object[] { redefinee });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
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

    public String qualifiedNameOperation()  {
        java.lang.Object value = invokeOperation("qualifiedName", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return (String)value;
        }
    }

    public cmof.common.ReflectiveSequence<? extends core.abstractions.namespaces.Namespace> allNamespaces()  {
        java.lang.Object value = invokeOperation("allNamespaces", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperListImpl<core.abstractions.namespaces.Namespace>((cmof.common.ReflectiveSequence)value);
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

    public cmof.common.ReflectiveCollection<? extends cmof.Constraint> getPrecondition() {
        java.lang.Object value = get("precondition");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Constraint>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> importedMemberOperation()  {
        java.lang.Object value = invokeOperation("importedMember", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.PackageableElement>((cmof.common.ReflectiveCollection)value);
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

    public cmof.common.ReflectiveCollection<? extends cmof.Constraint> getPostcondition() {
        java.lang.Object value = get("postcondition");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Constraint>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveSequence<? extends cmof.Parameter> getReturnResult() {
        java.lang.Object value = get("returnResult");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperListImpl<cmof.Parameter>((cmof.common.ReflectiveSequence)value);
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

    public boolean includesCardinality(int C)  {
        java.lang.Object value = invokeOperation("includesCardinality_C", new java.lang.Object[] { C });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public long upperBound()  {
        java.lang.Object value = invokeOperation("upperBound", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Long)value;
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getFeaturingClassifier() {
        java.lang.Object value = get("featuringClassifier");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Classifier>((cmof.common.ReflectiveCollection)value);
        }
    }

    public String separator()  {
        java.lang.Object value = invokeOperation("separator", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return (String)value;
        }
    }

    public boolean isDistinguishableFrom(core.abstractions.namespaces.NamedElement n, core.abstractions.namespaces.Namespace ns)  {
        java.lang.Object value = invokeOperation("isDistinguishableFrom_n_ns", new java.lang.Object[] { n, ns });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public boolean isDistinguishableFrom(cmof.NamedElement n, cmof.Namespace ns)  {
        java.lang.Object value = invokeOperation("isDistinguishableFrom_n_ns", new java.lang.Object[] { n, ns });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public cmof.Constraint getBodyCondition() {
        java.lang.Object value = get("bodyCondition");
        if (value == null) {
           return null;
        } else {
            return (cmof.Constraint)value;
        }
    }

    public void setBodyCondition(cmof.Constraint value) {
        set("bodyCondition", value);
    }

    public cmof.Element getOwner() {
        java.lang.Object value = get("owner");
        if (value == null) {
           return null;
        } else {
            return (cmof.Element)value;
        }
    }

    public boolean isMultivalued()  {
        java.lang.Object value = invokeOperation("isMultivalued", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
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

    public int lowerOperation()  {
        java.lang.Object value = invokeOperation("lower", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Integer)value;
        }
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

    public boolean mustBeOwned()  {
        java.lang.Object value = invokeOperation("mustBeOwned", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.RedefinableElement> getRedefinedElement() {
        java.lang.Object value = get("redefinedElement");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.RedefinableElement>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> allOwnedElements()  {
        java.lang.Object value = invokeOperation("allOwnedElements", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<core.abstractions.ownerships.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

    public boolean isUniqueOperation()  {
        java.lang.Object value = invokeOperation("isUnique", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public long upperOperation()  {
        java.lang.Object value = invokeOperation("upper", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Long)value;
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

    public boolean isOrderedOperation()  {
        java.lang.Object value = invokeOperation("isOrdered", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public int lowerBound()  {
        java.lang.Object value = invokeOperation("lowerBound", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Integer)value;
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> excludeCollisions(cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> imps)  {
        java.lang.Object value = invokeOperation("excludeCollisions_imps", new java.lang.Object[] { imps });
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.PackageableElement>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.NamedElement> getOwnedMember() {
        java.lang.Object value = get("ownedMember");
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

    public cmof.common.ReflectiveCollection<String> getNamesOfMember(core.abstractions.namespaces.NamedElement element)  {
        java.lang.Object value = invokeOperation("getNamesOfMember_element", new java.lang.Object[] { element });
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<String>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<String> getNamesOfMember()  {
        java.lang.Object value = invokeOperation("getNamesOfMember", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<String>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> importMembers(cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> imps)  {
        java.lang.Object value = invokeOperation("importMembers_imps", new java.lang.Object[] { imps });
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.PackageableElement>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveSequence<? extends cmof.Parameter> getParameter() {
        java.lang.Object value = get("parameter");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperListImpl<cmof.Parameter>((cmof.common.ReflectiveSequence)value);
        }
    }

}

