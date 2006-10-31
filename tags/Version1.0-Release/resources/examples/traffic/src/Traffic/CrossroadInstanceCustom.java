package Traffic;

public class CrossroadInstanceCustom extends CrossroadInstanceDlg {

    @Override
    public void justDoIt() {
        Crossroad crossroad = getMetaClassifier();
        for(SequenceColumn col: crossroad.getLightSequence()) {
            System.out.println("-----");
            for(SequenceEntry entry: col.getEntries()) {
                Color color = entry.getColor();
                TrafficLight light = entry.getSignal();
                TrafficLightInstance trafficLightInstance = light.getMetaInstance().iterator().next();

                SignalLightType lightType = null;
                TypeLoop:
                for(SignalLightType type: light.getType().getLights()) {
                    if (type.getColor() == color) {
                        lightType = type;
                        break TypeLoop;
                    }
                }

                trafficLightInstance.justDoIt(lightType);
            }
            try {
                Thread.sleep(col.getDuration());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
