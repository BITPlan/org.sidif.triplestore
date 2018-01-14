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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.sidif.parser.SiDIFLanguageParser;

import com.bitplan.antlr.BaseTest;
import com.bitplan.antlr.LanguageParser;
import com.bitplan.antlr.SourceDirectory;

/**
 * Unit tests for testing ANTLR based SiDIF Language Parser
 * 
 * @author wf
 *
 */
public class TestSiDIFLanguageParser extends BaseTest {

  @Override
  public LanguageParser getParser() {
    return new SiDIFLanguageParser();
  }

  @Test
  /**
   * test a few simple SiDIF statements to be parseable
   * @throws Exception
   */
  public void testSimpleSiDIF() throws Exception {
    String sidifs[] = { "dad loves mum",
        "true is value of booleanLiteral",
        "0x7F is value of hexLiteral",
        "1 is value of integer",
        "'a' is value of char",
        "\"a multi\nline\n-comment \n\" is value of comment",
        "#\n#This is just acomment\n#over multiple lines\n1 is month of date",
        "2.7 is floatvalue of it",
        "2015-02-25 is exampleValue of Date\n" + 
        "2015-02-25 11:34 is exampleValue of DateTime\n" + 
        "1 is exampleValue of Number\n" + 
        "3.1415926 is exampleValue of Number\n" + 
        "\"Hello World!\" is exampleValue of Text\n" + 
        "11:24 is exampleValue of Time\n" + 
        "http://www.bitplan.com is exampleValue of IRI",
        "https://semantic-mediawiki.org/wiki/Help:List_of_datatypes is example of IRI"
     };
    super.debug=BaseSiDIFTest.isDebug();
    this.checkgui=true;
    for (String sidif : sidifs) {
      LanguageParser lparser = getParser();
      lparser.setGui(true);
      super.runParser(lparser, sidif, 0);
    }
  }

  @Test
  /**
   * test parse ability of SiDIF files in examples directory
   */
  public void testSiDIFSamples() throws Exception {
    super.debug=BaseSiDIFTest.isDebug();
    File rootDir = new File("src/test/resources");
    String[] extensions = { ".sidif" };
    checkgui = false;
    super.checkTree=false;
    super.gui=false;
    List<SourceDirectory> sourceDirectories = new ArrayList<SourceDirectory>();
    SourceDirectory sidifSrc = new SourceDirectory(
        rootDir.getCanonicalPath() + "/sidif", "utf-8", "SiDIF samples");
    sourceDirectories.add(sidifSrc);
    int progressStep = 1;
    int limit = 200;
    String[] ignorePrefixes = {};
    testParseFilesInDirectories(rootDir, sourceDirectories, extensions,
        ignorePrefixes, limit, progressStep);
  }

}
