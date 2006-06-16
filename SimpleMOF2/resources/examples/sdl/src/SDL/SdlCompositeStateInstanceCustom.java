package SDL;

import cmof.common.ReflectiveSequence;
import hub.sam.mof.util.ListImpl;

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
        new Thread(new Runner(self)).start();
    }

    class Runner implements Runnable {
        private final SdlCompositeStateInstance self;
        Runner(SdlCompositeStateInstance self) {
            super();
            this.self = self;
        }

        public void run() {
            System.out.println("Run composite state type " + self.getMetaClassifierSdlStateType().getName());

            SdlStateAutomaton automaton = self.getMetaClassifierSdlStateType().getStateAutomaton();
            Start start = automaton.getStart();
            executeImmidiateTransition(start, self);

            while(true) {
                // execute any possibly imidiate transition
                while(executeImmidiateTransition(self.getActualState().iterator().next().getMetaClassifierSdlState(), self)) {
                    // nothing
                }

                // wait for arriving signal
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // consume input
                // TODO
            }
        }
    }

    @Override
    public SdlInstance getContainingInstance() {
        return self.getOwningInstance();
    }

    private static void executeAction(SdlAction action, SdlCompositeStateInstance self) {
        if (action instanceof SdlOutput) {
            SdlOutput output = (SdlOutput)action;
            SdlSignalInstance signal = output.getClassifier().metaCreateSdlSignalInstance();
            signal.initialize();
            signal.setSender(self.getOwningInstance());

            // evaluate the to expression
            SdlEvaluation toEval = (SdlEvaluation)output.getTo().instantiate();
            toEval.updateContext(self.getOwningInstance());
            signal.setReceiver(((PidValue)toEval.getValue()).getValue());

            self.getOwningInstance().dispatchSignal(signal, output.getVia());
        } else if (action instanceof SdlCreate) {
            SdlCreate create = (SdlCreate)action;
            SdlAgent agent = create.getAgent();
            boolean created = false;
            for(SdlAgentInstanceSet instanceSet: self.getOwningInstance().getAgentInstanceSet()) {
                if (agent.equals(instanceSet.getMetaClassifierSdlAgent())) {
                    SdlAgentType type = agent.getType();
                    SdlAgentInstance instance = type.metaCreateSdlAgentInstance();
                    instance.initialize();
                    instanceSet.getValue().add(instance); // TODO max instances check
                    instance.run();
                    created = true;
                }
            }
            if (!created) {
                System.err.println("create dropped");
            }
        } else if (action instanceof SdlAssignment) {
            SdlAssignment assignment = (SdlAssignment)action;
            SdlVariable variable = assignment.getVariable();
            ReflectiveSequence<SdlVariableSlot> slots = new ListImpl<SdlVariableSlot>();
            slots.addAll(self.getVariable());
            slots.addAll(self.getOwningInstance().getVariable()); // TODO recusive
            boolean set = false;
            for (SdlVariableSlot slot: slots) {
                if (slot.getMetaClassifierSdlVariable().equals(variable)) {
                    SdlEvaluation expr = (SdlEvaluation)assignment.getExpression().instantiate();
                    expr.updateContext(self.getOwningInstance());
                    SdlDataValue value = (SdlDataValue)expr.getValue();
                    slot.updateValue(value);
                }
            }
        }
    }

    private static boolean executeImmidiateTransition(SdlAbstractState pstate, SdlCompositeStateInstance self) {
        // select transition
        SdlTransition transtion = null;
        for(SdlTrigger trigger: pstate.getTrigger()) {
            if (trigger instanceof SdlImidiate) {
                transtion = trigger.getTransition();
            }
        }
        if (transtion == null) {
            return false;
        }

        // execute transtion
        for(SdlAction action: transtion.getAction()) {

        }

        // enter next state
        SdlAbstractState target = transtion.getTarget();
        if (target instanceof SdlPseudoState) {
            // TODO stop, decision, fork, join, etc.
            executeImmidiateTransition(target, self);
        } else {
            SdlStateInstance targetInstance = null;
            for (SdlStateInstance stateInstance: self.getState()) {
                if (stateInstance.getMetaClassifierSdlState().equals(target)) {
                    targetInstance = stateInstance;
                }
            }
            if (targetInstance == null) {
                throw new RuntimeException("assert");
            }
            self.getActualState().set(0, (SdlStateInstance)targetInstance);
        }
        return true;
    }
}
