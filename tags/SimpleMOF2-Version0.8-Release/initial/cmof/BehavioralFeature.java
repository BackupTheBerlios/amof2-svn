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
 * <b>BehavioralFeature</b>, isAbstract, superClass = {core.abstractions.behavioralfeatures.BehavioralFeature, cmof.Feature, cmof.Namespace}
 */
public interface BehavioralFeature extends core.abstractions.behavioralfeatures.BehavioralFeature, cmof.Feature, cmof.Namespace
{

    /**
     * <b>formalParameter</b>, multiplicity=(0,*), isComposite
     */
    public cmof.common.ReflectiveSequence<? extends cmof.Parameter> getFormalParameter();

    /**
     * <b>returnResult</b>, multiplicity=(0,*), isComposite
     */
    public cmof.common.ReflectiveSequence<? extends cmof.Parameter> getReturnResult();

    /**
     * <b>raisedException</b>, multiplicity=(0,*)
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Type> getRaisedException();

    /**
     * <b>parameter</b>, multiplicity=(0,*), isDerived
     */
    public cmof.common.ReflectiveSequence<? extends cmof.Parameter> getParameter();

    /**
     * <b>isDistinguishableFrom</b>
     */
    public boolean isDistinguishableFrom(core.abstractions.namespaces.NamedElement n, core.abstractions.namespaces.Namespace ns) ;

    /**
     * <b>isDistinguishableFrom</b>
     */
    public boolean isDistinguishableFrom(cmof.NamedElement n, cmof.Namespace ns) ;

}

