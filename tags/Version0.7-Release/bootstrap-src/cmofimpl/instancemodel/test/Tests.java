package cmofimpl.instancemodel.test;

import junit.framework.TestCase;
import cmofimpl.instancemodel.*;
import core.abstractions.instances.*;

public class Tests extends TestCase {

    private ClassInstanceImpl A1,A2,B1,B2;
    private InstanceValue A1value, A2value, B1value, B2value;
    
    public Tests(String name) {
        super(name);
    }
    
    public void setUp() {
        A1 = new ClassInstanceImpl(null, TestModel.A);
        A2 = new ClassInstanceImpl(null, TestModel.A);
        B1 = new ClassInstanceImpl(null, TestModel.B);
        B2 = new ClassInstanceImpl(null, TestModel.B);
        A1value = new cmofimpl.instancemodel.InstanceValueImpl(A1);
        A2value = new cmofimpl.instancemodel.InstanceValueImpl(A2);
        B1value = new cmofimpl.instancemodel.InstanceValueImpl(B1);
        B2value = new cmofimpl.instancemodel.InstanceValueImpl(B2);
    }
    
    public void testEatInitTime() {
        
    }
    
    public void testCreateLink() {
        LinkImpl.createLink(TestModel.AB, A1value, B1value);
        Link theLink = LinkImpl.getLink(TestModel.AB, A1value, B1value);
        assertNotNull(theLink);
        assertTrue(A1.getSlot(TestModel.b).getValue().contains(B1value));        
    }
    
    public void testRemoveLink() {       
        LinkImpl.createLink(TestModel.AB, A1value, B1value);
        LinkImpl.removeLink(TestModel.AB, A1value, B1value);
        Link theRemovedLink = LinkImpl.getLink(TestModel.AB, A1value, B1value);
        assertNull(theRemovedLink);
        assertFalse(A1.getSlot(TestModel.b).getValue().contains(B1value));        
    }
    
    public void testAddLinkProperty() {
        A1.getSlot(TestModel.b).getValue().add(B1value);
        assertNotNull(LinkImpl.getLink(TestModel.AB, A1value, B1value));
        assertTrue(A1.getSlot(TestModel.b).getValue().contains(B1value));
        A1.getSlot(TestModel.b).getValue().add(B2value);
        assertNotNull(LinkImpl.getLink(TestModel.AB, A1value, B1value));
        assertTrue(A1.getSlot(TestModel.b).getValue().contains(B1value));
        assertNotNull(LinkImpl.getLink(TestModel.AB, A1value, B2value));
        assertTrue(A1.getSlot(TestModel.b).getValue().contains(B2value));              
    }
    
    public void testRemoveLinkProperty() {
        A1.getSlot(TestModel.b).getValue().add(B1value);
        A1.getSlot(TestModel.b).getValue().add(B2value);
        A1.getSlot(TestModel.b).getValue().remove(B2value);
        assertNull(LinkImpl.getLink(TestModel.AB, A1value, B2value));        
        assertFalse(A1.getSlot(TestModel.b).getValue().contains(B2value));
        assertNotNull(LinkImpl.getLink(TestModel.AB, A1value, B1value));
        assertTrue(A1.getSlot(TestModel.b).getValue().contains(B1value));
        A1.getSlot(TestModel.b).getValue().remove(B1value);
        assertNull(LinkImpl.getLink(TestModel.AB, A1value, B1value));        
        assertFalse(A1.getSlot(TestModel.b).getValue().contains(B1value));
        assertNull(LinkImpl.getLink(TestModel.AB, A1value, B2value));        
        assertFalse(A1.getSlot(TestModel.b).getValue().contains(B2value));
    }
    
    public void testSetLinkProperty() {
        A1.getSlot(TestModel.b).getValue().add(B1value);
        A1.getSlot(TestModel.b).getValue().set(0, B2value);
        assertNull(LinkImpl.getLink(TestModel.AB, A1value, B1value));        
        assertFalse(A1.getSlot(TestModel.b).getValue().contains(B1value));
        assertNotNull(LinkImpl.getLink(TestModel.AB, A1value, B2value));
        assertTrue(A1.getSlot(TestModel.b).getValue().contains(B2value));
        A1.getSlot(TestModel.b).getValue().remove(B2value);
        assertNull(LinkImpl.getLink(TestModel.AB, A1value, B2value));        
        assertFalse(A1.getSlot(TestModel.b).getValue().contains(B2value));        
    }
    
    public void testAddSubsettedProperty() {
        A1.getSlot(TestModel.c).getValue().add(B1value);
        A1.getSlot(TestModel.c2).getValue().add(B2value);
        assertTrue(A1.getSlot(TestModel.b).getValue().contains(B1value));        
        assertTrue(A1.getSlot(TestModel.b).getValue().contains(B2value));
        assertTrue(A1.getSlot(TestModel.c).getValue().contains(B1value));
        assertTrue(A1.getSlot(TestModel.c2).getValue().contains(B2value));
    }
    
    public void testRemoveSubsettedProperty() {
        A1.getSlot(TestModel.c).getValue().add(B1value);
        A1.getSlot(TestModel.b).getValue().add(B2value);
        A1.getSlot(TestModel.b).getValue().remove(B1value);
        assertFalse(A1.getSlot(TestModel.b).getValue().contains(B1value));
        assertTrue(A1.getSlot(TestModel.b).getValue().contains(B2value));
        assertFalse(A1.getSlot(TestModel.c).getValue().contains(B1value));
        assertFalse(A1.getSlot(TestModel.c).getValue().contains(B2value));
    }
}
