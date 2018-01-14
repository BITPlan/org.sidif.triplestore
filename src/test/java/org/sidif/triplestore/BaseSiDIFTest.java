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

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.sidif.parser.jjtree.SiDIF;
import org.sidif.triple.TripleStore;
import org.sidif.util.SiDIFReader;
import org.sidif.util.TripleStoreBuilder;

/**
 * Base class for SiDIF tests
 * 
 * @author wf
 *
 */
public class BaseSiDIFTest {
  /**
   * Logging may be enabled by setting debug to true
   */
  protected static java.util.logging.Logger LOGGER = java.util.logging.Logger
      .getLogger("org.sidif.triplestore");
  
  // set to true for debugging
  private static boolean debug = false;
  
  public static boolean isDebug() {
    return debug;
  }

  public static void setDebug(boolean pDebug) {
    debug = pDebug;
  }

  /**
   * get all example Names
   * @return all example names
   */
  String[] getAllExampleNames() {
    String examples[] = { "example1", "example2","familyTree","graph1", "json_ld_manu_sporny","notation3_TonyBenn",
        "presentation", "rdf_cd", "rdf_json_anna_wilder", "royal92-14", "royal92", "trig_bob_alice", "turtle_spiderman","typetest",
        "utf8","vcard" };
    return examples;
  }
  
  /**
   * get the example file for the given exampleName
   * 
   * @param exampleName
   * @return
   */
  public File getExampleFile(String exampleName) {
    File exampleFile = new File("src/test/resources/sidif/" + exampleName
        + ".sidif");
    assertTrue(exampleName+" should exist",exampleFile.exists());
    if (isDebug()) {
      System.out.println("reading " + exampleFile.getName());
    }
    return exampleFile;
  }

  /**
   * get a SiDIF graph from the given example
   * 
   * @param exampleName
   * @return the SiDIF parser result
   * @throws Exception
   */
  public SiDIF getSiDIFFromExample(String exampleName) throws Exception {
    File exampleFile = getExampleFile(exampleName);
    SiDIF sidif = SiDIF.fromFile(exampleFile);
    return sidif;
  }

  /**
   * get the TripleStore for the given example Name
   * 
   * @param exampleName
   * @throws Exception
   * @return the triple for the given example name
   */
  public TripleStore getTripleStoreFromExample(String exampleName)
      throws Exception {
    File exampleFile = getExampleFile(exampleName);
    SiDIFReader siDIFReader = TripleStoreBuilder.getSiDIFReader();
    TripleStore tripleStore=TripleStore.fromSiDIFFile(siDIFReader,exampleFile);
    return tripleStore;
  }

}
