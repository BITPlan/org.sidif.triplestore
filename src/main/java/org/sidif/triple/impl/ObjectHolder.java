/**
 *
 * This file is part of the https://github.com/BITPlan/org.sidif.triplestore open source project
 *
 * Copyright © 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *  You may obtain a copy of the License at
 *
 *  http:www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sidif.triple.impl;

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
   * @param other - the objectHolder to compare me with
   * @return whether this ObjectHolder is equal to the other
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
