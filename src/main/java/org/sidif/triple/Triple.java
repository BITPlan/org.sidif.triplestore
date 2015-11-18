/**
 * Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 * 
 */
package org.sidif.triple;


/**
 * http://en.wikipedia.org/wiki/Triple states about a Triple
 * "The atomic data entity in the Resource Description Framework"
 * "as it is based upon the idea of making statements about resources 
 * (in particular web resources) in the form of subject–predicate–object expressions. "
 * 
 *
 */
public class Triple implements TripleI {
 
  public Object subject;
  public Object predicate;
  public Object object;
  
  /**
   * construct me from the three given subject, predicate and object Objects
   * @param subject
   * @param predicate
   * @param object
   */
  public Triple(Object subject, Object predicate, Object object) {
    super();
    this.subject = subject;
    this.predicate = predicate;
    this.object = object;
  }

  /* (non-Javadoc)
   * @see org.sidif.triple.TripleI#getSubject()
   */
  @Override
  public Object getSubject() {
    return subject;
  }

  /* (non-Javadoc)
   * @see org.sidif.triple.TripleI#setSubject(java.lang.Object)
   */
  @Override
  public void setSubject(Object subject) {
    this.subject = subject;
  }

  /* (non-Javadoc)
   * @see org.sidif.triple.TripleI#getPredicate()
   */
  @Override
  public Object getPredicate() {
    return predicate;
  }

  /* (non-Javadoc)
   * @see org.sidif.triple.TripleI#setPredicate(java.lang.Object)
   */
  @Override
  public void setPredicate(Object predicate) {
    this.predicate = predicate;
  }

  /* (non-Javadoc)
   * @see org.sidif.triple.TripleI#getObject()
   */
  @Override
  public Object getObject() {
    return object;
  }

  /* (non-Javadoc)
   * @see org.sidif.triple.TripleI#setObject(org.sidif.triple.Value)
   */
  @Override
  public void setObject(Value<Object> object) {
    this.object = object;
  }
 
  /* (non-Javadoc)
   * @see org.sidif.triple.TripleI#isLiteral()
   */
  @Override
  public boolean isLiteral() {
    boolean result = false;
    if (object instanceof Value) {
      @SuppressWarnings("unchecked")
      Value<Object>value=(Value<Object>) object;
      result=value.literal;
    }
    return result;
  }
  
  /**
   * check if the given object is a literal
   * @param object
   * @return whether the given object is a literal
   */
  static boolean isLiteral(Object object) {
    boolean result=object instanceof Integer
        || object instanceof Double 
        || object instanceof Character
        || object instanceof String
        || object instanceof java.util.Date
        || object instanceof java.sql.Time
        || object instanceof java.net.URI
        ;
        return result;
  }

  /* (non-Javadoc)
   * @see org.sidif.triple.TripleI#nullValue(java.lang.Object)
   */
  @Override
  public String nullValue(Object o) {
    if (o==null) 
      return "null";
    else
      return o.toString();
  }
  
  /* (non-Javadoc)
   * @see org.sidif.triple.TripleI#toString()
   */
  @Override
  public String toString() {
    String result=nullValue(subject)+" "+nullValue(predicate)+" "+nullValue(object);
    return result;
  }
  
}