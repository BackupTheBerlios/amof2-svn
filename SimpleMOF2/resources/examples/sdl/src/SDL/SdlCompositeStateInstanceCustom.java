package SDL;

public class SdlCompositeStateInstanceCustom extends SdlCompositeStateInstanceDlg {
    @Override
    public void initialize() {
        ((SdlInstance)getSuper(SdlInstance.class)).initialize();
        initializeStates();
    }

    @Override
    public void initializeStates() {
        for(SdlState state: getMetaClassifierSdlStateType().getState()) {
            SdlStateInstance stateInstance = state.metaCreateSdlStateInstance();
            self.getState().add(stateInstance);
        }
    }

    @Override
    public void run() {
        System.out.println("Running composite state: " + getMetaClassifierSdlStateType());
    }
}
