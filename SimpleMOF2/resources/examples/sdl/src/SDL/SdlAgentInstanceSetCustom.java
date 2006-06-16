package SDL;

public class SdlAgentInstanceSetCustom extends SdlAgentInstanceSetDlg {

    @Override
    public void initialize() {
        self.createInitialValues();
    }

    @Override
    public void createInitialValues() {
        int minInstances = self.getMetaClassifierSdlAgent().getLower();
        for(int i = 0; i < minInstances; i++) {
            self.getValue().add(getMetaClassifierSdlAgent().getType().instanciate());
        }
    }
}
