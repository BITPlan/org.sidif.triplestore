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
 * 
 * @author wf
 *
 */
public class TestTypes extends BaseSiDIFTest {
  @Test
  public void testTypes() throws Exception {
    TripleStore tripleStore = getTripleStoreFromExample("typetest");
    // debug=true;
    if (isDebug())
      TripleStoreDumper.dump(tripleStore);
    int tripleStoreSize = tripleStore.size();
    assertEquals(62, tripleStoreSize);
    int literals = 0;
    for (Triple triple : tripleStore.getTriples()) {
      if (isDebug())
        TripleStoreDumper.dump(triple, "");
      Object object = triple.getObject();
      if (object != null) {
        assertTrue(object instanceof Value);
        @SuppressWarnings("unchecked")
        Value<Object> value = (Value<Object>) object;
        if (value.literal)
          literals++;
      }
    }
    if (isDebug())
      System.out.println("found " + literals + " literals");
    assertEquals(16, literals);
  }

}
