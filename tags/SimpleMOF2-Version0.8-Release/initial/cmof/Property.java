/*
A MOF 2 Java -- The MOF Repository tool for Java
Copyright (C) 2005 Markus Scheidgen

    This library is free software; you can redistribute it and/or modify it
under the terms of the GNU Lesser General Public License as published by the
Free Software Foundation; either version 2.1 of the License, or any later
version.

    This library is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
details.

    You should have received a copy of the GNU Lesser General Public License
along with this library; if not, write to the Free Software Foundation, Inc.,
59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
*/

package cmof;


/**
 * <b>Property</b>, superClass = {cmof.StructuralFeature, core.basic.Property}
 * <br>constraint - opposite_is_other_end : 
 * <pre>opposite = if owningAssociation->notEmpty() and association.memberEnd->size() = 2 then let otherEnd = (association.memberEnd - self)->any() in if otherEnd.owningAssociation->notEmpty then otherEnd else Set{} endif else Set {} endif</pre>
 * <br>constraint - multiplicity_of_composite : 
 * <pre>isComposite implies (upperBound()->isEmpty() or upperBound() <= 1)</pre>
 * <br>constraint - subsetting_context : 
 * <pre>subsettedProperty->notEmpty() implies (subsettingContext()->notEmpty() and subsettingContext()->forAll (sc | subsettedProperty->forAll(sp | sp.subsettingContext()->exists(c | sc.conformsTo(c)))))</pre>
 * <br>constraint - navigable_property_redefinition : 
 * <pre>(subsettedProperty->exists(sp | sp.class->notEmpty()) implies class->notEmpty()) and (redefinedProperty->exists(rp | rp.class->notEmpty()) implies class->notEmpty())</pre>
 * <br>constraint - subsetting_rules : 
 * <pre>subsettedProperty->forAll(sp | type.conformsTo(sp.type) and ((upperBound()->notEmpty() and sp.upperBound()->notEmpty()) implies upperBound()<=sp.upperBound() ))</pre>
 * <br>constraint - navigable_readonly : 
 * <pre>isReadOnly implies class->notEmpty()</pre>
 * <br>constraint - derivedUnion_is_derived : 
 * <pre>isDerivedUnion implies isDerived</pre>
 */
public interface Property extends cmof.StructuralFeature, core.basic.Property
{

    /**
     * <b>details</b>, multiplicity=(0,1)
     */
    public String getDetails();

    public void setDetails(String value);

    /**
     * <b>isReadOnly</b>, multiplicity=(1,1), redefinedProperty = {core.abstractions.changeabilities.StructuralFeature.isReadOnly, core.basic.Property.isReadOnly}
     */
    public boolean isReadOnly();

    public void setIsReadOnly(boolean value);

    /**
     * <b>isDerivedUnion</b>, multiplicity=(1,1)
     */
    public boolean isDerivedUnion();

    public void setIsDerivedUnion(boolean value);

    /**
     * <b>class</b>, multiplicity=(0,1), subsettedProperty = {cmof.NamedElement.namespace, cmof.Feature.featuringClassifier, cmof.null.classifier}
     */
    public cmof.UmlClass getUmlClass();

    public void setUmlClass(cmof.UmlClass value);

    public void setUmlClass(core.basic.UmlClass value);

    /**
     * <b>owningAssociation</b>, multiplicity=(0,1), subsettedProperty = {cmof.Property.association, cmof.NamedElement.namespace, cmof.Feature.featuringClassifier}
     */
    public cmof.Association getOwningAssociation();

    public void setOwningAssociation(cmof.Association value);

    /**
     * <b>redefinedProperty</b>, multiplicity=(0,*), subsettedProperty = {cmof.RedefinableElement.redefinedElement}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Property> getRedefinedProperty();

    /**
     * <b>subsettedProperty</b>, multiplicity=(0,*)
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Property> getSubsettedProperty();

    /**
     * <b>opposite</b>, multiplicity=(0,1), redefinedProperty = {core.basic.Property.opposite}
     */
    public cmof.Property getOpposite();

    public void setOpposite(cmof.Property value);

    public void setOpposite(core.basic.Property value);

    /**
     * <b>datatype</b>, multiplicity=(0,1), subsettedProperty = {cmof.NamedElement.namespace, cmof.Feature.featuringClassifier, cmof.null.classifier}
     */
    public cmof.DataType getDatatype();

    public void setDatatype(cmof.DataType value);

    /**
     * <b>association</b>, multiplicity=(0,1)
     */
    public cmof.Association getAssociation();

    public void setAssociation(cmof.Association value);

    /**
     * <b>opposite</b>
     */
    public cmof.Property oppositeOperation() ;

    /**
     * <b>isConsistentWith</b>
     */
    public boolean isConsistentWith(core.abstractions.redefinitions.RedefinableElement redefinee) ;

    /**
     * <b>isConsistentWith</b>
     */
    public boolean isConsistentWith(cmof.RedefinableElement redefinee) ;

    /**
     * <b>subsettingContext</b>
     */
    public cmof.Classifier subsettingContext() ;

}

