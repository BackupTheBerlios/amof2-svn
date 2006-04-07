Traffic::SignalLightInstance::justDoIt activity {
    start;
    decision [self.isOn] {
        Call: switchOff;
    } [else] {
        Call: switchOn;
    }
    end;
}

Traffic::TrafficLightInstance::justDoIt activity {
    start >new lightType:SignalLightType;
    Expression: [lights->select(l|l.metaClassifier = lightType)->asSequence()->first()] <lightType as lightType, >new signal:SignalLightInstance;
    decision [not self.isOn] <signal as context {
        decision [lights->select(l|l.isOn)->notEmpty()] {
            Call: justDoIt <{Expression: [lights->select(l|l.isOn)->asSequence()->first()]}:SignalLightInstance as context;
        } [else] {}
        Call: justDoIt <signal as context;
    } [else] {

    }
    end;
}
