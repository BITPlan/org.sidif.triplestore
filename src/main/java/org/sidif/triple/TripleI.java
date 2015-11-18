package org.sidif.triple;

public interface TripleI {

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