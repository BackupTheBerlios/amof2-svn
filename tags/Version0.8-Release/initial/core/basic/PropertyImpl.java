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

package core.basic;


public class PropertyImpl extends hub.sam.mof.reflection.ObjectImpl implements Property
{
    public PropertyImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public PropertyImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
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

    public int lowerBound()  {
        return (Integer)invokeOperation("", new java.lang.Object[] {  });
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

    public core.basic.Property getOpposite() {
        java.lang.Object value = get("opposite");
        if (value == null) {
           return null;
        } else {
            return (core.basic.Property)value;
        }
    }

    public void setOpposite(core.basic.Property value) {
        set("opposite", value);
    }

    public long upperBound()  {
        return (Long)invokeOperation("", new java.lang.Object[] {  });
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

    public boolean includesCardinality(int C)  {
        return (Boolean)invokeOperation("_C", new java.lang.Object[] { C });
    }

    public boolean includesMultiplicity(core.abstractions.multiplicities.MultiplicityElement M)  {
        return (Boolean)invokeOperation("_M", new java.lang.Object[] { M });
    }

}

