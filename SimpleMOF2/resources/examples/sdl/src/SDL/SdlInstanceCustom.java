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
}
