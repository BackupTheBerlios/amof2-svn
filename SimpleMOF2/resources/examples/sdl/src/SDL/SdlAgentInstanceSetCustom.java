package SDL;

public class SdlAgentInstanceSetCustom extends SdlAgentInstanceSetDlg {

    @Override
    public void initialize() {
        initializeGates();
        self.createInitialValues();
    }

    @Override
    public void initializeGates() {
        for(SdlGate gate: self.getMetaClassifierSdlAgent().getType().getGate()) {
            SdlGateInstance gateInstance = gate.metaCreateSdlGateInstance();
            getGate().add(gateInstance);
        }
    }

    @Override
    public void createInitialValues() {
        int minInstances = self.getMetaClassifierSdlAgent().getLower();
        for(int i = 0; i < minInstances; i++) {
            self.getValue().add(getMetaClassifierSdlAgent().getType().instanciate());
        }
    }
}
