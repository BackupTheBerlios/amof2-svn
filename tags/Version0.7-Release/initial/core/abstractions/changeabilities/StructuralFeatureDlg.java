package core.abstractions.changeabilities;


public class StructuralFeatureDlg extends cmofimpl.reflection.ObjectImpl implements StructuralFeature
{
    public StructuralFeatureDlg(cmofimpl.instancemodel.ClassInstanceImpl instance, cmofimpl.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public StructuralFeatureDlg(cmofimpl.reflection.Identifier id, cmofimpl.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }

    public String getQualifiedName() {
        java.lang.Object value = get("qualifiedName");
        if (value == null) {
           return null;
        } else {
            return (String)value;
        }
    }

    public core.abstractions.namespaces.Namespace getNamespace() {
        java.lang.Object value = get("namespace");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.namespaces.Namespace)value;
        }
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.classifiers.Classifier> getFeaturingClassifier() {
        java.lang.Object value = get("featuringClassifier");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<core.abstractions.classifiers.Classifier>((cmof.common.ReflectiveCollection)value);
        }
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

    public core.abstractions.ownerships.Element getOwner() {
        java.lang.Object value = get("owner");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.ownerships.Element)value;
        }
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getOwnedElement() {
        java.lang.Object value = get("ownedElement");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<core.abstractions.ownerships.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

    public core.abstractions.typedelements.Type getType() {
        java.lang.Object value = get("type");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.typedelements.Type)value;
        }
    }
}

