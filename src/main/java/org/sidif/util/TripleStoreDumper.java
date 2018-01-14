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
package org.sidif.util;

import java.util.List;

import org.sidif.triple.Triple;
import org.sidif.triple.TripleQuery;
import org.sidif.triple.TripleStore;
import org.sidif.triple.TripleStore.TripleContainer;
import org.sidif.triple.Value;

/**
 * utility class to dump a TripleStore
 * @author wf
 *
 */
public class TripleStoreDumper {
  /*
   * dump the given concept
   * @param concept
   */
  public static void dump(Object concept) {
    System.out.print(concept.toString());
  }

  /**
   * dump the given triple with the given indentation
   * @param cardTriple
   * @param indent
   */
  public static void dump(Triple cardTriple, String indent) {
    System.out.print(indent);
    dump(cardTriple.getSubject());
    System.out.print(" --");
    dump(cardTriple.getPredicate());
    System.out.print("-> ");
    dump(cardTriple.getObject());
    if (cardTriple.getObject() instanceof Value) {
      @SuppressWarnings("unchecked")
      Value<Object> value=(Value<Object>) cardTriple.getObject();
      if (value.literal)
        dump("("+value.type+")");
    }
    System.out.println();
  }

  /**
   * dump the given triple Query and indentation
   * @param tripleQuery
   * @param indent - the indentation
   */
  public static void dump(TripleQuery tripleQuery,String indent) {
    for (Triple triple:tripleQuery.getTriples()) {
      dump(triple,indent);
    }
  }
  
  /**
   * dump the given Triple Container
   * 
   * @param tripleContainer
   * @param title
   */
  public static void dump(TripleContainer tripleContainer, String title) {
    System.out.println(title);
    String indent = "\t";
    for (Object concept : tripleContainer.tripleLookup.keySet()) {
      System.out.print(indent);
      dump(concept);
      System.out.println();
      List<Triple> triples = tripleContainer.tripleLookup
          .get(concept);
      if (triples == null) {
        System.out.println("concept "+concept.toString()+" has no triples");
      } else {
        for (Triple triple : triples) {
          dump(triple, indent);
        }
      }
    }
  }

  /**
   * dump the given triple Store
   * 
   * @param TripleStore
   */
  public static void dump(TripleStore TripleStore) {
    dump(TripleStore.bySubject, "bySubject");
    dump(TripleStore.byPredicate, "byPredicate");
    dump(TripleStore.byObject, "byObject");
  }

}
