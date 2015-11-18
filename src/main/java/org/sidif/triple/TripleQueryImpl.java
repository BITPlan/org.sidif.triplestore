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
public class TripleQueryImpl implements TripleQuery {
  public static enum TopicType {
    subject, predicate, object
  };

  TripleStore tripleStore;
  public TripleI triple;
  TopicType topicType;
  protected  List<TripleI> triples;

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
  public List<TripleI> select(Object subject, Object predicate, Object object) {
    TripleQueryImpl tripleQuery = this.query(subject,
        predicate, object);
    List<TripleI> result = tripleQuery.triples;
    return result;
  }
  
  /**
   * select the triples for the given "template" triple
   * 
   * @param select
   * @return the selected list of triples
   */
  public List<TripleI> select(TripleI select) {
    TripleQueryImpl tripleQuery = this.queryTriple(select);
    List<TripleI> result = tripleQuery.triples;
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
    Triple select = new Triple(subject, predicate, object);
    TripleQueryImpl result = queryTriple(select);
    return result;
  }

  /* (non-Javadoc)
   * @see org.sidif.triple.TripleQueryI#selectSingle(java.lang.Object, java.lang.Object, java.lang.Object)
   */
  @Override
  public TripleI selectSingle(Object subject, Object predicate, Object object) {
    List<TripleI> triples = this.select(subject, predicate, object);
    if (triples.size() > 1) {
      String msg="selectSingle subject='"+subject
          +"' predicate='"+predicate
          +"' object='"+object+"' returned "+triples.size()+" triples but there should be only one!";
      for (int i=1;i<=Math.min(triples.size(), 10);i++) {
        TripleI triple=triples.get(i-1);
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
  public TripleQueryImpl queryTriple(TripleI select) {
    TripleQueryImpl result=new TripleQueryImpl(this);
    result.triples = new ArrayList<TripleI>();
    // FIXME - this is inefficient
    for (TripleI triple : this.getTriples()) {
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
  private void add(TripleI triple) {
    if (!triples.contains(triple)) {
      triples.add(triple);
    }
  }

  /**
   * set my triples
   * @param pTriples - the triples to use
   */
  public void setTriples(List<TripleI> pTriples) {
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
    List<TripleI> triples = new ArrayList<TripleI>();
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
    result.triples=new ArrayList<TripleI>();
    // FIXME this is ineffecient quadratic O(this.size x otherQuery.size) ...
    for (TripleI triple:this.getTriples()) {
      for (TripleI otherTriple:otherQuery.getTriples()) {
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
    result.triples=new ArrayList<TripleI>();
    // FIXME this is ineffecient quadratic O(this.size x otherQuery.size) ...
    for (TripleI triple:this.getTriples()) {
      for (TripleI otherTriple:otherQuery.getTriples()) {
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
  public List<TripleI> getTriples() {
    return triples;
  }

}
