/* Generated By:JJTree: Do not edit this line. Value.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.sidif.parser.node;

import org.sidif.parser.jjtree.SiDIF;

public
class Value extends SimpleNode {
  private Literal literal;
  private String property;
  private String concept;
  /**
   * @return the literal
   */
  public Literal getLiteral() {
    return literal;
  }

  /**
   * @param literal the literal to set
   */
  public void setLiteral(Literal literal) {
    this.literal = literal;
  }

  /**
   * @return the property
   */
  public String getProperty() {
    return property;
  }

  /**
   * @param property the property to set
   */
  public void setProperty(String property) {
    this.property = property;
  }

  /**
   * @return the concept
   */
  public String getConcept() {
    return concept;
  }

  /**
   * @param concept the concept to set
   */
  public void setConcept(String concept) {
    this.concept = concept;
  }
  
  /**
   * set the Value for this literal
   * @param literal
   * @param property
   * @param concept
   */
  public void setValue(org.sidif.parser.node.Literal literal, String property,
      String concept) {
    this.literal=literal;
    this.property=property;
    this.concept=concept;
  }
  
  public Value(int id) {
    super(id);
  }

  public Value(SiDIF p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(SiDIFVisitor visitor, org.sidif.triple.impl.ObjectHolder data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=15682b8d77404bdb83432c17d252c179 (do not edit this line) */
