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
public class Triple {
 
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

  /**
   * @return the subject
   */
  public Object getSubject() {
    return subject;
  }

  /**
   * @param subject the subject to set
   */
  public void setSubject(Object subject) {
    this.subject = subject;
  }

  /**
   * @return the predicate
   */
  public Object getPredicate() {
    return predicate;
  }

  /**
   * @param predicate the predicate to set
   */
  public void setPredicate(Object predicate) {
    this.predicate = predicate;
  }

  /**
   * @return the object
   */
  public Object getObject() {
    return object;
  }

  /**
   * @param object the object to set
   */
  public void setObject(Value<Object> object) {
    this.object = object;
  }
 
  /**
   * is this Triple representing a literal?
   * @return true or false
   */
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
   * @return
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

  /**
   * get a NullValue representation
   * @param o
   * @return the nullValue
   */
  public String nullValue(Object o) {
    if (o==null) 
      return "null";
    else
      return o.toString();
  }
  
  /**
   * return a string representation of me
   * @return the string representation
   */
  public String toString() {
    String result=nullValue(subject)+" "+nullValue(predicate)+" "+nullValue(object);
    return result;
  }
  
}