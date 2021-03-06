package ocl;


public class NamespaceImpl extends hub.sam.mof.reflection.ObjectImpl implements Namespace
{
    public NamespaceImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public NamespaceImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.ExtentImpl extent, hub.sam.mof.reflection.Identifier metaId, java.lang.String implementationClassName, java.lang.String[] delegateClassNames) {
        super(id, extent, metaId, implementationClassName, delegateClassNames);
    }

    public cmof.common.ReflectiveCollection<? extends ocl.NamedElement> getMember() {
        java.lang.Object value = get("member");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value, this, "member");
        }
    }

    public java.lang.String getQualifiedName() {
        java.lang.Object value = get("qualifiedName");
        if (value == null) {
           return null;
        } else {
            return (java.lang.String)value;
        }
    }

    public void setQualifiedName(java.lang.String value) {
        set("qualifiedName", value);
    }

    public java.lang.String getName() {
        java.lang.Object value = get("name");
        if (value == null) {
           return null;
        } else {
            return (java.lang.String)value;
        }
    }

    public void setName(java.lang.String value) {
        set("name", value);
    }

    public ocl.Namespace getNamespace() {
        java.lang.Object value = get("namespace");
        if (value == null) {
           return null;
        } else {
            return (ocl.Namespace)value;
        }
    }

    public void setNamespace(ocl.Namespace value) {
        set("namespace", value);
    }

    public java.lang.String calculateFullName()  {
        java.lang.Object value = invokeOperation("calculateFullName", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return (java.lang.String)value;
        }
    }

    public java.lang.String calculateFullName(java.lang.String separator)  {
        java.lang.Object value = invokeOperation("calculateFullName_String", new java.lang.Object[] { separator });
        if (value == null) {
           return null;
        } else {
            return (java.lang.String)value;
        }
    }

}

