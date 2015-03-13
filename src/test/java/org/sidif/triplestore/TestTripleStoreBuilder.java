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
    if (debug) {
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
    if (debug)
      System.out.println(outText);
    String expected = "\"UTF-8 test\" is comment of SiDIF\n"
        + "Tokyo isA City\n" + "\"東京\" is name of Tokyo";
    assertTrue(outText.startsWith(expected));
  }

}
