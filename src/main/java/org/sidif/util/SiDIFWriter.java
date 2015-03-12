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

import org.sidif.triple.Triple;
import org.sidif.triple.TripleStore;
import org.sidif.triple.Value;

/**
 * creates SiDIF Output for a given TripleStore
 * 
 * @author wf
 *
 */
public class SiDIFWriter {
  private static Object currentSubject;
  private static boolean canonical;

  /**
   * get a sidif String from the given tripleStore
   * @param tripleStore
   * @return
   */
  public static String asSiDIF(TripleStore tripleStore) {
    String result=asSiDIF(tripleStore,false);
    return result;
  }
  
  /**
   * get the SiDIF representation of the given TripleStore
   * 
   * @param tripleStore
   * @return
   */
  public static String asSiDIF(TripleStore tripleStore, boolean canonical) {
    SiDIFWriter.canonical=canonical;
    String sidif = "";
    currentSubject=null;
    for (Triple triple : tripleStore.getTriples()) {
      sidif += asSiDIF(triple);
    }
    return sidif;
  }

  /**
   * get the subject's to string or "it" if the subject is the current Subject
   * @param subject
   * @return
   */
  public static String getIt(Object subject) {
    String subjectString;
    if (!canonical && currentSubject!=null && subject.toString().equals(currentSubject.toString())) {
      subjectString="it";
    } else {
      subjectString=subject.toString();
    }
    return subjectString;
  }
  
  /**
   * as SiDIF
   * 
   * @param triple
   * @return
   */
  public static String asSiDIF(Triple triple) {
    String sidif = "";
    Object subject = triple.getSubject();
    Object predicate = triple.getPredicate();
    Object object = triple.getObject();
    if (object instanceof Value) {
      Value value = (Value) object;  
      if (value.type!=null && value.type.equals("Reference")) {
        sidif += subject + " " + predicate + " " + getIt(value.getObject()) + "\n";       
      } else {
        sidif += value.asSiDIF()+ " is " +predicate  +" of "+getIt(subject) +"\n";
      }
    } else {
      sidif += subject + " " + predicate + " " + object + "\n";
    }
    currentSubject=subject;
    return sidif;
  }
}
