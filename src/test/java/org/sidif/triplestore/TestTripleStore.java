/**
 * Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 * 
 */
package org.sidif.triplestore;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.sidif.triple.Triple;
import org.sidif.triple.TripleQuery;
import org.sidif.triple.TripleQuery.TopicType;
import org.sidif.triple.TripleStore;
import org.sidif.util.TripleStoreDumper;

/**
 * test the simple TripleStore
 * 
 * @author wf
 *
 */
public class TestTripleStore extends BaseSiDIFTest {

  @Test
  public void testMultiMap() {
    TripleStore tripleStore = new TripleStore();
    Triple triple1 = new Triple("s", "p", "o");
    Triple triple2 = new Triple("s", "p", "o");
    ;
    Triple triple3 = new Triple("s", "p3", "o3");
    ;
    tripleStore.add(triple1);
    tripleStore.add(triple2);
    tripleStore.add(triple3);
    // debug=true;
    if (debug) {
      TripleStoreDumper.dump(tripleStore);
      System.out.println(tripleStore.size());
      System.out.println(tripleStore.bySubject.size());
      System.out.println(tripleStore.byPredicate.size());
      System.out.println(tripleStore.byObject.size());
    }
    assertEquals(2, tripleStore.size());
    assertEquals(1, tripleStore.bySubject.size());
    assertEquals(2, tripleStore.byPredicate.size());
    assertEquals(2, tripleStore.byObject.size());
    assertEquals(2, tripleStore.bySubject.getTriples("s").size());
    assertEquals(0, tripleStore.bySubject.getTriples("undefined").size());
  }

  @Test
  public void testSelect() throws Exception {
    TripleStore tripleStore = super.getTripleStoreFromExample("triple1");
    // debug=true;
    if (debug) {
      TripleStoreDumper.dump(tripleStore);
    }
    List<Triple> select1 = tripleStore.query().select("s", null, null);
    assertEquals(4, select1.size());
    Set<Object> os1 = tripleStore.byPredicate.getObjectSet("p1");
    if (debug) {
      for (Object o : os1) {
        System.out.println(o.toString());
      }
    }
    assertEquals(3, os1.size());
  }

  /**
   * test query
   * 
   * @throws Exception
   */
  @Test
  public void testQuery() throws Exception {
    TripleStore tripleStore = getTripleStoreFromExample("triple1");
    TripleQuery query = tripleStore.query();
    // debug=true;
    if (debug) {
      TripleStoreDumper.dump(tripleStore);
    }
    TripleQuery query1 = query.query("s", null, null);
    if (debug) {
      System.out.println("query1");
      TripleStoreDumper.dump(query1, "");
    }
    assertNotNull(query.triple);
    assertEquals("s", query.queryTopic(TopicType.subject).getTopic());
    assertEquals("p", query.queryTopic(TopicType.predicate).getTopic());
    assertEquals("o", query.queryTopic(TopicType.object).getTopic().toString());
  }

  /**
   * test the set operations
   * 
   * @throws Exception
   */
  @Test
  public void testSetOperations() throws Exception {
    TripleStore tripleStore = getTripleStoreFromExample("triple1");
    TripleQuery query = tripleStore.query();
    TripleQuery query1 = query.query("s", null, null);
    // debug = true;
    if (debug) {
      System.out.println("query1");
      TripleStoreDumper.dump(query1, "");
    }
    TripleQuery query2 = query.query(null, "p1", null);
    if (debug) {
      System.out.println("query2");
      TripleStoreDumper.dump(query, "");
    }
    TripleQuery intersect = query1.intersect(query2);
    if (debug) {
      System.out.println("intersection query1,query2:");
      TripleStoreDumper.dump(intersect, "");
    }
    assertEquals(2, intersect.size());
    TripleQuery complement = query2.complement(query1);
    if (debug) {
      System.out.println("complement query2,query1:");
      TripleStoreDumper.dump(complement, "");
    }
    assertEquals(3, complement.size());
    TripleQuery union = query.query(null, null, "o2").union(
        query.query(null, null, "o1"));
    if (debug) {
      System.out.println("union object query o2 - o1");
      TripleStoreDumper.dump(union, "");
    }
    assertEquals(3, union.size());
  }

}
