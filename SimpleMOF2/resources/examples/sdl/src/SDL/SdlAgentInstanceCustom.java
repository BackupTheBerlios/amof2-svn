package SDL;

public class SdlAgentInstanceCustom extends SdlAgentInstanceDlg {

    @Override
    public void initialize() {
        ((SdlInstance)getSuper(SdlInstance.class)).initialize();
        self.initializeBehavior();
    }

    @Override
    public void createSlots() {
        ((SdlInstance)getSuper(SdlInstance.class)).createSlots();
        for(SdlAgent agent : self.getMetaClassifierSdlAgentType().getAgent()) {
            self.getAgentInstanceSet().add(agent.instanciate());
        }
    }

    @Override
    public void initializeBehavior() {
        SdlBehavior behavior = getMetaClassifierSdlAgentType().getBehavior();
        if (behavior == null) {
            return;
        }
        self.setBehavior(((SdlCompositeState)behavior).getType().metaCreateSdlCompositeStateInstance());
        self.getBehavior().initialize();
    }

    @Override
    public void run() {
        if (self.getBehavior() != null) {
            self.getBehavior().run();
        }
    }
}
