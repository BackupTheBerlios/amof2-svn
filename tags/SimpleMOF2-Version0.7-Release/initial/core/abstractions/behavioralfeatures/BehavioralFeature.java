package core.abstractions.behavioralfeatures;


public interface BehavioralFeature extends core.abstractions.classifiers.Feature, core.abstractions.namespaces.Namespace
{

    public cmof.common.ReflectiveSequence<? extends core.abstractions.behavioralfeatures.Parameter> getParameter();

}

