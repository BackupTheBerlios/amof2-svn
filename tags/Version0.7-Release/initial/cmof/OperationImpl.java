package cmof;


public class OperationImpl extends cmofimpl.reflection.ObjectImpl implements Operation
{
    public OperationImpl(cmofimpl.instancemodel.ClassInstanceImpl instance, cmofimpl.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public OperationImpl(cmofimpl.reflection.Identifier id, cmofimpl.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
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

    public cmof.common.ReflectiveCollection<? extends cmof.Operation> getRedefinedOperation() {
        java.lang.Object value = get("redefinedOperation");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.Operation>((cmof.common.ReflectiveCollection)value);
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

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> getImportedMember() {
        java.lang.Object value = get("importedMember");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.PackageableElement>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Type> getRaisedException() {
        java.lang.Object value = get("raisedException");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.Type>((cmof.common.ReflectiveCollection)value);
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

    public cmof.Element getOwner() {
        java.lang.Object value = get("owner");
        if (value == null) {
           return null;
        } else {
            return (cmof.Element)value;
        }
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

    public cmof.common.ReflectiveCollection<? extends cmof.RedefinableElement> getRedefinedElement() {
        java.lang.Object value = get("redefinedElement");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.RedefinableElement>((cmof.common.ReflectiveCollection)value);
        }
    }

    public String getQualifiedName() {
        java.lang.Object value = get("qualifiedName");
        if (value == null) {
           return null;
        } else {
            return (String)value;
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Constraint> getPostcondition() {
        java.lang.Object value = get("postcondition");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.Constraint>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveSequence<? extends cmof.Parameter> getParameter() {
        java.lang.Object value = get("parameter");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperListImpl<cmof.Parameter>((cmof.common.ReflectiveSequence)value);
        }
    }

    public cmof.common.ReflectiveSequence<? extends cmof.Parameter> getReturnResult() {
        java.lang.Object value = get("returnResult");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperListImpl<cmof.Parameter>((cmof.common.ReflectiveSequence)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageImport> getPackageImport() {
        java.lang.Object value = get("packageImport");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.PackageImport>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Constraint> getOwnedRule() {
        java.lang.Object value = get("ownedRule");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.Constraint>((cmof.common.ReflectiveCollection)value);
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

    public cmof.common.ReflectiveCollection<? extends cmof.Constraint> getPrecondition() {
        java.lang.Object value = get("precondition");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.Constraint>((cmof.common.ReflectiveCollection)value);
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

    public cmof.common.ReflectiveCollection<? extends cmof.ElementImport> getElementImport() {
        java.lang.Object value = get("elementImport");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.ElementImport>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getOwnedElement() {
        java.lang.Object value = get("ownedElement");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveSequence<? extends cmof.Parameter> getFormalParameter() {
        java.lang.Object value = get("formalParameter");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperListImpl<cmof.Parameter>((cmof.common.ReflectiveSequence)value);
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

    public cmof.common.ReflectiveCollection<? extends cmof.NamedElement> getOwnedMember() {
        java.lang.Object value = get("ownedMember");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.NamedElement>((cmof.common.ReflectiveCollection)value);
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

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getFeaturingClassifier() {
        java.lang.Object value = get("featuringClassifier");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.Classifier>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveSequence<? extends core.basic.Parameter> getOwnedParameter() {
        java.lang.Object value = get("ownedParameter");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperListImpl<core.basic.Parameter>((cmof.common.ReflectiveSequence)value);
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

    public cmof.common.ReflectiveCollection<? extends cmof.Comment> getOwnedComment() {
        java.lang.Object value = get("ownedComment");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.Comment>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.NamedElement> getMember() {
        java.lang.Object value = get("member");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.NamedElement>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getRedefinitionContext() {
        java.lang.Object value = get("redefinitionContext");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.Classifier>((cmof.common.ReflectiveCollection)value);
        }
    }

}

