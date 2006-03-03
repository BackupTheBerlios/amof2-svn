package cmofimpl.bootstrap;

import java.util.*;

public class B1Model {

    private static Map<String, UmlClassImpl> bootstrapXmiNames;
    
    private static cmofimpl.reflection.ExtentImpl b1Extent;
    
    static {
        b1Extent = new cmofimpl.reflection.ExtentImpl("b1Extent", true);

        cmof.PrimitiveType string = new B1PrimitiveType("String");
        B1Class umlClass = new B1Class("B1Class");
        B1Class property = new B1Class("B1Property");
        
        b1Extent.addObject(umlClass);
        b1Extent.addObject(property);     
        
        umlClass.init(new cmof.Property[]{
            new B1Property("name").init(string, false, false, false, 0, 1, null, null),
            new B1Property("member").init(property, false, false, false, 0, -1, null, null)
        });
        
        property.init(new cmof.Property[]{
            new B1Property("name").init(string, false, false, false, 0, 1, null, null),
            new B1Property("type").init(umlClass, false, false, false, 0, 1, null, null)
        });               
    }
    
    public static cmof.reflection.Extent bootstrapModel() {
        return b1Extent;
    }

}
