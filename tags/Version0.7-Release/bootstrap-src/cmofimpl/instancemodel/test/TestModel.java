package cmofimpl.instancemodel.test;

import cmof.Property;
import cmofimpl.bootstrap.*;

public class TestModel {

    public static final B1Class A, B;
    public static final B1Association AB, AC2, AC;
    public static final B1Property a, a2, a3, b, c2, c;
    
    static {
        A = new B1Class("A");
        B = new B1Class("B");
        
        a = new B1Property("a").init(null, false, false, false, 0, -1, null, null);
        a2 = new B1Property("a2").init(null, false, false, false, 0, -1, null, null);
        a3 = new B1Property("a3").init(null, false, false, false, 0, -1, null, null);
        b = new B1Property("b").init(null, false, false, false, 0, -1, null, null);
        c2 = new B1Property("b2").init(null, false, false, false, 0, -1, new Property[] {b}, null);
        c = new B1Property("c").init(null, false, false, false, 0, -1, new Property[] {b}, null);
        A.init(new Property[] {b, c2, c});
        B.init(new Property[] {});
        AB = new cmofimpl.bootstrap.B1Association("AB", a, b);
        AC = new cmofimpl.bootstrap.B1Association("AC", a2, c);
        AC2 = new B1Association("AB2", a3, c2);
        a.setOwningAssociation(AB);
        a.setOposite(b);
        a.setType(A);
        a2.setOwningAssociation(AC);
        a2.setOposite(c);
        a2.setType(A);
        a3.setOwningAssociation(AC2);
        a3.setType(A);
        a3.setOposite(c2);
        b.setUmlClass(A);
        b.setOposite(a);
        b.setType(B);
        c2.setUmlClass(A);
        c2.setOposite(a3);
        c2.setType(B);
        c.setUmlClass(A);
        c.setOposite(a2);
        c.setType(B);
    }
}
