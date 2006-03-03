package hub.sam.models.commondata;

import hub.sam.models.common.NamedElement;

public class VariableAccessCustom extends VariableAccessDlg {

    @Override
    public NamedElement determineType() {
        return getVariable().getType();
    }
}
