/**
 *
 *  Class RealLiteralExp.java
 *
 *  Generated by KMFStudio at 09 May 2003 17:47:41
 *  Visit http://www.cs.ukc.ac.uk/kmf
 *
 */

package org.oslo.ocl20.semantics.model.expressions;

import org.oslo.ocl20.semantics.SemanticsElement;

public interface RealLiteralExp
extends
    SemanticsElement,
    NumericalLiteralExp,
    LiteralExp,
    OclExpression
{

	/** Get the 'realSymbol' from 'RealLiteralExp' */
	public Double getRealSymbol();
	/** Set the 'realSymbol' from 'RealLiteralExp' */
	public void setRealSymbol(Double realSymbol);

	/** Override the toString */
	public String toString();

	/** Clone the object */
	public Object clone();
}
