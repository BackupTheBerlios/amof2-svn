package core.basic;


public class OperationImpl extends hub.sam.mof.reflection.ObjectImpl implements Operation
{
    public OperationImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public OperationImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }
    protected Operation self = null;
    protected void setSelf(hub.sam.mof.reflection.ObjectImpl self) {
        this.self = (Operation)self;
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

    public cmof.common.ReflectiveSequence<? extends core.basic.Parameter> getOwnedParameter() {
        java.lang.Object value = get("ownedParameter");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperListImpl<core.basic.Parameter>((cmof.common.ReflectiveSequence)value);
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

    public core.basic.UmlClass getUmlClass() {
        java.lang.Object value = get("umlClass");
        if (value == null) {
           return null;
        } else {
            return (core.basic.UmlClass)value;
        }
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

    public long upperBound()  {
        java.lang.Object value = invokeOperation("upperBound", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Long)value;
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

    public cmof.common.ReflectiveCollection<? extends core.basic.Type> getRaisedException() {
        java.lang.Object value = get("raisedException");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<core.basic.Type>((cmof.common.ReflectiveCollection)value);
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

    public boolean isMultivalued()  {
        java.lang.Object value = invokeOperation("isMultivalued", new java.lang.Object[] {  });
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

}

