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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.sidif.triple.TripleStore;
import org.sidif.util.TripleStoreDumper;

/**
 * test graph handling
 * @author wf
 *
 */
public class TestGraph extends BaseSiDIFTest {
  
  /**
   * check the given set against the given strings
   * @param title
   * @param set
   * @param strings
   */
  public void check(String title,Set<Object> set, String[] strings) {
    assertEquals(title,set.size(),strings.length);
    List<String> objectStrings=new ArrayList<String>();
    for (Object object:set) {
      objectStrings.add(object.toString());
    }  
    String[] setStrings = objectStrings.toArray(new String[strings.length]);
    Arrays.sort(setStrings);
    for (int i=0;i<strings.length;i++) {
      assertEquals(title+" "+(i+1),strings[i],setStrings[i]);
    }
  }

  @Test
  public void testGraph() throws Exception {
    TripleStore tripleStore=super.getTripleStoreFromExample("graph1");
    int size = tripleStore.size();
    assertEquals(7,size);
    // debug=true;
    if (debug) {
      System.out.println(size);
      TripleStoreDumper.dump(tripleStore);
    }
    String subjects[]={"Graph","Labels","Nodes","Relationships","SiDIF"};
    String predicates[]={"comment","group","have","organize","recordsDataIn"};
    String objects[]={ 
        "\n" + 
        "  Example from \n" + 
        "  http://neo4j.com/docs/stable/what-is-a-graphdb.html\n" + 
        "\n" ,
        "Nodes","Properties","Relationships"};
    check("subject",tripleStore.getSubjects(),subjects);
    check("predicate",tripleStore.getPredicates(),predicates);
    check("object",tripleStore.getObjects(),objects);
  }
  
  @Test
  public void testFamily() throws Exception {
    TripleStore tripleStore=getTripleStoreFromExample("familyTree");
    if (debug)
      TripleStoreDumper.dump(tripleStore);
    int size = tripleStore.size();
    assertEquals(51,size);
  }
  
  @Test
  public void testRoyal92() throws Exception {
    TripleStore tripleStore=super.getTripleStoreFromExample("royal92");
    int size = tripleStore.size();
    assertEquals(25614,size);
  }
  
}
