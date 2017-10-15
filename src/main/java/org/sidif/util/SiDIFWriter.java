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
   * get a sidif String from the given TripleStore
   * @param tripleStore
   * @return the sidif string created for the given TripleStore
   */
  public static String asSiDIF(TripleStore tripleStore) {
    String result=asSiDIF(tripleStore,false);
    return result;
  }
  
  /**
   * get the SiDIF representation of the given TripleStore
   * 
   * @param tripleStore
   * @param canonical - if true the canonical version is used
   * @return the sidif String 
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
   * @return the string
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
   * @return the sidif representation of the triple
   */
  public static String asSiDIF(Triple triple) {
    String sidif = "";
    Object subject = triple.getSubject();
    Object predicate = triple.getPredicate();
    Object object = triple.getObject();
    if (object instanceof Value) {
      @SuppressWarnings("rawtypes")
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
