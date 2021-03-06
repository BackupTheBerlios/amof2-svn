
package org.oslo.ocl20.syntax.ast.expressions;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class ImpliesExpASImpl

  extends
    
      LogicalExpASImpl
    
  
  implements ImpliesExpAS,
             expressionsVisitable
{
  //--- Constructors ---
  public ImpliesExpASImpl() {

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
  	factory.destroy("ImpliesExpAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.expressions.ImpliesExpAS",this);
  }


  //--- java.lang.Object ---

  static int _nextId=0;
   int _id=0;


  public java.lang.String toString() {
      return "ImpliesExpAS {"+

  _id+
"}";
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof ImpliesExpAS) {
      try {
        ImpliesExpAS obj = (ImpliesExpAS)o;
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
    ImpliesExpAS obj = new ImpliesExpASImpl();

    return obj;
  }
}



