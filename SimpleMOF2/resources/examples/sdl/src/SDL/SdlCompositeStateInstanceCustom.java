package SDL;

import Pattern.Communication.Event;
import Pattern.Communication.Listener;
import Pattern.Evaluation.Evaluation;
import cmof.common.ReflectiveSequence;
import hub.sam.mof.util.ListImpl;

import java.util.Collection;
import java.util.Vector;

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
    public void start() {
        new Thread(new Runner(self)).start();
    }

    @Override
    public synchronized void run() {
        System.out.println("Run composite state type " + self.getMetaClassifierSdlStateType().getName());

        SdlStateAutomaton automaton = self.getMetaClassifierSdlStateType().getStateAutomaton();
        Start start = automaton.getStart();
        executeImmidiateTransition(start, self);

        //noinspection InfiniteLoopStatement
        while(true) {
            // execute any possibly imidiate transition
            while(executeImmidiateTransition(self.getActualState().iterator().next().getMetaClassifierSdlState(), self)) {
                // nothing
            }

            // wait for arriving signal
            if (self.getTriggered() == null) {
                try {
                    self.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // consume input
            SdlTransition transition = self.getTriggered().getMetaClassifierSdlInput().getTransition();
            self.setTriggered(null);
            // remove obsolete triggers
            Collection<SdlInputInstance> oldTriggers = new Vector<SdlInputInstance>();
            for (SdlInputInstance oldTrigger: self.getInput()) {
                oldTriggers.add(oldTrigger);
            }
            self.getInput().removeAll(oldTriggers);
            for (SdlInputInstance oldTrigger: oldTriggers) {
                oldTrigger.metaDelete();
            }
            executeTransition(transition, self);
        }
    }

    @Override
    public void consume(Listener listener, Event event) {
        SdlSignalInstance signal = (SdlSignalInstance)event;
        // TODO parameter
        self.setSender(((SdlSignalInstance)event).getSender());
        self.setTriggered((SdlInputInstance)listener);
        self.notify();
    }

    class Runner implements Runnable {
        private final SdlCompositeStateInstance self;
        Runner(SdlCompositeStateInstance self) {
            super();
            this.self = self;
        }

        public synchronized void run() {
            self.run();
        }
    }

    @Override
    public SdlInstance getContainingInstance() {
        return self.getOwningInstance();
    }

    private static void executeAction(SdlAction action, SdlCompositeStateInstance self) {
        System.out.println("execute action at line " + action.getLine());
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
                    self.getOwningInstance().setOffspring(instance);
                    instanceSet.getValue().add(instance); // TODO max instances check
                    instance.run();
                    // TODO parameters
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
                    expr.updateContext(self);
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


        executeTransition(transtion, self);
        return true;
    }

    private static void executeTransition(SdlTransition transition, SdlCompositeStateInstance self) {
        // execute transtion
        for(SdlAction action: transition.getAction()) {
            executeAction(action, self);
        }

        // enter next state
        SdlAbstractState target = transition.getTarget();
        if (target instanceof SdlPseudoState) {
            if (target instanceof SdlSplit) {
                executeSplit((SdlSplit)target, self);
            } else {
                // TODO stop, fork, join, etc.
                executeImmidiateTransition(target, self);
            }
        } else if (target instanceof SdlStateNode) {
            SdlStateNode targetStateNode = (SdlStateNode)target;
            if (targetStateNode.getState().size() == 0) {
                // stay in the current state
            } else {
                nextstate(self, targetStateNode.getState().iterator().next());
            }
        } else {
            nextstate(self, target);
        }
    }

    private static void executeSplit(SdlSplit split, SdlCompositeStateInstance self) {
        SdlEvaluation questionEvaluation = (SdlEvaluation)split.getQuestion().instantiate();
        questionEvaluation.updateContext(self);
        SdlChoice choosen = null;
        SdlChoice elseChoice = null;
        for (SdlChoice choice: split.getAnswer()) {
            if (choice instanceof SdlElse) {
                elseChoice = choice;
            } else {
                for (SdlRange rangeCondition: choice.getCondition()) {
                    if (rangeCondition instanceof SdlOpenRange) {
                        SdlOpenRange condition = (SdlOpenRange)rangeCondition;
                        if (evalOpenRange(condition, self, questionEvaluation)) {
                            if (choosen == null) {
                                choosen = choice;
                            } else {
                                throw new RuntimeException("assert, two choices are true");
                            }
                        }
                    } else {
                        SdlClosedRange condition = (SdlClosedRange)rangeCondition;
                        if (evalOpenRange(condition.getLower(), self, questionEvaluation) &&
                                evalOpenRange(condition.getUpper(), self, questionEvaluation)) {
                            if (choosen == null) {
                                choosen = choice;
                            } else {
                                throw new RuntimeException("assert, two choices are true");
                            }
                        }
                    }
                }
            }
        }
        if (choosen == null) {
            if (elseChoice == null) {
                throw new RuntimeException("assert, no choice selected");
            } else {
                choosen = elseChoice;
            }
        }
        executeTransition(choosen.getTransition(), self);
    }

    private static boolean evalOpenRange(SdlOpenRange condition, SdlCompositeStateInstance self,
                                      Evaluation questionEvaluation) {
        // TODO, use the actual condition operator
        SdlEvaluation choiceEvaluation = (SdlEvaluation)condition.getExpression().instantiate();
        choiceEvaluation.updateContext(self);
        return ((SdlGeneralValue)questionEvaluation.getValue()).getValue().equals(
                ((SdlGeneralValue)choiceEvaluation.getValue()).getValue());
    }

    private static void nextstate(SdlCompositeStateInstance self, SdlAbstractState target) {
        SdlStateInstance targetInstance = null;
        for (SdlStateInstance stateInstance: self.getState()) {
            if (stateInstance.getMetaClassifierSdlState().equals(target)) {
                targetInstance = stateInstance;
            }
        }
        if (targetInstance == null) {
            throw new RuntimeException("assert");
        }
        enterState(targetInstance, self);
    }

    private static void enterState(SdlStateInstance state, SdlCompositeStateInstance self) {
        self.getActualState().set(0, state);

        // get state nodes that cover the actual state
        Collection<SdlStateNode> stateNodesWithActualState = new Vector<SdlStateNode>();
        for (SdlAbstractState abstractState: self.getMetaClassifierSdlStateType().getStateAutomaton().getState()) {
            if (abstractState instanceof SdlStateNode) {
                SdlStateNode stateNode = (SdlStateNode)abstractState;
                if (stateNode.getState().contains(state.getMetaClassifierSdlState())) {
                    stateNodesWithActualState.add(stateNode);
                }
            }
        }
        for (SdlStateNode stateNode: stateNodesWithActualState) {
            for (SdlTrigger trigger: stateNode.getTrigger()) {
                self.getInput().add(((SdlInput)trigger).metaCreateSdlInputInstance());
            }
        }
    }
}
