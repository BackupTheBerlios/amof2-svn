package core.abstractions.classifiers;


/**
 * <b>Classifier</b>, isAbstract, superClass = {core.abstractions.namespaces.Namespace}
 */
public interface Classifier extends core.abstractions.namespaces.Namespace
{

    /**
     * <b>allFeatures</b>, multiplicity=(0,*)
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.classifiers.Feature> allFeatures() ;

    /**
     * <b>feature</b>, multiplicity=(0,*), isDerivedUnion, isDerived, isUnique, subsettedProperty = {core.abstractions.namespaces.Namespace.member}
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.classifiers.Feature> getFeature();

}

