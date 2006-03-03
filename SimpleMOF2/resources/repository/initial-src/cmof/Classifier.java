package cmof;


/**
 * <b>Classifier</b>, isAbstract, superClass = {cmof.Namespace, core.abstractions.super.Classifier, cmof.Type}
 */
public interface Classifier extends cmof.Namespace, core.abstractions.umlsuper.Classifier, cmof.Type
{

    /**
     * <b>conformsTo</b>, multiplicity=(1,1)
     */
    public boolean conformsTo(cmof.Classifier other) ;

    /**
     * <b>conformsTo</b>, multiplicity=(1,1)
     */
    public boolean conformsTo(core.abstractions.typedelements.Type other) ;

    /**
     * <b>attribute</b>, multiplicity=(0,*), isDerivedUnion, isDerived, isUnique, subsettedProperty = {cmof.Classifier.feature}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Property> getAttribute();

    /**
     * <b>feature</b>, multiplicity=(0,*), isDerivedUnion, isDerived, isUnique, subsettedProperty = {cmof.Namespace.member}, redefinedProperty = {core.abstractions.classifiers.Classifier.feature}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Feature> getFeature();

    /**
     * <b>general</b>, multiplicity=(0,*), isUnique, redefinedProperty = {core.abstractions.super.Classifier.general}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getGeneral();

}

