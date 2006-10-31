package Java;

public class NewCustom extends NewDlg {

    @Override
    public Object evaluate(CallFrame context) {
        Object result = self.getUmlClass().createObject();
        for (Variable memberVariable: self.getUmlClass().getVariable()) {
            if (memberVariable.getScope() == Scope.MEMBER) {
                result.getMemberVariable().add(memberVariable.createSlot());
            }
        }
        return result;
    }
}
