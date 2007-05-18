
package org.oslo.ocl20.syntax.ast.expressions;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class XorExpASImpl

  extends
    
      LogicalExpASImpl
    
  
  implements XorExpAS,
             expressionsVisitable
{
  //--- Constructors ---
  public XorExpASImpl() {

    _id=_nextId++;

  }










  //--- Visitable ---
  public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
    if (visitor instanceof expressionsVisitor)
      return ((expressionsVisitor)visitor).visit(this,data);
    else
      return visitor.visit(this,data);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Factory factory) {
  	factory.destroy("XorExpAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.expressions.XorExpAS",this);
  }


  //--- java.lang.Object ---

  static int _nextId=0;
   int _id=0;


  public java.lang.String toString() {
      return "XorExpAS {"+

  _id+
"}";
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof XorExpAS) {
      try {
        XorExpAS obj = (XorExpAS)o;
        return (this == o);
      } catch (NullPointerException e) {
        return this == o;
      }
    }
    return false;
  }

  public int hashCode() {
      return 0;
  }

  public java.lang.Object clone() {
    XorExpAS obj = new XorExpASImpl();

    return obj;
  }
}



