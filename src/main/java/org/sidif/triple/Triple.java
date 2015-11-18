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
 * this is a more generic Triple concept that does not dependend on the technical details
 * of RDF
 */
public interface Triple {

  /**
   * @return the subject
   */
  public abstract Object getSubject();

  /**
   * @param subject the subject to set
   */
  public abstract void setSubject(Object subject);

  /**
   * @return the predicate
   */
  public abstract Object getPredicate();

  /**
   * @param predicate the predicate to set
   */
  public abstract void setPredicate(Object predicate);

  /**
   * @return the object
   */
  public abstract Object getObject();

  /**
   * @param object the object to set
   */
  public abstract void setObject(Value<Object> object);

  /**
   * is this Triple representing a literal?
   * @return true or false
   */
  public abstract boolean isLiteral();

  /**
   * get a NullValue representation
   * @param o
   * @return the nullValue
   */
  public abstract String nullValue(Object o);

  /**
   * return a string representation of me
   * @return the string representation
   */
  public abstract String toString();

}