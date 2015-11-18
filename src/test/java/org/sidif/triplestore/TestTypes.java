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

import org.junit.Test;
import org.sidif.triple.Triple;
import org.sidif.triple.TripleStore;
import org.sidif.triple.Value;
import org.sidif.triple.impl.TripleImpl;
import org.sidif.util.TripleStoreDumper;

/**
 * type test
 * @author wf
 *
 */
public class TestTypes extends BaseSiDIFTest {
  @Test
  public void testTypes() throws Exception {
    TripleStore tripleStore = getTripleStoreFromExample("typetest");
    // debug=true;
    if (debug)
      TripleStoreDumper.dump(tripleStore);
    int tripleStoreSize = tripleStore.size();
    assertEquals(62,tripleStoreSize);
    int literals=0;
    for (Triple triple:tripleStore.getTriples()) {
      if (debug)
        TripleStoreDumper.dump(triple,"");
      assertTrue(triple.getObject() instanceof Value);
      @SuppressWarnings("unchecked")
      Value<Object>value=(Value<Object>) triple.getObject();
      if (value.literal)
        literals++;
    }
    if (debug)
      System.out.println("found "+literals+" literals");
    assertEquals(16,literals);
  }

}
