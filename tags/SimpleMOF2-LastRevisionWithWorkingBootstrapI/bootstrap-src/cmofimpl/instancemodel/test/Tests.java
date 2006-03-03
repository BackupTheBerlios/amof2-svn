package cmofimpl.instancemodel.test;

import junit.framework.TestCase;
import cmofimpl.instancemodel.*;
import hub.sam.mof.instancemodel.InstanceValue;
import cmof.*;

public class Tests extends TestCase {

    private MofClassInstance A1,A2,B1,B2;
    private InstanceValue<UmlClass,Property,java.lang.Object> A1value, A2value, B1value, B2value;
    private MofInstanceModel model = new MofInstanceModel();
    
    public Tests(String name) {
        super(name);
    }
    
    public void setUp() {
        A1 = model.createAInstance(null, TestModel.A);
        A2 = model.createAInstance(null, TestModel.A);
        B1 = model.createAInstance(null, TestModel.B);
        B2 = model.createAInstance(null, TestModel.B);
        A1value = new InstanceValue(A1);
        A2value = new InstanceValue(A2);
        B1value = new InstanceValue(B1);
        B2value = new InstanceValue(B2);
    }
    
    public void testEatInitTime() {
        
    }
    
    public void testCreateLink() {
        MofLink.createLink(TestModel.AB, A1value, B1value);
        MofLink theLink = MofLink.getLink(TestModel.AB, A1value, B1value);
        assertNotNull(theLink);
        assertTrue(A1.get(TestModel.b).getValuesAsList().contains(B1value));        
    }
    
    public void testRemoveLink() {       
        MofLink.createLink(TestModel.AB, A1value, B1value);
        MofLink.removeLink(TestModel.AB, A1value, B1value);
        MofLink theRemovedLink = MofLink.getLink(TestModel.AB, A1value, B1value);
        assertNull(theRemovedLink);
        assertFalse(A1.get(TestModel.b).getValuesAsList().contains(B1value));        
    }
    
    public void testAddLinkProperty() {
        A1.get(TestModel.b).getValuesAsList().add(B1value);
        assertNotNull(MofLink.getLink(TestModel.AB, A1value, B1value));
        assertTrue(A1.get(TestModel.b).getValuesAsList().contains(B1value));
        A1.get(TestModel.b).getValuesAsList().add(B2value);
        assertNotNull(MofLink.getLink(TestModel.AB, A1value, B1value));
        assertTrue(A1.get(TestModel.b).getValuesAsList().contains(B1value));
        assertNotNull(MofLink.getLink(TestModel.AB, A1value, B2value));
        assertTrue(A1.get(TestModel.b).getValuesAsList().contains(B2value));              
    }
    
    public void testRemoveLinkProperty() {
        A1.get(TestModel.b).getValuesAsList().add(B1value);
        A1.get(TestModel.b).getValuesAsList().add(B2value);
        A1.get(TestModel.b).getValuesAsList().remove(B2value);
        assertNull(MofLink.getLink(TestModel.AB, A1value, B2value));        
        assertFalse(A1.get(TestModel.b).getValuesAsList().contains(B2value));
        assertNotNull(MofLink.getLink(TestModel.AB, A1value, B1value));
        assertTrue(A1.get(TestModel.b).getValuesAsList().contains(B1value));
        A1.get(TestModel.b).getValuesAsList().remove(B1value);
        assertNull(MofLink.getLink(TestModel.AB, A1value, B1value));        
        assertFalse(A1.get(TestModel.b).getValuesAsList().contains(B1value));
        assertNull(MofLink.getLink(TestModel.AB, A1value, B2value));        
        assertFalse(A1.get(TestModel.b).getValuesAsList().contains(B2value));
    }
    
    public void testSetLinkProperty() {
        A1.get(TestModel.b).getValuesAsList().add(B1value);
        A1.get(TestModel.b).getValuesAsList().set(0, B2value);
        assertNull(MofLink.getLink(TestModel.AB, A1value, B1value));        
        assertFalse(A1.get(TestModel.b).getValuesAsList().contains(B1value));
        assertNotNull(MofLink.getLink(TestModel.AB, A1value, B2value));
        assertTrue(A1.get(TestModel.b).getValuesAsList().contains(B2value));
        A1.get(TestModel.b).getValuesAsList().remove(B2value);
        assertNull(MofLink.getLink(TestModel.AB, A1value, B2value));        
        assertFalse(A1.get(TestModel.b).getValuesAsList().contains(B2value));        
    }
    
    public void testAddSubsettedProperty() {
        A1.get(TestModel.c).getValuesAsList().add(B1value);
        A1.get(TestModel.c2).getValuesAsList().add(B2value);
        assertTrue(A1.get(TestModel.b).getValuesAsList().contains(B1value));        
        assertTrue(A1.get(TestModel.b).getValuesAsList().contains(B2value));
        assertTrue(A1.get(TestModel.c).getValuesAsList().contains(B1value));
        assertTrue(A1.get(TestModel.c2).getValuesAsList().contains(B2value));
    }
    
    public void testRemoveSubsettedProperty() {
        A1.get(TestModel.c).getValuesAsList().add(B1value);
        A1.get(TestModel.b).getValuesAsList().add(B2value);
        A1.get(TestModel.b).getValuesAsList().remove(B1value);
        assertFalse(A1.get(TestModel.b).getValuesAsList().contains(B1value));
        assertTrue(A1.get(TestModel.b).getValuesAsList().contains(B2value));
        assertFalse(A1.get(TestModel.c).getValuesAsList().contains(B1value));
        assertFalse(A1.get(TestModel.c).getValuesAsList().contains(B2value));
    }
}