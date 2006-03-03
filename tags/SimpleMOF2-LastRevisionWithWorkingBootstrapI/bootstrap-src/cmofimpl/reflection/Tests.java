package cmofimpl.reflection;

import cmof.*;
import cmof.reflection.Object;
import core.abstractions.elements.Element;

public class Tests extends junit.framework.TestCase {

    private ExtentImpl extent = null;
    private UmlClass property = null;
    private UmlClass umlClass = null;
    
    public Tests(String name) {
        super(name);
    }
    
    public void setUp() {
        extent = new ExtentImpl("test", true);
        for (Element e: cmofimpl.bootstrap.B1M2Model.bootstrapModel().objectsOfType(null, true)) {
            if (e instanceof UmlClass) {
                UmlClass umlClass = (UmlClass)e;
                if (umlClass.getName().equals("Property")) {
                    property = umlClass;
                } else if (umlClass.getName().equals("Class")) {
                    this.umlClass = umlClass;
                }
            }
        }
    }
   
    public void testCreateAndDeleteObject() {
        ObjectImpl testObject = (ObjectImpl)extent.getFactory().create(property);
        assertTrue(extent.objectsOfType(null, true).size() == 1);
        testObject.delete();
        assertTrue(extent.objectsOfType(null, true).size() == 0);
    }
    
    public void testSetPrimitiveProperty() {
        ObjectImpl testObject = (ObjectImpl)extent.getFactory().create(property);
        testObject.set("name", "TestName");
        assertEquals("TestName", testObject.get("name"));
    }
    
    public void testSetMulOneProperty() {
        ObjectImpl testObject = (ObjectImpl)extent.getFactory().create(property);
        ObjectImpl testValue = (ObjectImpl)extent.getFactory().create(umlClass);
        testValue.set("name", "TestValue");
        testObject.set("name", "TestName");
        testObject.set("type", testValue);
        assertEquals(testValue, testObject.get("type"));
        assertEquals("TestValue", ((ObjectImpl)testObject.get("type")).get("name"));
    }
    
    public void testSetMulProperty() {
        ObjectImpl testObject = (ObjectImpl)extent.getFactory().create(umlClass);
        ObjectImpl testValue1 = (ObjectImpl)extent.getFactory().create(property);
        ObjectImpl testValue2 = (ObjectImpl)extent.getFactory().create(property);
        
        testValue1.set("name", "TestValue1");
        testValue2.set("name", "TestValue2");
        testObject.set("name", "TestObject");
        ((cmof.common.ReflectiveCollection)testObject.get("member")).add(testValue1);
        ((cmof.common.ReflectiveCollection)testObject.get("member")).add(testValue2);
        assertTrue(((cmof.common.ReflectiveCollection<? extends Object>)testObject.get("member")).size() == 2);
        assertEquals(testValue1, ((cmof.common.ReflectiveSequence<? extends Object>)testObject.get("member")).get(0));
        assertEquals(testValue2, ((cmof.common.ReflectiveSequence<? extends Object>)testObject.get("member")).get(1));        
    }
    
    public void testDeleteOfUsedValue1() {
        ObjectImpl testObject = (ObjectImpl)extent.getFactory().create(property);
        ObjectImpl testValue = (ObjectImpl)extent.getFactory().create(umlClass);
        testValue.set("name", "TestValue");
        testObject.set("name", "TestName");
        testObject.set("type", testValue);
        assertEquals(testValue, testObject.get("type"));
        assertEquals("TestValue", ((ObjectImpl)testObject.get("type")).get("name"));
        testValue.delete();
        assertNull(testObject.get("type"));
    }
    
    public void testDeleteOfUsedValue2() {
        ObjectImpl testObject = (ObjectImpl)extent.getFactory().create(umlClass);
        ObjectImpl testValue1 = (ObjectImpl)extent.getFactory().create(property);
        ObjectImpl testValue2 = (ObjectImpl)extent.getFactory().create(property);
        
        testValue1.set("name", "TestValue1");
        testValue2.set("name", "TestValue2");
        testObject.set("name", "TestObject");
        ((cmof.common.ReflectiveCollection)testObject.get("member")).add(testValue1);
        ((cmof.common.ReflectiveCollection)testObject.get("member")).add(testValue2);
        assertTrue(((cmof.common.ReflectiveCollection<? extends Object>)testObject.get("member")).size() == 2);
        assertEquals(testValue1, ((cmof.common.ReflectiveSequence<? extends Object>)testObject.get("member")).get(0));
        assertEquals(testValue2, ((cmof.common.ReflectiveSequence<? extends Object>)testObject.get("member")).get(1));
        testValue1.delete();
        assertTrue(((cmof.common.ReflectiveCollection<? extends Object>)testObject.get("member")).size() == 1);
        assertEquals(testValue2, ((cmof.common.ReflectiveSequence<? extends Object>)testObject.get("member")).get(0));
        testValue2.delete();
        assertTrue(((cmof.common.ReflectiveCollection<? extends Object>)testObject.get("member")).size() == 0);
    }

    public void testDefaultValues() {
        ObjectImpl testObject = (ObjectImpl)extent.getFactory().create(property);
        assertEquals(testObject.get("isUnique"), Boolean.TRUE);
        assertEquals(testObject.get("isOrdered"), Boolean.FALSE);
        assertEquals(testObject.get("isReadOnly"), Boolean.FALSE);
        assertEquals(testObject.get("upper"), new Long(1));
        assertEquals(testObject.get("lower"), new Integer(1));
    }
}
