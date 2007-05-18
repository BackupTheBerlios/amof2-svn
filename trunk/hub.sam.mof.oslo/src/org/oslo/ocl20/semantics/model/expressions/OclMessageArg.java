/**
 *
 *  Class OclMessageArg.java
 *
 *  Generated by KMFStudio at 09 May 2003 17:47:41
 *  Visit http://www.cs.ukc.ac.uk/kmf
 *
 */

package org.oslo.ocl20.semantics.model.expressions;

import org.oslo.ocl20.semantics.SemanticsElement;

public interface OclMessageArg
extends
    SemanticsElement
{

	/** Get the 'unspecified' from 'OclMessageArg' */
	public UnspecifiedValueExp getUnspecified();
	/** Set the 'unspecified' from 'OclMessageArg' */
	public void setUnspecified(UnspecifiedValueExp unspecified);

	/** Get the 'expression' from 'OclMessageArg' */
	public OclExpression getExpression();
	/** Set the 'expression' from 'OclMessageArg' */
	public void setExpression(OclExpression expression);

	/** Override the toString */
	public String toString();

	/** Clone the object */
	public Object clone();
}