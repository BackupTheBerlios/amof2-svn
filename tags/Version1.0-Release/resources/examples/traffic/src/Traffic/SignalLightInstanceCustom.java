package Traffic;

public class SignalLightInstanceCustom extends SignalLightInstanceDlg {
    @Override
    public void justDoIt() {
        if (isOn()) {
            switchOff();
        } else {
            switchOn();
        }
    }

    @Override
    public void switchOn() {
        setIsOn(true);
        System.out.println("ON: " + getMetaClassifier().getColor() + ":" + getTrafficLight().getMetaClassifier().getLightId());
    }

    @Override
    public void switchOff() {
        setIsOn(false);
        System.out.println("OFF: " + getMetaClassifier().getColor() + ":" + getTrafficLight().getMetaClassifier().getLightId());
    }
}
