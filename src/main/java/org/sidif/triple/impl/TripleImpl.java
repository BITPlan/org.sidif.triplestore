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
import org.sidif.triple.Value;

/**
 * simple Triple holder
 * @author wf
 *
 */
public class TripleImpl implements Triple {
 
  public Object subject;
  public Object predicate;
  public Object object;
  
  /**
   * construct me from the three given subject, predicate and object Objects
   * @param subject
   * @param predicate
   * @param object
   */
  public TripleImpl(Object subject, Object predicate, Object object) {
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
    String result=String.format("%s %s %s",nullValue(subject),nullValue(predicate),nullValue(object));
    return result;
  }
  
  public String asDebug(Object o) {
    if (o==null) 
      return "null";
    else {
      return o.toString()+"("+o.getClass().getName()+")";
    }
  }
  
  public String asDebug() {
    return String.format("%s %s %s", asDebug(subject),asDebug(predicate),asDebug(object));
  }

  @Override
  public boolean sameAs(Triple other) {
    String dme=this.asDebug();
    TripleImpl otheri = (TripleImpl) other;
    String dother=otheri.asDebug();
    return dme.equals(dother);
  }
  
}