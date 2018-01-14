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

import org.sidif.triple.Triple;

/**
 * Handles "it" references
 * 
 * @author wf
 *
 */
public class Resolver {
  private Object currentSubject;

  /**
   * set the current subject
   * 
   * @param subject
   */
  public void setCurrentSubject(Object subject) {
    this.currentSubject = subject;
  }

  /**
   * resolve the subject
   * 
   * @param subject
   *          - if it is "it" then return the name of the current subject
   * @return - the resolved subject
   */
  public Object resolve(Object subject) {
    // resolve "it" references
    if (subject.equals("it")) {
      subject = currentSubject;
    }
    return subject;
  }

  /**
   * create a triple from the given subject, predicate and object
   * @param subject
   * @param predicate
   * @param object
   * @return - the new triple
   */
  public Triple createTriple(Object subject, Object predicate, Object object) {
    subject=resolve(subject);
    object=resolve(object);
    org.sidif.triple.Value<Object> value = new org.sidif.triple.Value<Object>();
    value.setObject(object);
    value.literal = false;
    value.type = "Reference";
    Triple triple = new TripleImpl(subject, predicate, value);

    if (predicate.equals("isA")) {
      setCurrentSubject(subject);
    }
    return triple;
  }
}
