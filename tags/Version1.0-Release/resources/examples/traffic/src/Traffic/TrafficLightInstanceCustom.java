package Traffic;

public class TrafficLightInstanceCustom extends TrafficLightInstanceDlg {

    @Override
    public void justDoIt(SignalLightType lightType) {
        for(SignalLightInstance signalLightInstance: getLights()) {
            if (signalLightInstance.getMetaClassifier().equals(lightType)) {
                if (!signalLightInstance.isOn()) {
                   signalLightInstance.justDoIt();
                }
            } else if (signalLightInstance.isOn()) {
                signalLightInstance.justDoIt();
            }
        }
    }
}
