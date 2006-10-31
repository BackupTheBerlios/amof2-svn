package cmofimpl.xmi;

import java.io.*;
import cmofimpl.reflection.*;

public class Tests extends junit.framework.TestCase {
    
    private InputStream in;
    private static final String b1mofFile = "testfiles/b1mof.xml";
    private XmiReader reader;
    private cmofimpl.reflection.ExtentImpl extent;
    
    public Tests(String name) {
        super(name);
    }

    public void setUp() {
        try {
            in = new FileInputStream(b1mofFile);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        extent = new cmofimpl.reflection.ExtentImpl("test", true);
        reader = new XmiReader(extent, cmofimpl.bootstrap.B1Model.bootstrapModel());
    }
    
    public void testReadB1Mof() throws Exception {        
        reader.readXmi(in);
        int count = 0;
        for (core.abstractions.elements.Element element: extent.objectsOfType(null, true)) {
            if (element instanceof ObjectImpl) {
                count++;
                ObjectImpl object = (ObjectImpl)element;
                assertNotNull(object.get("name"));
                assertTrue( (object.get("name").equals("B1Class")) || 
                        (object.get("name").equals("MEMBER")));
                if (object.get("name").equals("B1Class")) {
                    count++;
                    assertTrue(object.get("member") instanceof cmof.common.ReflectiveCollection);
                    assertTrue(((cmof.common.ReflectiveCollection<? extends Object>)object.get("member")).size() == 1);
                    assertEquals(((ObjectImpl)((cmof.common.ReflectiveCollection<? extends Object>)object.get("member")).iterator().next()).get("type"), object);
                    assertEquals(((ObjectImpl)((cmof.common.ReflectiveCollection<? extends Object>)object.get("member")).iterator().next()).get("name"), "MEMBER");
                }                    
            }
        }
        assertEquals(count, 3);
    }
}
