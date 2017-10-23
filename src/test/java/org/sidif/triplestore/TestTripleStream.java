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

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.junit.Test;
import org.sidif.parser.SiDIFLanguageParser;
import org.sidif.triple.Triple;
import org.sidif.util.SiDIFReader;

/**
 * test Triple Stream functions
 * @author wf
 *
 */
public class TestTripleStream {

  @Test
  public void testTripleStream() throws Exception {
    SiDIFReader siDIFReader = new SiDIFLanguageParser();
    File sidifFile=new File("src/test/resources/sidif/royal92.sidif");
    List<Triple> tripleList = siDIFReader.fromSiDIFFile(sidifFile);
    Stream<Triple> tripleStream = tripleList.stream();
    assertEquals(31390,tripleStream.count());
  }

}
