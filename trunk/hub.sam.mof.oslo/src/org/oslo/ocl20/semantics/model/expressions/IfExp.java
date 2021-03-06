/**
 *
 *  Class IfExp.java
 *
 *  Generated by KMFStudio at 09 May 2003 17:47:42
 *  Visit http://www.cs.ukc.ac.uk/kmf
 *
 */

package org.oslo.ocl20.semantics.model.expressions;

import org.oslo.ocl20.semantics.SemanticsElement;

public interface IfExp
extends
    SemanticsElement,
    OclExpression
{

	/** Get the 'condition' from 'IfExp' */
	public OclExpression getCondition();
	/** Set the 'condition' from 'IfExp' */
	public void setCondition(OclExpression condition);

	/** Get the 'thenExpression' from 'IfExp' */
	public OclExpression getThenExpression();
	/** Set the 'thenExpression' from 'IfExp' */
	public void setThenExpression(OclExpression thenExpression);

	/** Get the 'elseExpression' from 'IfExp' */
	public OclExpression getElseExpression();
	/** Set the 'elseExpression' from 'IfExp' */
	public void setElseExpression(OclExpression elseExpression);

	/** Override the toString */
	public String toString();

	/** Clone the object */
	public Object clone();
}
