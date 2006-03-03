package core.abstractions.instances;


public class InstanceValueDlg extends hub.sam.mof.reflection.ObjectImpl implements InstanceValue
{
    public InstanceValueDlg(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public InstanceValueDlg(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }
    protected InstanceValue self = null;
    protected void setSelf(hub.sam.mof.reflection.ObjectImpl self) {
        this.self = (InstanceValue)self;
    }

    public core.abstractions.instances.InstanceSpecification getInstance() {
        java.lang.Object value = get("instance");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.instances.InstanceSpecification)value;
        }
    }

    public void setInstance(core.abstractions.instances.InstanceSpecification value) {
        set("instance", value);
    }

    public boolean booleanValue()  {
        java.lang.Object value = invokeOperation("booleanValue", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
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

    public long unlimitedValue()  {
        java.lang.Object value = invokeOperation("unlimitedValue", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Long)value;
        }
    }

    public boolean isNull()  {
        java.lang.Object value = invokeOperation("isNull", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getOwnedElement() {
        java.lang.Object value = get("ownedElement");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<core.abstractions.ownerships.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

    public boolean isComputable()  {
        java.lang.Object value = invokeOperation("isComputable", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public String stringValue()  {
        java.lang.Object value = invokeOperation("stringValue", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return (String)value;
        }
    }

    public boolean mustBeOwned()  {
        java.lang.Object value = invokeOperation("mustBeOwned", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
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

    public int integerValue()  {
        java.lang.Object value = invokeOperation("integerValue", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Integer)value;
        }
    }

}

