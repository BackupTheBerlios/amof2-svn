package hub.sam.mof.jocl.teststuff;

import cmof.NamedElement;
import hub.sam.mof.jocl.standardlib.OclModelElement;
import hub.sam.mof.jocl.standardlib.OclString;

public class NamedElementValue extends OclModelElement<NamedElement> /* implements NamedElement */ {

    protected NamedElementValue(NamedElement value) {
        super(value);
    }

    public OclString name() {
        return null;
    }

}
