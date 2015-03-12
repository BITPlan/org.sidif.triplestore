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

import java.io.File;
import java.io.StringReader;
import java.util.List;

import org.sidif.util.FileUtils;
import org.junit.Test;
import org.sidif.parser.jjtree.ParseException;
import org.sidif.parser.jjtree.SiDIF;
import org.sidif.parser.node.SiDIFVisitor;
import org.sidif.triple.Triple;
import org.sidif.triple.TripleQuery;
import org.sidif.triple.TripleStore;
import org.sidif.util.TripleStoreDumper;
import org.sidif.visitor.SiDIFDumpVisitor;

/**
 * test the Parser for SiDIF
 * 
 * @author wf
 *
 */
public class TestSiDIFParser extends BaseSiDIFTest {

  @Test
  public void testSiDIFDump() throws ParseException {
    String sidif1 = "Tokyo isA City\n"
        + "\"http://www.metro.tokyo.jp/ENGLISH/ \" is webpage of Tokyo\n"
        + "City isA Concept\n" + "Country isA Concept\n"
        + "City linksTo Country\n" + "name addsTo City\n"
        + "name isA Property\n" + "Property isA Concept\n"
        + "Property linksTo Concept\n" + "Concept sameAs Topic";
    StringReader reader = new StringReader(sidif1);
    SiDIF sidif = new SiDIF(reader);
    SiDIFVisitor visitor = new SiDIFDumpVisitor();
    String dump = sidif.visit(visitor, "");
    if (debug)
      System.out.println(dump);
  }

  @Test
  public void testSiDIFExamples() throws Exception {
    final int maxLength = 10000; // FIXME performance issue for dump
    for (String example : super.getAllExampleNames()) {
      File exampleFile = this.getExampleFile(example);
      if (exampleFile.length() < maxLength) {
        SiDIF sidif = getSiDIFFromExample(example);
        SiDIFVisitor visitor = new SiDIFDumpVisitor();
        String dump = sidif.visit(visitor, "");
        File dumpFile = new File(exampleFile.getPath()
            .replace("sidif", "dump"));
        FileUtils.writeStringToFile(dumpFile, dump);
        String expected = FileUtils.readFileToString(dumpFile);
        if (debug && dump.length() < maxLength) {
          System.out.println(dump);
        }
        assertEquals(example, expected, dump);
      }
    }
  }

  @Test
  public void testTripleStore() throws Exception {
    /**
     * example1 (10)
     * example2 (15)
     * familyTree (51)
     * graph1 (7)
     * json_ld_manu_sporny (16)
     * notation3_TonyBenn (4)
     * presentation (39)
     * rdf_cd (22)
     * rdf_json_anna_wilder (8)
     * royal92-14 (209)
     * royal92 (25614)
     * trig_bob_alice (11)
     * turtle_spiderman (8)
     * typetest (62)
     * utf8 (3)
     * vcard (31)
     */
    int expectedSize[] = { 10, 15, 51, 7, 16, 4, 39, 22, 8, 209, 25614, 11, 8,
        62, 3, 31 };
    int index = 0;
    for (String example : getAllExampleNames()) {
      TripleStore tripleStore = getTripleStoreFromExample(example);
      int storeSize = tripleStore.size();
      if (debug) {
        System.out.println(example + " (" + storeSize + ")");
        TripleStoreDumper.dump(tripleStore);
      }
      assertEquals(example, expectedSize[index], storeSize);
      index++;
    }
  }

  @Test
  public void testSelect() throws Exception {
    TripleStore tripleStore = getTripleStoreFromExample("vcard");
    if (debug)
      TripleStoreDumper.dump(tripleStore);
    TripleQuery cardTriples = tripleStore.query().query(null, "isA", "VCard");
    // TripleContainer cardTriples = select(tripleStore,select);
    int cardSize = cardTriples.size();
    assertEquals(3, cardSize);
    for (Triple cardTriple : cardTriples.getTriples()) {
      if (debug)
        TripleStoreDumper.dump(cardTriple, "card: ");
      Triple propertySelect = new Triple(cardTriple.subject, null, null);
      TripleQuery cardProperties = tripleStore.query().queryTriple(propertySelect);
      for (Triple cardProperty : cardProperties.getTriples()) {
        if (debug)
          TripleStoreDumper.dump(cardProperty, "");
      }
    }
  }

  /**
   * test UTF-8 support
   * 
   * @throws Exception
   */
  @Test
  public void testUTF8() throws Exception {
    TripleStore tripleStore = this.getTripleStoreFromExample("utf8");
    if (debug)
      TripleStoreDumper.dump(tripleStore);
    List<Triple> triples = tripleStore.query().select(null, "name", null);
    assertEquals(1, triples.size());
    Triple nameTriple = triples.get(0);
    assertEquals("東京", nameTriple.object.toString());
  }

}
