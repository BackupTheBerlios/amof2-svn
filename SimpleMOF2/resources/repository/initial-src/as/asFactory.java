package as;


public interface asFactory extends cmof.reflection.Factory {

    public as.ForkNode createForkNode();

    public as.GuardSpecification createGuardSpecification();

    public as.MergeNode createMergeNode();

    public as.OpaqueAction createOpaqueAction();

    public as.OutputPin createOutputPin();

    public as.JoinNode createJoinNode();

    public as.ControlFlow createControlFlow();

    public as.ValueNode createValueNode();

    public as.FinalNode createFinalNode();

    public as.DecisionNode createDecisionNode();

    public as.Pin createPin();

    public as.InitialNode createInitialNode();

    public as.ObjectFlow createObjectFlow();

    public as.ContextExtensionPin createContextExtensionPin();

    public as.InputPin createInputPin();

    public as.ContextPin createContextPin();

    public as.Activity createActivity();

    public as.TypeString createTypeString();

}

