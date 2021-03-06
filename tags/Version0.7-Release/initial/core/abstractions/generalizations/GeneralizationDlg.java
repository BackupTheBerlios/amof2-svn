package core.abstractions.generalizations;


public class GeneralizationDlg extends cmofimpl.reflection.ObjectImpl implements Generalization
{
    public GeneralizationDlg(cmofimpl.instancemodel.ClassInstanceImpl instance, cmofimpl.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public GeneralizationDlg(cmofimpl.reflection.Identifier id, cmofimpl.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getTarget() {
        java.lang.Object value = get("target");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<core.abstractions.ownerships.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

    public core.abstractions.generalizations.Classifier getGeneral() {
        java.lang.Object value = get("general");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.generalizations.Classifier)value;
        }
    }

    public void setGeneral(core.abstractions.generalizations.Classifier value) {
        set("general", value);
    }

    public core.abstractions.generalizations.Classifier getSpecific() {
        java.lang.Object value = get("specific");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.generalizations.Classifier)value;
        }
    }

    public void setSpecific(core.abstractions.generalizations.Classifier value) {
        set("specific", value);
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getRelatedElement() {
        java.lang.Object value = get("relatedElement");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<core.abstractions.ownerships.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getSource() {
        java.lang.Object value = get("source");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<core.abstractions.ownerships.Element>((cmof.common.ReflectiveCollection)value);
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

    public core.abstractions.ownerships.Element getOwner() {
        java.lang.Object value = get("owner");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.ownerships.Element)value;
        }
    }

}

