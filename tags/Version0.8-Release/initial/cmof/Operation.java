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
 * <b>Operation</b>, superClass = {core.basic.Operation, cmof.BehavioralFeature}
 * <br>constraint - type_of_result : 
 * <pre>if returnResult->size() = 1 then type = returnResult.type else type = nil endif </pre>
 * <br>constraint - only_body_for_query : 
 * <pre>bodyCondition->notEmpty() implies isQuery</pre>
 */
public interface Operation extends core.basic.Operation, cmof.BehavioralFeature
{

    /**
     * <b>isQuery</b>, multiplicity=(1,1)
     */
    public boolean isQuery();

    public void setIsQuery(boolean value);

    /**
     * <b>isOrdered</b>, multiplicity=(1,1), redefinedProperty = {core.abstractions.multiplicities.MultiplicityElement.isOrdered}
     */
    public boolean isOrdered();

    public void setIsOrdered(boolean value);

    /**
     * <b>isUnique</b>, multiplicity=(1,1), redefinedProperty = {core.abstractions.multiplicities.MultiplicityElement.isUnique}
     */
    public boolean isUnique();

    public void setIsUnique(boolean value);

    /**
     * <b>lower</b>, multiplicity=(0,1), redefinedProperty = {core.abstractions.multiplicities.MultiplicityElement.lower}
     */
    public int getLower();

    public void setLower(int value);

    /**
     * <b>upper</b>, multiplicity=(0,1), redefinedProperty = {core.abstractions.multiplicities.MultiplicityElement.upper}
     */
    public long getUpper();

    public void setUpper(long value);

    /**
     * <b>class</b>, multiplicity=(0,1), subsettedProperty = {cmof.RedefinableElement.redefinitionContext, cmof.NamedElement.namespace, cmof.Feature.featuringClassifier}
     */
    public cmof.UmlClass getUmlClass();

    public void setUmlClass(cmof.UmlClass value);

    public void setUmlClass(core.basic.UmlClass value);

    /**
     * <b>datatype</b>, multiplicity=(0,1), subsettedProperty = {cmof.RedefinableElement.redefinitionContext, cmof.NamedElement.namespace, cmof.Feature.featuringClassifier}
     */
    public cmof.DataType getDatatype();

    public void setDatatype(cmof.DataType value);

    /**
     * <b>raisedException</b>, multiplicity=(0,*), redefinedProperty = {core.basic.Operation.raisedException, cmof.BehavioralFeature.raisedException}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Type> getRaisedException();

    /**
     * <b>formalParameter</b>, multiplicity=(0,*), isComposite
     */
    public cmof.common.ReflectiveSequence<? extends cmof.Parameter> getFormalParameter();

    /**
     * <b>precondition</b>, multiplicity=(0,*), isComposite
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Constraint> getPrecondition();

    /**
     * <b>postcondition</b>, multiplicity=(0,*), isComposite
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Constraint> getPostcondition();

    /**
     * <b>redefinedOperation</b>, multiplicity=(0,*), subsettedProperty = {cmof.RedefinableElement.redefinedElement}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Operation> getRedefinedOperation();

    /**
     * <b>type</b>, multiplicity=(0,1)
     */
    public cmof.Type getType();

    public void setType(cmof.Type value);

    /**
     * <b>bodyCondition</b>, multiplicity=(0,1), isComposite
     */
    public cmof.Constraint getBodyCondition();

    public void setBodyCondition(cmof.Constraint value);

    /**
     * <b>isOrdered</b>
     */
    public boolean isOrderedOperation() ;

    /**
     * <b>isUnique</b>
     */
    public boolean isUniqueOperation() ;

    /**
     * <b>lower</b>
     */
    public int lowerOperation() ;

    /**
     * <b>upper</b>
     */
    public long upperOperation() ;

    /**
     * <b>type</b>
     */
    public cmof.Classifier typeOperation() ;

    /**
     * <b>isConsistentWith</b>
     */
    public boolean isConsistentWith(core.abstractions.redefinitions.RedefinableElement redefinee) ;

    /**
     * <b>isConsistentWith</b>
     */
    public boolean isConsistentWith(cmof.RedefinableElement redefinee) ;

}

