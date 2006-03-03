package hub.sam.models.commondata;

import hub.sam.models.common.NamedElement;

public class ExpressionCustom extends ExpressionDlg {

    @Override
    public NamedElement determineType() {
        System.out.println("####: Expression");
        return null;
    }

    @Override
    public boolean isConstant() {
        if (this instanceof LiteralExpression) {
            return true;
        } else if (this instanceof OperationApplication) {
            boolean result = true;
            for (Expression expression : ((OperationApplication)this).getOperands()) {
                if (!expression.isConstant()) {
                    result = false;
                }
            }
            return result;
        } else {
            return false;
        }
    }
}
