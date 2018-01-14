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
package org.sidif.triple;
/**
 * http://en.wikipedia.org/wiki/Triple states about a Triple
 * "The atomic data entity in the Resource Description Framework"
 * "as it is based upon the idea of making statements about resources 
 * (in particular web resources) in the form of subject–predicate–object expressions. "
 * 
 * this is a more generic Triple concept that does not depend on the technical details
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

  /**
   * check that the triples are the same
   * @param other
   * @return
   */
  public abstract boolean sameAs(Triple other);

}