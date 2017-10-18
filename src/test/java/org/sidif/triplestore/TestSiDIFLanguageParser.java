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
  public void testSimpleSiDIF() throws Exception {
    String sidifs[] = { "dad loves mum" };
    for (String sidif : sidifs) {
      LanguageParser lparser = getParser();
      lparser.setGui(false);
      super.runParser(lparser,sidif, 0);
    }
  }
  
  @Test
  public void testSiDIFSamples() throws Exception {
    File rootDir=new File ("src/test/resources");
    String[] extensions={".sidif"};
    checkgui=false;
    List<SourceDirectory> sourceDirectories=new ArrayList<SourceDirectory>();
    SourceDirectory sidifSrc=new SourceDirectory(rootDir.getCanonicalPath()+"/sidif", "utf-8", "SiDIF samples");
    sourceDirectories.add(sidifSrc);
    int progressStep=1;
    int limit=200;
    String[] ignorePrefixes={};
    testParseFilesInDirectories(rootDir, sourceDirectories, extensions, ignorePrefixes, limit, progressStep);
  }

}
