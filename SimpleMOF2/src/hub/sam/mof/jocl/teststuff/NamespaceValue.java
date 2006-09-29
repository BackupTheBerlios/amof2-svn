package hub.sam.mof.jocl.teststuff;

import cmof.Namespace;
import cmof.NamedElement;
import hub.sam.mof.jocl.standardlib.OclCollection;
import hub.sam.mof.jocl.standardlib.OclModelElement;


public class NamespaceValue extends OclModelElement<Namespace> /* implements Namespace*/ {
    protected NamespaceValue(Namespace value) {
        super(value);
    }

    public OclCollection<NamedElementValue,NamedElement> ownedElement() {
        return null;
    }
}
