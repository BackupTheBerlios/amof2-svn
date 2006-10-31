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

package core.abstractions.generalizations;


/**
 * <b>Classifier</b>, isAbstract, superClass = {core.abstractions.super.Classifier, core.abstractions.typedelements.Type}
 * <br>constraint - general_equals_parents : 
 * <pre>general = self.parents()</pre>
 */
public interface Classifier extends core.abstractions.umlsuper.Classifier, core.abstractions.typedelements.Type
{

    /**
     * <b>generalization</b>, multiplicity=(0,*), isComposite
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.generalizations.Generalization> getGeneralization();

    /**
     * <b>general</b>, multiplicity=(0,*), isDerived
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.generalizations.Classifier> getGeneral();

    /**
     * <b>general</b>
     */
    public core.abstractions.generalizations.Classifier generalOperation() ;

    /**
     * <b>parents</b>
     */
    public core.abstractions.umlsuper.Classifier parents() ;

    /**
     * <b>conformsTo</b>
     */
    public boolean conformsTo(core.abstractions.typedelements.Type other) ;

    /**
     * <b>conformsTo</b>
     */
    public boolean conformsTo(core.abstractions.generalizations.Classifier other) ;

}

