package hub.sam.mof.jocl.teststuff;

import hub.sam.mof.jocl.standardlib.OclCollection;
import hub.sam.mof.jocl.standardlib.OclBoolean;
import hub.sam.mof.jocl.standardlib.OclString;

public class Test {

    public static void main(String[] args) {
        NamespaceValue self = null;
        NamedElementValue n = null;
        NamedElementValue m = null;

        OclBoolean correct = self.ownedElement().forAll(n, self.ownedElement().forAll(m,
                n.name().oclEquals(m.name()).not()));

        // self.ownedElement->forAll(n|self.ownedElement->forAll(m|not n.name = m.name))
        self.ownedElement().forAll(n, self.ownedElement().forAll(m, n.name().oclEquals(m.name()).not()));

        // self.ownedElement->forAll(n,m| not n.name = m.name)
        //self.ownedElement().forAll(new NamedElementValue[] {n, m}, n.name().oclEquals(m.name()).not());

        OclCollection<String> names = self.ownedElement().collect(n, n.name());

        // self.ownedElement->collect(n | n.name)
        self.ownedElement().collect(n, n.name());

        OclString stringVar = null;
        names.forAll(stringVar, stringVar.oclEquals(stringVar));
    }
}
