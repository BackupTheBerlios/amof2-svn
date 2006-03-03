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

package core.abstractions.instances;


/**
 * <b>InstanceSpecification</b>, superClass = {core.abstractions.namespaces.NamedElement}
 * <br>constraint - slots_are_defined : 
 * <pre>slot->forAll(s | classifier->exists(c | c.allFeatures()->includes(s.definingFeature))</pre>
 * <br>constraint - no_duplicate_slots : 
 * <pre>classifier->forAll(c | (c.allFeatures()->forAll(f | slot->select(s | s.definingFeature = f)->size() <= 1) )</pre>
 */
public interface InstanceSpecification extends core.abstractions.namespaces.NamedElement
{

    /**
     * <b>slot</b>, multiplicity=(0,*), isComposite
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.instances.Slot> getSlot();

    /**
     * <b>classifier</b>, multiplicity=(1,*)
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.classifiers.Classifier> getClassifier();

    /**
     * <b>specification</b>, multiplicity=(0,1), isComposite
     */
    public core.abstractions.expressions.ValueSpecification getSpecification();

    public void setSpecification(core.abstractions.expressions.ValueSpecification value);

}

