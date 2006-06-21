package SDL;

import cmof.common.ReflectiveCollection;
import hub.sam.mof.util.ListImpl;

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

    @Override
    public ReflectiveCollection<? extends SdlCompositeStateInstance> getProcess() {
        ReflectiveCollection<? extends SdlCompositeStateInstance> result = new ListImpl<SdlCompositeStateInstance>();
        for(SdlAgentInstance agent: self.getValue()) {
            result.add(agent.getBehavior());
        }
        return result;
    }
}
