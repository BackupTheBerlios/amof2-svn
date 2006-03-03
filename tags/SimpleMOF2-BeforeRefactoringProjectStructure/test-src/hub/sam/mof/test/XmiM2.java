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

import cmof.reflection.*;
import cmof.*;

public class XmiM2 extends AbstractRepository {

    public XmiM2() {
        super("XMI M2 tests");
    }
    
    private Extent firstImport = null;
    
    public void testImport() throws Exception {
        firstImport = repository.createExtent("firstImport");
        repository.loadXmiIntoExtent(firstImport, m3, "test/warehouse.xml");
    }
    
    public void testExport() throws Exception {        
        testImport();
        repository.writeExtentToXmi("test/warehouse-out.xml", m3, firstImport);
    }
    
    public void testReImport() throws Exception {
        testImport();
        testExport();
        Extent secondImport = repository.createExtent("secondImport");
        repository.loadXmiIntoExtent(secondImport, m3, "test/warehouse-out.xml");
    }
    
    public void testUnisys() throws Exception {
        firstImport = repository.createExtent("firstImport");
        repository.loadUnisysXmiIntoExtent(firstImport, m3, "test/warehouse-unisys.xml");
        testExport();        
        Extent secondImport = repository.createExtent("secondImport");
        repository.loadXmiIntoExtent(secondImport, m3, "test/warehouse-out.xml");
    }
    
    public void testUnisys2() throws Exception {
        firstImport = repository.createExtent("firstImport");
        repository.loadUnisysXmiIntoExtent(firstImport, m3, "test/CeeJay.xml");
        Property redefined = (Property)firstImport.query("Package:CeeJay/Class:Parameter/Property:signature");
        Property original = (Property)firstImport.query("Package:Common/Association:Signature/Property:signaturedElement");
        assertNotNull(original);
        assertNotNull(redefined);
        assertTrue(redefined.getRedefinedElement().contains(original));       
    }
    
    public void testUnisys3() throws Exception {
        firstImport = repository.createExtent("firstImport");
        repository.loadXmiIntoExtent(firstImport, m3, "models/core.xmi");
        UmlClass namespace = (UmlClass)firstImport.query("Package:core/Package:abstractions/Package:namespaces/Class:Namespace");
        UmlClass namedElement = (UmlClass)firstImport.query("Package:core/Package:abstractions/Package:namespaces/Class:NamedElement");
        UmlClass typedElement = (UmlClass)firstImport.query("Package:core/Package:abstractions/Package:typedelements/Class:TypedElement");
        UmlClass type = (UmlClass)firstImport.query("Package:core/Package:abstractions/Package:typedelements/Class:Type");
        assertNotNull(namespace);
        assertNotNull(namedElement);
        assertNotNull(typedElement);
        assertNotNull(type);
        repository.loadUnisysXmiIntoExtent(firstImport, m3, "test/library-usage-test.xml");
        Property myNamespaceMember = (Property)firstImport.query("Package:test/Class:MyNamespace/Property:ownedMember");
        Property myNamedElementNamespace = (Property)firstImport.query("Package:test/Class:MyNameElement/Property:namespace");
        Property myTypedElementType = (Property)firstImport.query("Package:test/Class:MyTypedElement/Property:type");
        assertNotNull(myNamespaceMember);
        assertNotNull(myNamedElementNamespace);
        assertNotNull(myTypedElementType);
        
        assertEquals(myTypedElementType.getType(), type);
        assertEquals(myNamespaceMember.getRedefinedProperty().iterator().next().getType(), namedElement);
        assertEquals(myNamedElementNamespace.getRedefinedProperty().iterator().next().getType(), namespace);
    }
}
