package cmof;


public interface BehavioralFeature extends core.abstractions.behavioralfeatures.BehavioralFeature, cmof.Feature, cmof.Namespace
{

    public cmof.common.ReflectiveSequence<? extends cmof.Parameter> getFormalParameter();

    public cmof.common.ReflectiveSequence<? extends cmof.Parameter> getReturnResult();

    public cmof.common.ReflectiveCollection<? extends cmof.Type> getRaisedException();

    public cmof.common.ReflectiveSequence<? extends cmof.Parameter> getParameter();

}

