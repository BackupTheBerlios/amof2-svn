package SDL;

public class SdlInstanceCustom extends SdlInstanceDlg {

    @Override
    public void initialize() {
        self.createSlots();
    }

    @Override
    public void createSlots() {
        for(SdlVariable variable : self.getMetaClassifierSdlClassifier().getVariable()) {
            self.getVariable().add(variable.metaCreateSdlVariableSlot());
        }
    }

    @Override
    public SdlVariableSlot resolveSlot(SdlVariable v) {
        SdlInstance instance = self;
        while (instance != null) {
            for (SdlVariableSlot slot: instance.getVariable()) {
                if (slot.getMetaClassifierSdlVariable().equals(v)) {
                    return slot;
                }
            }
            instance = getContainingInstance();
        }
        return null;
    }
}
