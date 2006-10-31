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


public class UmlClassImpl extends hub.sam.mof.reflection.ObjectImpl implements UmlClass
{
    public UmlClassImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public UmlClassImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
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

    public cmof.common.ReflectiveCollection<? extends core.basic.UmlClass> getSuperClass() {
        java.lang.Object value = get("superClass");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<core.basic.UmlClass>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveSequence<? extends core.basic.Operation> getOwnedOperation() {
        java.lang.Object value = get("ownedOperation");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperListImpl<core.basic.Operation>((cmof.common.ReflectiveSequence)value);
        }
    }

    public core.basic.Package getPackage() {
        java.lang.Object value = get("package");
        if (value == null) {
           return null;
        } else {
            return (core.basic.Package)value;
        }
    }

    public void setPackage(core.basic.Package value) {
        set("package", value);
    }

    public cmof.common.ReflectiveSequence<? extends core.basic.Property> getOwnedAttribute() {
        java.lang.Object value = get("ownedAttribute");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperListImpl<core.basic.Property>((cmof.common.ReflectiveSequence)value);
        }
    }

}

