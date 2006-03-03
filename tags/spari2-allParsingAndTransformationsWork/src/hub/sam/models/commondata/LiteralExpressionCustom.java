package hub.sam.models.commondata;

import hub.sam.models.common.NamedElement;

public class LiteralExpressionCustom extends LiteralExpressionDlg {

    @Override
    public NamedElement determineType() {
        return getLiteral().getType();
    }
}
