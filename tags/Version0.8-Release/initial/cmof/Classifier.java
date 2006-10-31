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
 * <b>Classifier</b>, isAbstract, superClass = {cmof.Namespace, core.abstractions.super.Classifier, cmof.Type}
 */
public interface Classifier extends cmof.Namespace, core.abstractions.umlsuper.Classifier, cmof.Type
{

    /**
     * <b>attribute</b>, multiplicity=(0,*), isDerived
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Property> getAttribute();

    /**
     * <b>feature</b>, multiplicity=(0,*), isDerived
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Feature> getFeature();

    /**
     * <b>general</b>, multiplicity=(0,*), redefinedProperty = {core.abstractions.super.Classifier.general}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getGeneral();

    /**
     * <b>conformsTo</b>
     */
    public boolean conformsTo(core.abstractions.typedelements.Type other) ;

    /**
     * <b>conformsTo</b>
     */
    public boolean conformsTo(cmof.Classifier other) ;

}

