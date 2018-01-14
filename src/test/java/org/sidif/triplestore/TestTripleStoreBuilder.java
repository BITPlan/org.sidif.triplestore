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

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.logging.Level;

import org.junit.Test;
import org.sidif.util.TripleStoreBuilder;

/**
 * test the triple store builder command line interface
 * 
 * @author wf
 *
 */
public class TestTripleStoreBuilder extends BaseSiDIFTest {
  /**
   * for grabbing stderr and stdout
   */
  private String errText;
  private String outText;

  /**
   * test the triple Store Builder tool command line
   * 
   * @param args
   *          - command line arguments
   * @param expectedExit
   *          - the expected exit code
   * @param sleepTime
   * @throws Exception
   */
  public void testTripleStoreBuilder(String args[], int expectedExit,
      int sleepTime) throws Exception {
    TripleStoreBuilder.testMode = true;
    PrintStream stdout = System.out;
    PrintStream stderr = System.err;
    ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    try {
      System.setOut(new PrintStream(outStream, true));
      System.setErr(new PrintStream(errStream, true));
      TripleStoreBuilder.main(args);
      Thread.sleep(sleepTime);
    } catch (InterruptedException e) {
      // ignore
    } finally {
      System.setOut(stdout);
      System.setErr(stderr);
    }
    errText = errStream.toString();
    outText = outStream.toString();
    if (isDebug()) {
      LOGGER.log(Level.INFO, "stderr:\n" + errText);
      LOGGER.log(Level.INFO, "stdout:\n" + outText);
    }
    assertEquals("" + Arrays.toString(args), expectedExit,
        TripleStoreBuilder.exitCode);
  }

  @Test
  public void testHelp() throws Exception {
    String[] args = { "-h" };
    // debug=true;
    testTripleStoreBuilder(args, 1, 0);
    assertTrue(errText
        .contains("github: https://github.com/BITPlan/org.sidif.triplestore"));
    assertTrue(errText.contains("usage:"));
  }

  @Test
  public void testTripleStoreBuilder() throws Exception {
    File sidifFile = super.getExampleFile("utf8");
    String[] args = { "-i", sidifFile.getPath() };
    int expectedExit = 0;
    int sleepTime = 0;
    testTripleStoreBuilder(args, expectedExit, sleepTime);
    if (isDebug())
      System.out.println(outText);
    String expected = "\"UTF-8 test\" is comment of SiDIF\n"
        + "Tokyo isA City\n" + "\"東京\" is name of Tokyo";
    assertTrue(outText.startsWith(expected));
  }

}
