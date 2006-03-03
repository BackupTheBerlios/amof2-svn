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

package core.abstractions.instances;


public class SlotImpl extends hub.sam.mof.reflection.ObjectImpl implements Slot
{
    public SlotImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public SlotImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }

    public core.abstractions.ownerships.Element getOwner() {
        java.lang.Object value = get("owner");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.ownerships.Element)value;
        }
    }

    public core.abstractions.instances.InstanceSpecification getOwningInstance() {
        java.lang.Object value = get("owningInstance");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.instances.InstanceSpecification)value;
        }
    }

    public void setOwningInstance(core.abstractions.instances.InstanceSpecification value) {
        set("owningInstance", value);
    }

    public core.abstractions.structuralfeatures.StructuralFeature getDefiningFeature() {
        java.lang.Object value = get("definingFeature");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.structuralfeatures.StructuralFeature)value;
        }
    }

    public void setDefiningFeature(core.abstractions.structuralfeatures.StructuralFeature value) {
        set("definingFeature", value);
    }

    public core.abstractions.ownerships.Element allOwnedElements()  {
        return (core.abstractions.ownerships.Element)invokeOperation("", new java.lang.Object[] {  });
    }

    public boolean mustBeOwned()  {
        return (Boolean)invokeOperation("", new java.lang.Object[] {  });
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getOwnedElement() {
        java.lang.Object value = get("ownedElement");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<core.abstractions.ownerships.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveSequence<? extends core.abstractions.expressions.ValueSpecification> getValue() {
        java.lang.Object value = get("value");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperListImpl<core.abstractions.expressions.ValueSpecification>((cmof.common.ReflectiveSequence)value);
        }
    }

}

