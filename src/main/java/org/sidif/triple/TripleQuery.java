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

import java.util.ArrayList;
import java.util.List;

import org.sidif.triple.TripleStore.TripleContainer;

/**
 * TripleQuery
 * 
 * @author wf
 *
 */
public class TripleQuery {
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
  public TripleQuery(TripleStore tripleStore) {
    this.tripleStore=tripleStore;
    this.topicType=TopicType.subject;
    setTriples(tripleStore.getTriples());
  }
  
  /**
   * create a new TripleQuery based on an existing one
   * @param tripleQuery
   */
  public TripleQuery(TripleQuery tripleQuery) {
    this.tripleStore=tripleQuery.tripleStore;
    this.topicType=tripleQuery.topicType;
    this.triples=tripleQuery.triples;
    this.triple=tripleQuery.triple;
  }

  /**
   * 
   * @param topicType
   * @return
   */
  public TripleQuery queryTopic(TopicType topicType) {
    TripleQuery result = new TripleQuery(this);
    result.topicType=topicType; 
    return result;
  }
  
  /**
   * get the TripleContainer
   * @return
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
   * get the Topic
   * @return
   */
  public Object getTopic() {
    Object topic=null;
    if (triple!=null) {
      switch (topicType) {
      case subject:
        topic=triple.subject;
        break;
      case predicate:
        topic=triple.predicate;
        break;
      case object:
        topic=triple.object;
        break;
      }
    }
    return topic;
  }
  
  /**
   * select the triples for the given subject, predicate and object
   * 
   * @param subject
   * @param predicate
   * @param object
   * @return
   */
  public List<Triple> select(Object subject, Object predicate, Object object) {
    TripleQuery tripleQuery = this.query(subject,
        predicate, object);
    List<Triple> result = tripleQuery.triples;
    return result;
  }
  
  /**
   * select the triples for the given "template" triple
   * 
   * @param select
   * @return
   */
  public List<Triple> select(Triple select) {
    TripleQuery tripleQuery = this.queryTriple(select);
    List<Triple> result = tripleQuery.triples;
    return result;
  }
  
  /**
   * check whether two Objects are the same
   * 
   * @param object
   * @param other
   * @return whether the two objects are the same
   */
  public boolean same(Object object, Object other) {
    boolean result = false;
    if ((object == null) && (other == null))
      result = true;
    if ((object != null) && (other != null))
      result = object.equals(other);
    return result;
  }
  
  /**
   * create a TripleQuery for the given subject, predicate and object
   * 
   * @param subject
   * @param predicate
   * @param object
   * @return
   */
  public TripleQuery query(Object subject,
      Object predicate, Object object) {
    Triple select = new Triple(subject, predicate, object);
    TripleQuery result = queryTriple(select);
    return result;
  }

  /**
   * select a single triple
   * @param subject
   * @param predicate
   * @param object
   * @return
   */
  public Triple selectSingle(Object subject, Object predicate, Object object) {
    List<Triple> triples = this.select(subject, predicate, object);
    if (triples.size() > 1) {
      String msg="selectSingle subject='"+subject
          +"' predicate='"+predicate
          +"' object='"+object+"' returned "+triples.size()+" triples but there should be only one!";
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

  /**
   * select the list of Triples that "fit" the given example Triple
   * 
   * @param select
   *          - the Triple to use as a "query"
   * @return - the list of Objects fitting
   */
  public TripleQuery queryTriple(Triple select) {
    TripleQuery result=new TripleQuery(this);
    result.triples = new ArrayList<Triple>();
    // FIXME - this is inefficient
    for (Triple triple : this.getTriples()) {
      boolean subjectOk = select.subject == null
          || same(triple.subject, select.subject);
      boolean predicateOk = select.predicate == null
          || same(triple.predicate, select.predicate);
      boolean objectOk = select.object == null
          || same(triple.object, select.object);
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
   * @param pTriple - the triples to use
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
  
  /**
   * create a union of me and the other Triple query
   * that is the list triples of me combined with the list of triples of the other triple query
   * see https://en.wikipedia.org/wiki/Union_%28set_theory%29
   * @param other
   * @return
   */
  public TripleQuery union(TripleQuery other) {
    TripleQuery result=new TripleQuery(this);
    List<Triple> triples = new ArrayList<Triple>();
    triples.addAll(this.triples);
    triples.addAll(other.triples);
    result.setTriples(triples);
    return result;
  }
  
  /**
   * create an intersection of me and the other Triple query
   * that is the list of triples that are part of my triples and part ot the otherQueries triples
   * see https://en.wikipedia.org/wiki/Intersection_%28set_theory%29#Intersecting_and_disjoint_sets
   * @param otherQuery
   * @return
   */
  public TripleQuery intersect(TripleQuery otherQuery) {
    TripleQuery result=new TripleQuery(this);
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
  
  /**
   * create a relative complement that is the triples that are in me but not in otherQuery
   * see https://en.wikipedia.org/wiki/Complement_%28set_theory%29#Relative_complement
   * @param otherQuery
   * @return
   */
  public TripleQuery complement(TripleQuery otherQuery) {
    TripleQuery result=new TripleQuery(this);
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

  /**
   * return the size of my triples 
   * @return
   */
  public int size() {
    int result=this.triples.size();
    return result;
  }

  /**
   * return my Triples
   * @return
   */
  public List<Triple> getTriples() {
    return triples;
  }

}
