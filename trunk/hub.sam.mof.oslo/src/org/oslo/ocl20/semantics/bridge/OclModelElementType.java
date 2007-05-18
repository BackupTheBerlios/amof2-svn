/**
 *
 *  Class OclModelElementType.java
 *
 *  Generated by KMFStudio at 09 May 2003 17:49:05
 *  Visit http://www.cs.ukc.ac.uk/kmf
 *
 */

package org.oslo.ocl20.semantics.bridge;

import org.oslo.ocl20.semantics.SemanticsElement;

public interface OclModelElementType
extends
    SemanticsElement,
    org.oslo.ocl20.semantics.model.types.OclAnyType,
    DataType,
    Classifier,
    Namespace,
    ModelElement
{
	/** Override the toString */
	public String toString();

	/** Clone the object */
	public Object clone();
}
