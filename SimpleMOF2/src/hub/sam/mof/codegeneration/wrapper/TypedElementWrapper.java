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

package hub.sam.mof.codegeneration.wrapper;

import cmof.PrimitiveType;
import cmof.Type;

public abstract class TypedElementWrapper extends AbstractWrapper {
	  public abstract String getName();
      public abstract Type getUmlType();
      public String getUpperName() {
          String name = getName();
          return name.substring(0,1).toUpperCase() + name.substring(1, name.length());
      }
      public String getPlainJavaType() {
          String typeName = null;
          Type type = getUmlType();
          if (type == null) {
              return "void";
          }
          if (type instanceof PrimitiveType) {
              if (type.getName().equals(core.primitivetypes.String.class.getSimpleName())) {
                  typeName = String.class.getCanonicalName();
              } else if (type.getName().equals(core.primitivetypes.Object.class.getSimpleName())) {
                  typeName = Object.class.getCanonicalName();
              } else if (type.getName().equals(core.primitivetypes.Integer.class.getSimpleName())) {
                  typeName = "int";
              } else if (type.getName().equals(core.primitivetypes.Boolean.class.getSimpleName())) {
                  typeName = "boolean";
              } else if (type.getName().equals(core.primitivetypes.UnlimitedNatural.class.getSimpleName())) {
                  typeName = "long";
              } else {
                  throw new RuntimeException("assert");
              }
          } else {
              typeName = getFullQualifiedJavaIdentifier(type);
          }
          return typeName;
      }
      public String getJavaObjectType() {
          String typeName = null;
          Type type = getUmlType();
          if (type instanceof PrimitiveType) {
              if (type.getName().equals(core.primitivetypes.String.class.getSimpleName())) {
                  typeName = java.lang.String.class.getCanonicalName();
              } else if (type.getName().equals(core.primitivetypes.Object.class.getSimpleName())) {
                  typeName = java.lang.Object.class.getCanonicalName();
              } else if (type.getName().equals(core.primitivetypes.Integer.class.getSimpleName())) {
                  typeName = Integer.class.getCanonicalName();
              } else if (type.getName().equals(core.primitivetypes.Boolean.class.getSimpleName())) {
                  typeName = Boolean.class.getCanonicalName();
              } else if (type.getName().equals(core.primitivetypes.UnlimitedNatural.class.getSimpleName())) {
                  typeName = Long.class.getCanonicalName();
              } else {
                  throw new RuntimeException("assert");
              }
          } else {
              typeName = getFullQualifiedJavaIdentifier(type);
          }
          return typeName;
      }
}
