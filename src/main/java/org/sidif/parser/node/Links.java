/* Generated By:JJTree: Do not edit this line. Links.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.sidif.parser.node;

import org.sidif.parser.jjtree.*;

public
class Links extends SimpleNode {
  public Links(int id) {
    super(id);
  }

  public Links(SiDIF p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(SiDIFVisitor visitor, org.sidif.triple.impl.ObjectHolder data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=3b362b171e0b6b57c96dc3617dc26c3c (do not edit this line) */
