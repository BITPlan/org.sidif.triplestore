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
package org.sidif.triplestore;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;
import org.sidif.parser.SiDIFLanguageParser;
import org.sidif.triple.Triple;
import org.sidif.util.SiDIFReader;
import org.sidif.visitor.SiDIFTripleListVisitor;

/**
 * test Triple Stream functions
 * 
 * @author wf
 *
 */
public class TestTripleStream extends BaseSiDIFTest {

  /**
   * check that the two triple list have the same content
   * @param tripleList1
   * @param tripleList2
   */
  public void checkEquals(List<Triple> tripleList1, List<Triple> tripleList2) {
    // https://stackoverflow.com/a/34818800/1497139
    Iterator<Triple> iter1 = tripleList1.iterator(),
        iter2 = tripleList2.iterator();
    while (iter1.hasNext() && iter2.hasNext())
      assertEquals(iter1.next(), iter2.next());
    assert !iter1.hasNext() && !iter2.hasNext();
  }

  @Test
  public void testTripleStream() throws Exception {
    /**
     * example1 (10) example2 (15) familyTree (51) graph1 (7)
     * json_ld_manu_sporny (16) notation3_TonyBenn (4) presentation (39) rdf_cd
     * (22) rdf_json_anna_wilder (8) royal92-14 (209) royal92 (25614)
     * trig_bob_alice (11) turtle_spiderman (8) typetest (62) utf8 (3) vcard
     * (31)
     */
    int expectedSize[] = { 11, 15, 51, 7, 16, 4, 546, 22, 8, 210, 31390, 11, 8,
        63, 3, 31 };
    int index = 0;
    for (String example : super.getAllExampleNames()) {
      File sidifFile = this.getExampleFile(example);
      SiDIFReader siDIFReader = new SiDIFLanguageParser();
      SiDIFReader siDIFReader2 = new SiDIFTripleListVisitor();
      List<Triple> tripleList = siDIFReader.fromSiDIFFile(sidifFile);
      List<Triple> tripleList2 = siDIFReader2.fromSiDIFFile(sidifFile);
      Stream<Triple> tripleStream = tripleList.stream();
      Stream<Triple> tripleStream2 = tripleList2.stream();
      assertEquals(""+index+":"+example,expectedSize[index], tripleStream.count());
      assertEquals(""+index+":"+example,expectedSize[index], tripleStream2.count());
      // checkEquals(tripleList,tripleList2);
      index++;
    }
  }
}
