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

package core.abstractions.expressions;


public class OpaqueExpressionImpl extends hub.sam.mof.reflection.ObjectImpl implements OpaqueExpression
{
    public OpaqueExpressionImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public OpaqueExpressionImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
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

    public core.abstractions.ownerships.Element allOwnedElements()  {
        return (core.abstractions.ownerships.Element)invokeOperation("", new java.lang.Object[] {  });
    }

    public String stringValue()  {
        return (String)invokeOperation("", new java.lang.Object[] {  });
    }

    public int integerValue()  {
        return (Integer)invokeOperation("", new java.lang.Object[] {  });
    }

    public long unlimitedValue()  {
        return (Long)invokeOperation("", new java.lang.Object[] {  });
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getOwnedElement() {
        java.lang.Object value = get("ownedElement");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<core.abstractions.ownerships.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

    public String getLanguage() {
        java.lang.Object value = get("language");
        if (value == null) {
           return null;
        } else {
            return (String)value;
        }
    }

    public void setLanguage(String value) {
        set("language", value);
    }

    public boolean booleanValue()  {
        return (Boolean)invokeOperation("", new java.lang.Object[] {  });
    }

    public boolean isNull()  {
        return (Boolean)invokeOperation("", new java.lang.Object[] {  });
    }

    public String getBody() {
        java.lang.Object value = get("body");
        if (value == null) {
           return null;
        } else {
            return (String)value;
        }
    }

    public void setBody(String value) {
        set("body", value);
    }

    public boolean mustBeOwned()  {
        return (Boolean)invokeOperation("", new java.lang.Object[] {  });
    }

    public boolean isComputable()  {
        return (Boolean)invokeOperation("", new java.lang.Object[] {  });
    }

}

