package hub.sam.models.common;

import hub.sam.models.commonbehaviour.Action;

public class FeatureCustom extends FeatureDlg {

    @Override
    public Classifier determineClassifier() {
        if (this instanceof Action) {
            // TODO might be SDL specific
            // action/transition
            // state automaton
            // state type
            return (Classifier)((cmof.reflection.Object)self).container().container();
        } else {
            return null;
        }
    }
}
