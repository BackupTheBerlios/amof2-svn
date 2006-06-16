package SDL;

import cmof.common.ReflectiveCollection;

public class SdlVariableAccessEvaluationCustom extends SdlVariableAccessEvaluationDlg {
    @Override
    public SdlDataValue getValue() {
        ReflectiveCollection<? extends SdlDataValue> values =
                self.getContext().resolveSlot(self.getMetaClassifierSdlVariableAccess().getFeature()).getValue();
        if (values.size() == 0) {
            return null;
        } else {
            return values.iterator().next();
        }
    }
}
