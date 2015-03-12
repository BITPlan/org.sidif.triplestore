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

import java.util.logging.Logger;

/**
 * an object holder to wrap native Java Objects as Concepts
 * 
 * @author wf
 *
 * @param <T>
 */
public class ObjectHolder<T> {
  protected static Logger LOGGER = Logger.getLogger("org.sidif.triple");

  private T object;

  /**
   * @return the object
   */
  public T getObject() {
    return object;
  }

  /**
   * @param object
   *          the object to set
   */
  public void setObject(T object) {
    // LOGGER.log(Level.INFO,"setting "+object.getClass().getName()+"="+object.toString());
    this.object = object;
  }

  /**
   * to String
   */
  public String toString() {
    String result = "null";
    if (this.object != null) {
      result = this.object.toString();
    }
    return result;
  }

  /**
   * equality ...
   * 
   * @param other
   * @return
   */
  public boolean equals(Object other) {
    boolean result = false;
    if (other instanceof ObjectHolder) {
      @SuppressWarnings("unchecked")
      ObjectHolder<T> otherHolder=(ObjectHolder<T>) other;
      if (this.object == null && otherHolder.getObject() == null) {
        result = true;
      } else if (this.object != null && otherHolder.getObject() != null) {
        result = this.object.equals(otherHolder.getObject());
      }
    } else {
      if (this.object == null && other == null) {
        result = true;
      } else if (this.object != null && other!= null) {
        result = this.object.equals(other);
      }
      
    }
    return result;
  }
  
  /**
   * make sure we provide the same hash as the object we are pointing to 
   */
  @Override
  public int hashCode() {
    if (this.object!=null) {
      return this.object.hashCode();
    } else {
      return super.hashCode();
    }
  }
}
