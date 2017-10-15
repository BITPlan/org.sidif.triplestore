/**
 *
 * This file is part of the https://github.com/BITPlan/org.sidif.triplestore open source project
 *
 * Copyright © 2015-2017 BITPlan GmbH https://github.com/BITPlan
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sidif.triple.impl.LinkedMultiValueMap;
import org.sidif.triple.impl.MultiValueMap;
import org.sidif.triple.impl.TripleImpl;
import org.sidif.triple.impl.TripleQueryImpl;

/**
 * as simple ObjectStore
 * 
 * @author wf
 *
 */
public class TripleStore {
  
  /**
   * return a default triple Query for all my triples
   * @return the default query
   */
  public TripleQueryImpl query() {
    return new TripleQueryImpl(this);
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
     * @return all the triples
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
        result.add(triple.getSubject());
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
        result.add(triple.getPredicate());
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
          result.add(triple.getObject());
        }
      }
      return result;
    }

    /**
     * get the keys
     * 
     * @return the set of keys
     */
    public Set<Object> getKeys() {
      Set<Object> result = this.tripleLookup.keySet();
      return result;
    }

    /**
     * get my size
     * 
     * @return the size
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
   * @return a sorted List
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
   * @return the set of subjects
   */
  public Set<Object> getSubjects() {
    Set<Object> subjects = this.bySubject.tripleLookup.keySet();
    return subjects;
  }

  /**
   * get the predicates of this tripleStore
   * 
   * @return the set of predicates
   */
  public Set<Object> getPredicates() {
    Set<Object> predicates = this.byPredicate.tripleLookup.keySet();
    return predicates;
  }

  /**
   * get the objects of this tripleStore
   * 
   * @return the set of objects
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
    TripleImpl triple = new TripleImpl(subject, predicate, object);
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
    if (bySubject.tripleLookup.containsKey(triple.getSubject())) {
      List<Triple> existingTriples = bySubject.tripleLookup.get(triple.getSubject());
      for (Triple etriple : existingTriples) {
        if (etriple.getPredicate().equals(triple.getPredicate())
            && etriple.getObject().equals(triple.getObject()))
          // don't add the triple - it exists!
          return;
      }
    }
    bySubject.add(triple.getSubject(), triple);
    byPredicate.add(triple.getPredicate(), triple);
    byObject.add(triple.getObject(), triple);
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
   * add the given select from the given source TripleStore to me
   * @param source - the tripleStore to select triples from
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
   * @return all my triples
   */
  public List<Triple> getTriples() {
    List<Triple> result = this.bySubject.getTriples();
    return result;
  }

  /**
   * get the size of this triple Store
   * 
   * @return my size
   */
  public int size() {
    return getTriples().size();
  }

}
