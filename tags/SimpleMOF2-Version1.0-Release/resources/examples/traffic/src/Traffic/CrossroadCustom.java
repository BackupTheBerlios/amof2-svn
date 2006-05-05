package Traffic;

import cmof.common.ReflectiveCollection;

public class CrossroadCustom extends CrossroadDlg {

    @Override
    public void justDoIt() {
        CrossroadInstance crossroadInstance = this.createCrossroadInstance();
        ReflectiveCollection signals = crossroadInstance.getSignals();
        for(TrafficLight trafficLight: getSignalDef()) {
            TrafficLightInstance trafficLightInstance = trafficLight.createTrafficLightInstance();
            signals.add(trafficLightInstance);
            ReflectiveCollection lights = trafficLightInstance.getLights();
            for(SignalLightType signalLightType: trafficLight.getType().getLights()) {
                SignalLightInstance signalLightInstance = signalLightType.createSignalLightInstance();
                lights.add(signalLightInstance);
                signalLightInstance.switchOff();
            }
        }
        crossroadInstance.justDoIt();
    }
}
