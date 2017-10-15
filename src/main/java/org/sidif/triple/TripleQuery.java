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

import java.util.List;

public interface TripleQuery {

  /**
   * select the triples for the given subject, predicate and object
   * 
   * @param subject
   * @param predicate
   * @param object 
   * @return the selected list of triples
   */
  public abstract List<Triple> select(Object subject, Object predicate,
      Object object);

  /**
   * check whether two Objects are the same
   * 
   * @param object
   * @param other
   * @return whether the two objects are the same
   */
  public abstract boolean same(Object object, Object other);

  /**
   * create a TripleQuery for the given subject, predicate and object
   * 
   * @param subject
   * @param predicate
   * @param object
   * @return the query
   */
  public abstract TripleQuery query(Object subject, Object predicate,
      Object object);

  /**
   * select a single triple
   * @param subject
   * @param predicate
   * @param object
   * @return the triple
   */
  public abstract Triple selectSingle(Object subject, Object predicate,
      Object object);

  /**
   * select the list of Triples that "fit" the given example Triple
   * 
   * @param select
   *          - the Triple to use as a "query"
   * @return - the list of Objects fitting
   */
  public abstract TripleQuery queryTriple(Triple select);

  /**
   * create a union of me and the other Triple query
   * that is the list triples of me combined with the list of triples of the other triple query
   * see https://en.wikipedia.org/wiki/Union_%28set_theory%29
   * @param other
   * @return the union
   */
  public abstract TripleQuery union(TripleQuery other);

  /**
   * create an intersection of me and the other Triple query
   * that is the list of triples that are part of my triples and part ot the otherQueries triples
   * see https://en.wikipedia.org/wiki/Intersection_%28set_theory%29#Intersecting_and_disjoint_sets
   * @param otherQuery
   * @return the intersection
   */
  public abstract TripleQuery intersect(TripleQuery otherQuery);

  /**
   * create a relative complement that is the triples that are in me but not in otherQuery
   * see https://en.wikipedia.org/wiki/Complement_%28set_theory%29#Relative_complement
   * @param otherQuery
   * @return the complement
   */
  public abstract TripleQuery complement(TripleQuery otherQuery);

  /**
   * return the size of my triples 
   * @return my size
   */
  public abstract int size();

  /**
   * return my Triples
   * @return my triples
   */
  public abstract List<Triple> getTriples();

  /**
   * get the tripleStore this query belongs to
   * @return the tripleStore
   */
  public abstract TripleStore getTripleStore();

}