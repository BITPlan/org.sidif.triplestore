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

import java.util.ArrayList;
import java.util.List;

import org.sidif.triple.Triple;
import org.sidif.triple.TripleQuery;
import org.sidif.triple.TripleStore;
import org.sidif.triple.TripleStore.TripleContainer;

/**
 * TripleQuery
 * 
 * @author wf
 *
 */
public class TripleQueryImpl implements TripleQuery {
  public static enum TopicType {
    subject, predicate, object
  };

  TripleStore tripleStore;
  public Triple triple;
  TopicType topicType;
  protected  List<Triple> triples;

  /**
   * create a TripleQuery from the given tripleStore
   * @param tripleStore
   */
  public TripleQueryImpl(TripleStore tripleStore) {
    this.tripleStore=tripleStore;
    this.topicType=TopicType.subject;
    setTriples(tripleStore.getTriples());
  }
  
  /**
   * create a new TripleQuery based on an existing one
   * @param tripleQuery
   */
  public TripleQueryImpl(TripleQueryImpl tripleQuery) {
    this.tripleStore=tripleQuery.tripleStore;
    this.topicType=tripleQuery.topicType;
    this.triples=tripleQuery.triples;
    this.triple=tripleQuery.triple;
  }

  /**
   * change the query topic
   * @param topicType
   * @return the query
   */
  public TripleQueryImpl queryTopic(TopicType topicType) {
    TripleQueryImpl result = new TripleQueryImpl(this);
    result.topicType=topicType; 
    return result;
  }
  
  /**
   * get the TripleContainer
   * @return the tripleContainer of the current topic type
   */
  public TripleContainer getTripleContainer() {
    TripleContainer result;
    switch (topicType) {
    case subject:
      result = tripleStore.bySubject;
      break;
    case predicate:
      result = tripleStore.byPredicate;
      break;
    case object:
      result = tripleStore.bySubject;
      break;
    default:
      throw new IllegalArgumentException("unknown topic Type: "+topicType);
    }
    return result;
  }
  
  /**
   * get the Topic for the current topic type subject predicate or object
   * @return the object
   */
  public Object getTopic() {
    Object topic=null;
    if (triple!=null) {
      switch (topicType) {
      case subject:
        topic=triple.getSubject();
        break;
      case predicate:
        topic=triple.getPredicate();
        break;
      case object:
        topic=triple.getObject();
        break;
      }
    }
    return topic;
  }
  
  /* (non-Javadoc)
   * @see org.sidif.triple.TripleQueryI#select(java.lang.Object, java.lang.Object, java.lang.Object)
   */
  @Override
  public List<Triple> select(Object subject, Object predicate, Object object) {
    TripleQueryImpl tripleQuery = this.query(subject,
        predicate, object);
    List<Triple> result = tripleQuery.triples;
    return result;
  }
  
  /**
   * select the triples for the given "template" triple
   * 
   * @param select
   * @return the selected list of triples
   */
  public List<Triple> select(Triple select) {
    TripleQueryImpl tripleQuery = this.queryTriple(select);
    List<Triple> result = tripleQuery.triples;
    return result;
  }
  
  /* (non-Javadoc)
   * @see org.sidif.triple.TripleQueryI#same(java.lang.Object, java.lang.Object)
   */
  @Override
  public boolean same(Object object, Object other) {
    boolean result = false;
    if ((object == null) && (other == null))
      result = true;
    if ((object != null) && (other != null))
      result = object.equals(other);
    return result;
  }
  
  /* (non-Javadoc)
   * @see org.sidif.triple.TripleQueryI#query(java.lang.Object, java.lang.Object, java.lang.Object)
   */
  @Override
  public TripleQueryImpl query(Object subject,
      Object predicate, Object object) {
    TripleImpl select = new TripleImpl(subject, predicate, object);
    TripleQueryImpl result = queryTriple(select);
    return result;
  }

  /* (non-Javadoc)
   * @see org.sidif.triple.TripleQueryI#selectSingle(java.lang.Object, java.lang.Object, java.lang.Object)
   */
  @Override
  public Triple selectSingle(Object subject, Object predicate, Object object) {
    List<Triple> triples = this.select(subject, predicate, object);
    if (triples.size() > 1) {
      String msg="selectSingle subject='"+subject
          +"' predicate='"+predicate
          +"' object='"+object+"' returned "+triples.size()+" triples but there should be only one!";
      // we'll show the first (upto) 10 culprits
      for (int i=1;i<=Math.min(triples.size(), 10);i++) {
        Triple triple=triples.get(i-1);
        msg+="\n\t"+i+":"+triple.toString();
      }
      throw new IllegalStateException(msg);
    } else if (triples.size()==0) {
      return null;
    }
    return triples.get(0);
  }

  /* (non-Javadoc)
   * @see org.sidif.triple.TripleQueryI#queryTriple(org.sidif.triple.Triple)
   */
  @Override
  public TripleQueryImpl queryTriple(Triple select) {
    TripleQueryImpl result=new TripleQueryImpl(this);
    result.triples = new ArrayList<Triple>();
    // FIXME - this is inefficient
    for (Triple triple : this.getTriples()) {
      boolean subjectOk = select.getSubject() == null
          || same(triple.getSubject(), select.getSubject());
      boolean predicateOk = select.getPredicate() == null
          || same(triple.getPredicate(), select.getPredicate());
      boolean objectOk = select.getObject() == null
          || same(triple.getObject(), select.getObject());
      if (subjectOk && predicateOk && objectOk) {
        result.add(triple);
      }
    }
    result.setTriple();
    return result;
  }
  
  /**
   * add the given Triple to my triples
   * @param triple
   */
  private void add(Triple triple) {
    if (!triples.contains(triple)) {
      triples.add(triple);
    }
  }

  /**
   * set my triples
   * @param pTriples - the triples to use
   */
  public void setTriples(List<Triple> pTriples) {
    triples=pTriples;
    setTriple();
  }
  
  /**
   * set my triple (head triple pointer ...)
   */
  public void setTriple() {
    if (triples.size()>0)
      triple=triples.get(0);
    else
      triple=null;  
  }
  
  /* (non-Javadoc)
   * @see org.sidif.triple.TripleQueryI#union(org.sidif.triple.TripleQueryImpl)
   */
  @Override
  public TripleQuery union(TripleQuery other) {
    TripleQueryImpl result=new TripleQueryImpl(this);
    List<Triple> triples = new ArrayList<Triple>();
    triples.addAll(this.getTriples());
    triples.addAll(other.getTriples());
    result.setTriples(triples);
    return result;
  }
  
  /* (non-Javadoc)
   * @see org.sidif.triple.TripleQueryI#intersect(org.sidif.triple.TripleQueryI)
   */
  @Override
  public TripleQuery intersect(TripleQuery otherQuery) {
    TripleQueryImpl result=new TripleQueryImpl(this);
    result.triples=new ArrayList<Triple>();
    // FIXME this is ineffecient quadratic O(this.size x otherQuery.size) ...
    for (Triple triple:this.getTriples()) {
      for (Triple otherTriple:otherQuery.getTriples()) {
        if (same(triple,otherTriple)) {
          result.add(triple);
        }
      }
    }
    result.setTriple();
    return result;
  }
  
  /* (non-Javadoc)
   * @see org.sidif.triple.TripleQueryI#complement(org.sidif.triple.TripleQueryI)
   */
  @Override
  public TripleQuery complement(TripleQuery otherQuery) {
    TripleQueryImpl result=new TripleQueryImpl(this);
    result.triples=new ArrayList<Triple>();
    // FIXME this is ineffecient quadratic O(this.size x otherQuery.size) ...
    for (Triple triple:this.getTriples()) {
      for (Triple otherTriple:otherQuery.getTriples()) {
        if (!same(triple,otherTriple)) {
          result.add(triple);
        }
      }
    }
    result.setTriple();
    return result;
  }

  /* (non-Javadoc)
   * @see org.sidif.triple.TripleQueryI#size()
   */
  @Override
  public int size() {
    int result=this.triples.size();
    return result;
  }

  /* (non-Javadoc)
   * @see org.sidif.triple.TripleQueryI#getTriples()
   */
  @Override
  public List<Triple> getTriples() {
    return triples;
  }

  @Override
  public TripleStore getTripleStore() {
    return this.tripleStore;
  }

}
