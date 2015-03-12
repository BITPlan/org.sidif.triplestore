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
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * as simple ObjectStore
 * 
 * @author wf
 *
 */
public class TripleStore {
  
  /**
   * return a triple Query
   * @param topicType
   * @return
   */
  public TripleQuery query() {
    return new TripleQuery(this);
  }
  
  /**
   * a triple Container
   * 
   * @author wf
   *
   */
  public static class TripleContainer {
    public MultiValueMap<Object, Triple> tripleLookup = new LinkedMultiValueMap<Object, Triple>();

    /**
     * @return the tripleLookup
     */
    public MultiValueMap<Object, Triple> getTripleLookup() {
      return tripleLookup;
    }

    /**
     * add the given triple with the given key
     * 
     * @param key
     * @param triple
     */
    public void add(Object key, Triple triple) {
      tripleLookup.add(key, triple);
    }

    /**
     * get the Triples
     * 
     * @return
     */
    public List<Triple> getTriples() {
      List<Triple> result = new ArrayList<Triple>();
      Collection<List<Triple>> tripleLists = tripleLookup.values();
      for (List<Triple> tripleList : tripleLists) {
        for (Triple triple : tripleList) {
          result.add(triple);
        }
      }
      return result;
    }

    /**
     * get the triples for a given key
     * 
     * @param key
     * @return the triples for that key
     */
    public List<Triple> getTriples(Object key) {
      List<Triple> triples = tripleLookup.get(key);
      if (triples == null)
        triples = new ArrayList<Triple>();
      return triples;
    }

    /**
     * get the set of subjects for a given key
     * 
     * @param key
     * @return - the set of unique objects for that key
     */
    public Set<Object> getSubjectSet(Object key) {
      List<Triple> triples = getTriples(key);
      Set<Object> result = new HashSet<Object>();
      for (Triple triple : triples) {
        result.add(triple.subject);
      }
      return result;
    }

    /**
     * get the set of predicates for a given key
     * 
     * @param key
     * @return - the set of unique objects for that key
     */
    public Set<Object> getPredicateSet(Object key) {
      List<Triple> triples = getTriples(key);
      Set<Object> result = new HashSet<Object>();
      for (Triple triple : triples) {
        result.add(triple.predicate);
      }
      return result;
    }

    /**
     * get the set of objects for a given key
     * 
     * @param key
     * @return - the set of unique objects for that key
     */
    public Set<Object> getObjectSet(Object key) {
      List<Triple> triples = getTriples(key);
      Set<Object> result = new HashSet<Object>();
      if (triples != null) {
        for (Triple triple : triples) {
          result.add(triple.object);
        }
      }
      return result;
    }

    /**
     * get the keys
     * 
     * @return
     */
    public Set<Object> getKeys() {
      Set<Object> result = this.tripleLookup.keySet();
      return result;
    }

    /**
     * get my size
     * 
     * @return
     */
    public int size() {
      int result = tripleLookup.size();
      return result;
    }
  }

  public TripleContainer bySubject = new TripleContainer();
  public TripleContainer byPredicate = new TripleContainer();
  public TripleContainer byObject = new TripleContainer();

  /**
   * @return the bySubject
   */
  public TripleContainer getBySubject() {
    return bySubject;
  }

  /**
   * @return the byPredicate
   */
  public TripleContainer getByPredicate() {
    return byPredicate;
  }

  /**
   * @return the byObject
   */
  public TripleContainer getByObject() {
    return byObject;
  }

  /**
   * utility function
   * 
   * @param c
   * @return
   */
  public static <T extends Comparable<? super T>> List<T> asSortedList(
      Collection<T> c) {
    List<T> list = new ArrayList<T>(c);
    java.util.Collections.sort(list);
    return list;
  }

  /**
   * get the subjects of this tripleStore
   * 
   * @return
   */
  public Set<Object> getSubjects() {
    Set<Object> subjects = this.bySubject.tripleLookup.keySet();
    return subjects;
  }

  /**
   * get the predicates of this tripleStore
   * 
   * @return
   */
  public Set<Object> getPredicates() {
    Set<Object> predicates = this.byPredicate.tripleLookup.keySet();
    return predicates;
  }

  /**
   * get the objects of this tripleStore
   * 
   * @return
   */
  public Set<Object> getObjects() {
    Set<Object> objects = this.byObject.tripleLookup.keySet();
    return objects;
  }

  /**
   * add the given subject, predicate, object triple
   * 
   * @param subject
   * @param predicate
   * @param object
   */
  public Triple add(Object subject, Object predicate, Object object) {
    Triple triple = new Triple(subject, predicate, object);
    add(triple);
    return triple;
  }

  /**
   * add the given triple
   * 
   * @param triple
   */
  public void add(Triple triple) {
    // FIXME null value handling ...
    if (bySubject.tripleLookup.containsKey(triple.subject)) {
      List<Triple> existingTriples = bySubject.tripleLookup.get(triple.subject);
      for (Triple etriple : existingTriples) {
        if (etriple.predicate.equals(triple.predicate)
            && etriple.object.equals(triple.object))
          // don't add the triple - it exists!
          return;
      }
    }
    bySubject.add(triple.subject, triple);
    byPredicate.add(triple.predicate, triple);
    byObject.add(triple.object, triple);
  }

  /**
   * add the triples from the given tripleContainer
   * 
   * @param tripleContainer
   */
  public void add(TripleContainer tripleContainer) {
    for (Triple triple : tripleContainer.getTriples()) {
      add(triple);
    }
  }

  /**
   * add the given select to the target TripleStore
   * 
   * @param target
   * @param source
   * @param subject
   * @param predicate
   * @param object
   */
  public void addSelect(TripleStore source, Object subject, Object predicate,
      Object object) {
    TripleQuery tripleQuery = source.query().query(subject,
        predicate, object);
    for (Triple triple:tripleQuery.getTriples()) {
      add(triple);
    }
  }
  
  /**
   * get the list of all myTriples
   * 
   * @return
   */
  public List<Triple> getTriples() {
    List<Triple> result = this.bySubject.getTriples();
    return result;
  }

  /**
   * get the size of this triple Store
   * 
   * @return
   */
  public int size() {
    return getTriples().size();
  }

}
