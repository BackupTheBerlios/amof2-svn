/**
 *
 *  Class ClassifierContextDecl.java
 *
 *  Generated by KMFStudio at 09 May 2003 17:48:56
 *  Visit http://www.cs.ukc.ac.uk/kmf
 *
 */

package org.oslo.ocl20.semantics.model.contexts;

import org.oslo.ocl20.semantics.SemanticsElement;

public interface ClassifierContextDecl
extends
    SemanticsElement,
    ContextDeclaration
{

	/** Get the 'referredClassifier' from 'ClassifierContextDecl' */
	public org.oslo.ocl20.semantics.bridge.Classifier getReferredClassifier();
	/** Set the 'referredClassifier' from 'ClassifierContextDecl' */
	public void setReferredClassifier(org.oslo.ocl20.semantics.bridge.Classifier referredClassifier);

	/** Override the toString */
	public String toString();

	/** Clone the object */
	public Object clone();
}