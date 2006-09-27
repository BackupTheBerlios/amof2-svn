package hub.sam.mof.jocl.teststuff;

import cmof.NamedElement;
import cmof.Namespace;
import hub.sam.mof.jocl.standardlib.OclCollection;
import hub.sam.mof.jocl.standardlib.OclModelElement;


public class NamespaceValue extends OclModelElement<Namespace> /* implements Namespace*/ {
    protected NamespaceValue(Namespace value) {
        super(value);
    }

    public OclCollection<NamedElement> ownedElement() {
        return null;
    }
}
