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
package org.sidif.triplestore;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.sidif.triple.Triple;
import org.sidif.triple.TripleQuery;
import org.sidif.triple.impl.TripleImpl;
import org.sidif.triple.impl.TripleQueryImpl;
import org.sidif.triple.impl.TripleQueryImpl.TopicType;
import org.sidif.triple.TripleStore;
import org.sidif.util.TripleStoreBuilder;
import org.sidif.util.TripleStoreDumper;

/**
 * test the simple TripleStore
 * 
 * @author wf
 *
 */
public class TestTripleStore extends BaseSiDIFTest {

  /**
   * test the multimaps used for the triplestore
   * attempt to add the same triple twice
   */
  @Test
  public void testMultiMap() {
    TripleStore tripleStore = new TripleStore();
    TripleImpl triple1 = new TripleImpl("s", "p", "o");
    TripleImpl triple2 = new TripleImpl("s", "p", "o");
    TripleImpl triple3 = new TripleImpl("s", "p3", "o3");
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

  /**
   * test select function
   * @throws Exception
   */
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

  @Test
  public void testGetTripleStoreFromQuery() throws Exception {
    TripleStore tripleStore = getTripleStoreFromExample("triple1");
    TripleQuery query = tripleStore.query();
    assertEquals(6,query.size());
    TripleQuery query2 = query.query("s",null,null);
    assertEquals(4,query2.size());
    TripleStore tripleStore2=query2.getTripleStore();
    assertEquals(6,tripleStore2.query().size());
  }
  /**
   * test query
   * 
   * @throws Exception
   */
  @Test
  public void testQuery() throws Exception {
    TripleStore tripleStore = getTripleStoreFromExample("triple1");
    TripleQueryImpl query = tripleStore.query();
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

  @Test
  public void testHasVersusIsOf() throws Exception {
    TripleStore tripleStore = getTripleStoreFromExample("roles");
    //debug=true;
    if (debug) {
      TripleStoreDumper.dump(tripleStore);
    }
    Triple triple = tripleStore.query().selectSingle("properties","source",null);
    assertNotNull(triple);
    assertEquals("Topic",triple.getObject().toString());
    triple=tripleStore.query().selectSingle("topicConfiguration","source",null);
    assertNotNull(triple);
    assertEquals("Topic",triple.getObject().toString());;
  }
  
  @Test
  public void testSelectQuery() throws Exception {
    String sidif="Training isA Context\n" + 
        "\"Training#sidif\" is sidif of it\n"+
        "ZQuestion needs Properties\n" + 
        "ZAnswer needs Properties";
    TripleStore tripleStore=TripleStoreBuilder.fromSiDIFText(sidif);
    debug=true;
    if (debug) {
      TripleStoreDumper.dump(tripleStore);
    }
    TripleQuery query=tripleStore.query();
    TripleQuery needed=query.query(null,"needs","Properties");
    assertEquals(2,needed.size());
    Triple contextTriple=query.selectSingle(null,"isA","Context");
    String contextName=contextTriple.getSubject().toString();
    assertEquals("Training",contextName);
    Triple sidifTriple=query.selectSingle(contextName,"sidif", null);
    String contextSidif=sidifTriple.getObject().toString();
    assertEquals("Training#sidif",contextSidif);
    
  }
}
