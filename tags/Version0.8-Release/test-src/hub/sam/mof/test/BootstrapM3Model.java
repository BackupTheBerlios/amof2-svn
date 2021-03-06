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

package hub.sam.mof.test;

public class BootstrapM3Model extends AbstractRepository {

    public BootstrapM3Model() {
        super("Bootstrap M3 Model tests");
    }
    
    public void testCmofPackage() throws Exception {
        cmof.Package cmofPackage = m3;
        assertNotNull(cmofPackage);
        assertEquals("cmof", cmofPackage.getName());                
        assertTrue(cmofPackage.getOwnedType().containsAll(cmofPackage.getOwnedType()));
        assertTrue(cmofPackage.getMember().containsAll(cmofPackage.getOwnedType()));
        assertEquals(60, cmofPackage.getOwnedType().size());
    }
    
    public void testUmlClass() throws Exception {
        cmof.UmlClass umlClass = (cmof.UmlClass)m3Extent.query("Package:cmof/Class:Class");
        assertNotNull(umlClass);
        assertEquals("Class", umlClass.getName());
        assertEquals(umlClass.getNamespace(), umlClass.getPackage());
        assertTrue(umlClass.getOwnedMember().containsAll(umlClass.getOwnedAttribute()));
        assertEquals(2, umlClass.getSuperClass().size());
        assertEquals(((cmof.reflection.Object)umlClass).getMetaClass(), umlClass);
    }
    
    public void testPrimitiveTypes() throws Exception {
        cmof.Package primitiveTypes = (cmof.Package)m3Extent.query("Package:core/Package:primitivetypes");
        assertNotNull(primitiveTypes);
        assertEquals(4, primitiveTypes.getOwnedType().size());        
    }
}
