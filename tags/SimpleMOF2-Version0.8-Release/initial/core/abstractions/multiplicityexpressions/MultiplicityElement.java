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

package core.abstractions.multiplicityexpressions;


/**
 * <b>MultiplicityElement</b>, isAbstract, superClass = {core.abstractions.multiplicities.MultiplicityElement, core.abstractions.ownerships.Element}
 * <br>constraint - lower_eq_lowerbound : 
 * <pre>lower=lowerBound()</pre>
 * <br>constraint - upper_eq_upperbound : 
 * <pre>upper = upperBound()</pre>
 */
public interface MultiplicityElement extends core.abstractions.multiplicities.MultiplicityElement, core.abstractions.ownerships.Element
{

    /**
     * <b>lower</b>, multiplicity=(0,1), isDerived
     */
    public int getLower();

    public void setLower(int value);

    /**
     * <b>upper</b>, multiplicity=(0,1), isDerived
     */
    public long getUpper();

    public void setUpper(long value);

    /**
     * <b>upperValue</b>, multiplicity=(0,1), isComposite
     */
    public core.abstractions.expressions.ValueSpecification getUpperValue();

    public void setUpperValue(core.abstractions.expressions.ValueSpecification value);

    /**
     * <b>lowerValue</b>, multiplicity=(0,1), isComposite
     */
    public core.abstractions.expressions.ValueSpecification getLowerValue();

    public void setLowerValue(core.abstractions.expressions.ValueSpecification value);

    /**
     * <b>lower</b>
     */
    public int lowerOperation() ;

    /**
     * <b>upper</b>
     */
    public long upperOperation() ;

    /**
     * <b>lowerBound</b>
     */
    public int lowerBound() ;

    /**
     * <b>upperBound</b>
     */
    public long upperBound() ;

}

