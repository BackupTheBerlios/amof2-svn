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

package core.abstractions.multiplicities;


/**
 * <b>MultiplicityElement</b>, isAbstract, superClass = {core.abstractions.elements.Element}
 * <br>constraint - upper_gt_0 : 
 * <pre>upperBound()->notEmpty() implies upperBound() > 0</pre>
 * <br>constraint - lower_ge_0 : 
 * <pre>lowerBound()->notEmpty() implies lowerBound() >= 0</pre>
 * <br>constraint - upper_ge_lower : 
 * <pre>(upperBound()->notEmpty() and lowerBound()->notEmpty()) implies upperBound() >= lowerBound()</pre>
 */
public interface MultiplicityElement extends core.abstractions.elements.Element
{

    /**
     * <b>isOrdered</b>, multiplicity=(1,1)
     */
    public boolean isOrdered();

    public void setIsOrdered(boolean value);

    /**
     * <b>isUnique</b>, multiplicity=(1,1)
     */
    public boolean isUnique();

    public void setIsUnique(boolean value);

    /**
     * <b>lower</b>, multiplicity=(0,1)
     */
    public int getLower();

    public void setLower(int value);

    /**
     * <b>upper</b>, multiplicity=(0,1)
     */
    public long getUpper();

    public void setUpper(long value);

    /**
     * <b>lowerBound</b>
     */
    public int lowerBound() ;

    /**
     * <b>upperBound</b>
     */
    public long upperBound() ;

    /**
     * <b>isMultivalued</b>
     */
    public boolean isMultivalued() ;

    /**
     * <b>includesCardinality</b>
     */
    public boolean includesCardinality(int C) ;

    /**
     * <b>includesMultiplicity</b>
     */
    public boolean includesMultiplicity(core.abstractions.multiplicities.MultiplicityElement M) ;

}

