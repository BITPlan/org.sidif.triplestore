/**
 * Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 * 
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
   * @param triple
   * @param indent
   */
  public static void dump(Triple triple, String indent) {
    System.out.print(indent);
    dump(triple.subject);
    System.out.print(" --");
    dump(triple.predicate);
    System.out.print("-> ");
    dump(triple.object);
    if (triple.object instanceof Value) {
      @SuppressWarnings("unchecked")
      Value<Object> value=(Value<Object>) triple.object;
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
