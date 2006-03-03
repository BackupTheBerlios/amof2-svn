package hub.sam.models.commondata;

import hub.sam.models.common.NamedElement;
import hub.sam.models.common.TypedElement;

public class OperationApplicationCustom extends OperationApplicationDlg {

    @Override
    public NamedElement determineType() {
        return ((TypedElement)getType()).getType();
    }
}
