package cmofimpl.codegeneration;

import junit.framework.TestCase;
import java.io.*;
import core.abstractions.elements.Element;
import cmof.UmlClass;

public class Tests extends TestCase {
    private StreamFactory streamFactory = new StreamFactory("./testfiles");
    
    public Tests(String name) {
        super(name);
    }

    public void setUp() throws IOException {
        
    }
    
    public void testWriteObjectProxyInterface() throws Throwable {
        for (Element element: cmofimpl.bootstrap.B1Model.bootstrapModel().objectsOfType(null, true)) {
            if (element instanceof UmlClass) {
                new ObjectProxyInterfaceGenerator(streamFactory).generate(new java.util.Vector<String>(), (UmlClass)element);
            }
        }                        
    }
        
    public void testWriteObjectProxyImplementation() throws Throwable {
        for (Element element: cmofimpl.bootstrap.B1Model.bootstrapModel().objectsOfType(null, true)) {
            if (element instanceof UmlClass) {
                new ObjectProxyImplementationGenerator(streamFactory, "Impl").generate(new java.util.Vector<String>(), (UmlClass)element);
            }
        }      
    }
        
}
