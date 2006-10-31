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

package core.abstractions.literals;


public class LiteralSpecificationDlg extends hub.sam.mof.reflection.ObjectImpl implements LiteralSpecification
{
    public LiteralSpecificationDlg(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public LiteralSpecificationDlg(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }

    public int integerValue()  {
        return (Integer)invokeOperation("integerValue", new java.lang.Object[] {  });
    }

    public String stringValue()  {
        return (String)invokeOperation("stringValue", new java.lang.Object[] {  });
    }

    public boolean isNull()  {
        return (Boolean)invokeOperation("isNull", new java.lang.Object[] {  });
    }

    public long unlimitedValue()  {
        return (Long)invokeOperation("unlimitedValue", new java.lang.Object[] {  });
    }

    public boolean mustBeOwned()  {
        return (Boolean)invokeOperation("mustBeOwned", new java.lang.Object[] {  });
    }

    public boolean booleanValue()  {
        return (Boolean)invokeOperation("booleanValue", new java.lang.Object[] {  });
    }

    public core.abstractions.ownerships.Element allOwnedElements()  {
        return (core.abstractions.ownerships.Element)invokeOperation("allOwnedElements", new java.lang.Object[] {  });
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getOwnedElement() {
        java.lang.Object value = get("ownedElement");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<core.abstractions.ownerships.Element>((cmof.common.ReflectiveCollection)value);
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

    public boolean isComputable()  {
        return (Boolean)invokeOperation("isComputable", new java.lang.Object[] {  });
    }

}

