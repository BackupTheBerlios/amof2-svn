package SDL;

import cmof.common.ReflectiveCollection;
import hub.sam.mof.util.SetImpl;

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
        for(SdlAgentInstanceSet slot: getAgentInstanceSet()) {
            for(SdlAgentInstance agent: slot.getValue()) {
                agent.run();
            }
        }
        SdlCompositeStateInstance behavior = self.getBehavior();
        if (behavior != null) {
            behavior.start();
        }
    }

    @Override
    public SdlInstance getContainingInstance() {
        return self.getOwningInstanceSet().getAgentInstance();
    }

    @Override
    public SdlAgentInstanceSet leadsTo(SdlChannelPath path) {
        SdlAgent agent = path.getTarget().iterator().next().getAgent();
        if (agent == null) {
            return self.getOwningInstanceSet();
        } else {
            for(SdlAgentInstanceSet slot: self.getAgentInstanceSet()) {
                if (slot.getMetaClassifierSdlAgent().equals(agent)) {
                    return slot;
                }
            }
        }

        return null;
    }

    private static SdlAgentType getAgentTypeForPath(SdlChannelPath path, SdlAgentInstance instance) {
        SdlAgentType agent = path.getTarget().iterator().next().getAgent().getType();
        if (agent == null) {
            agent = instance.getOwningInstanceSet().getAgentInstance().getMetaClassifierSdlAgentType();
        }
        return agent;
    }

    /**
     * Queries a set of SdlChannelPaths for a path. The result contains all ChannelPaths that source
     * in the paths target end point.
     *
     * @param path A SdlChannelPath
     * @return A set of SdlChannelPaths
     */
    @Override
    public ReflectiveCollection<? extends SdlChannelPath> continuesWith(SdlChannelPath path) {
        ReflectiveCollection<? extends SdlChannelPath> result = new SetImpl<SdlChannelPath>();
        SdlAgentType context = getAgentTypeForPath(path, self);

        SdlGate gate = path.getTarget().iterator().next().getGate();
        if (gate == null) {
            return result;
        }

        for (SdlChannel channel: context.getChannel()) {
            for (SdlChannelPath continuingPath: channel.getPath()) {
                SdlGate continueingGate = continuingPath.getSource().iterator().next().getGate();
                if (gate.equals(continueingGate)) {
                    result.add(continuingPath);
                }
            }
        }
        return result;
    }

    @Override
    public void dispatchSignal(SdlSignalInstance s, SdlGate via) {
        SdlAgentInstanceSet dispatchedTo = null;
        SdlSignal signal = s.getMetaClassifierSdlSignal();
        for (SdlAgentInstanceSet receiver: collectAgentInstanceSets(s, via, self)) {
            if (s.getReceiver() != null) {
                if (receiver.getValue().contains(s.getReceiver())) {
                    dispatchedTo = receiver;
                    receiver.getInputQueue().add(s);
                    receiver.update();
                }
            } else {
                dispatchedTo = receiver;
                receiver.getInputQueue().add(s);
                receiver.update();
            }
        }
        if (dispatchedTo == null) {
            System.out.println("signal dropped");
        } else {
            System.out.println("signal of type " + signal + " dipatched to instance of agent " +
                    dispatchedTo.getMetaClassifierSdlAgent());
        }
    }

    private static ReflectiveCollection<? extends SdlAgentInstanceSet> collectAgentInstanceSets(
            SdlSignalInstance s, SdlGate via, SdlAgentInstance self) {
        if (via != null) {
            return collectAgentInstanceSets(s.getMetaClassifierSdlSignal(), via, self);
        } else {
            for(SdlGate gate: self.getMetaClassifierSdlAgentType().getGate()) {
                return collectAgentInstanceSets(s.getMetaClassifierSdlSignal(), gate, self);
            }
        }
        return new SetImpl<SdlAgentInstanceSet>();
    }

    private static ReflectiveCollection<? extends SdlAgentInstanceSet> collectAgentInstanceSets(SdlSignal s,
            SdlGate gate, SdlAgentInstance self) {
        ReflectiveCollection<? extends SdlAgentInstanceSet> result = new SetImpl<SdlAgentInstanceSet>();
        for(SdlChannelEnd end: gate.getChannelEnd()) {
            SdlChannelPath path = end.getChannel();
            if (path.getTarget().equals(end)) {
                collectAgentInstanceSets(s, path, result, self.getOwningInstanceSet().getAgentInstance());
            } else {
                collectAgentInstanceSets(s, path, result, self);
            }
        }
        return result;
    }

    private static void collectAgentInstanceSets(SdlSignal s, SdlChannelPath path,
                                          ReflectiveCollection<? extends SdlAgentInstanceSet> result,
                                          SdlAgentInstance self) {
        if (path.getSignal().contains(s)) {
            SdlAgentInstanceSet leadsTo = self.leadsTo(path);
            if (leadsTo.getMetaClassifierSdlAgent().getType().getKind() == SdlAgentKind.PROCESS) {
                result.add(leadsTo);
            } else {
                for(SdlAgentInstance instance: leadsTo.getValue()) {
                    for(SdlChannelPath nextPath: instance.continuesWith(path)) {
                        collectAgentInstanceSets(s, nextPath, result, instance);
                    }
                }
            }
        }
    }
}
