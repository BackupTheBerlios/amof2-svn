package Java;

import cmof.exception.ModelException;

public class CallFrameCustom extends CallFrameDlg {

    @Override
    public Slot slotForVariable(Variable var) {
        for (Slot local: self.getLocalVariable()) {
            if (local.getMetaClassifier().equals(var)) {
                return local;
            }
        }

        for (Slot member: self.getThis().getMemberVariable()) {
            if (member.getMetaClassifier().equals(var)) {
                return member;
            }
        }

        for (Slot classVar: self.getThis().getMetaClassifier().getClassVariable()) {
            if (classVar.getMetaClassifier().equals(var)) {
                return classVar;
            }
        }

        throw new ModelException("Cannot resolve variable.");
    }
}
