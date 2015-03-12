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

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.sidif.parser.jjtree.SiDIF;
import org.sidif.triple.TripleStore;
import org.sidif.visitor.SiDIFTripleStoreVisitor;

/**
 * Base class for sidif tests
 * 
 * @author wf
 *
 */
public class BaseSiDIFTest {

  // set to true for debugging
  boolean debug = false;

  /**
   * get all example Nemas
   * @return
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
    if (debug) {
      System.out.println("reading " + exampleFile.getName());
    }
    return exampleFile;
  }

  /**
   * get a SiDIF graph from the given example
   * 
   * @param exampleName
   * @return
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
   * @return
   * @throws Exception
   */
  public TripleStore getTripleStoreFromExample(String exampleName)
      throws Exception {
    File exampleFile = getExampleFile(exampleName);
    TripleStore tripleStore = SiDIFTripleStoreVisitor
        .fromSiDIFFile(exampleFile);
    return tripleStore;
  }

}
