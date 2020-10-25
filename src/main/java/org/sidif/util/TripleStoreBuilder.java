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
package org.sidif.util;

import java.io.File;
import java.io.InputStream;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.sidif.triple.TripleStore;
import org.sidif.visitor.SiDIFTripleListVisitor;

/**
 * builder for TripleStore
 * 
 * @author wf
 *
 */
public class TripleStoreBuilder {
  public static final String VERSION = "0.0.9";
  public static boolean testMode = false;

  private CmdLineParser parser;
  public static int exitCode;

  /**
   * handle the given Throwable (in commandline mode)
   * 
   * @param t
   *          - the throwable to handle
   */
  public void handle(Throwable t) {
    System.out.flush();
    System.err.println(t.getClass().getSimpleName() + ":" + t.getMessage());
    if (debug)
      t.printStackTrace();
  }

  /**
   * show the Version
   */
  public static void showVersion() {
    System.err.println("org.sidif.triplestore Version: " + VERSION);
    System.err.println();
    System.err
        .println(" github: https://github.com/BITPlan/org.sidif.triplestore");
    System.err.println("");
  }

  /**
   * show Help
   */
  public void showHelp() {
    String msg = "Help\n";
    usage(msg);
  }

  /**
   * show a usage
   */
  public void usage(String msg) {
    System.err.println(msg);

    showVersion();
    System.err
        .println("usage: java org.sidif.triplestore.util.TripleStoreBuilder");
    parser.printUsage(System.err);
    exitCode = 1;
  }

  /**
   * set to true for debugging
   */
  @Option(name = "-d", aliases = { "--debug" }, usage = "debug\nadds debugging output")
  protected boolean debug = false;

  @Option(name = "-h", aliases = { "--help" }, usage = "help\nshow this usage")
  boolean showHelp = false;

  @Option(name = "-v", aliases = { "--version" }, usage = "showVersion\nshow current version if this switch is used")
  boolean showVersion = false;

  @Option(name = "-i", aliases = { "--input" }, usage = "input\nthe path to the sidif input .sidif file - will use stdin if omitted or '-' is specified as input parameter")
  protected String input = "-";

  @Option(name = "-o", aliases = { "--output" }, usage = "output\nthe path to the output file - will use stdout if omitted or '-' is specified as output parameter")
  protected String output = "-";

  @Option(name = "-if", aliases = { "--inputformat" }, usage = "input format \ndefault: sidif")
  protected String inputformat = "sidif";
  
  /**
   * get the SiDIF Reader
   * @return 
   */
  public static SiDIFReader getSiDIFReader() {
    return new SiDIFTripleListVisitor();
  }

  /**
   * get the TripleStore from the given File
   * @param sidifFile
   * @return the TripleStore
   * @throws Exception
   */
  public static TripleStore fromFile(File sidifFile) throws Exception {
    TripleStore tripleStore = TripleStore.fromSiDIFFile(getSiDIFReader(),sidifFile);
    return tripleStore;
  }
  
  /**
   * create a TripleStore from the given sidifText
   * @param sidifText
   * @return the tripleStore
   * @throws Exception 
   */
  public static TripleStore fromSiDIFText(String sidifText) throws Exception {
    TripleStore tripleStore = TripleStore.fromSiDIFText(getSiDIFReader(),sidifText);
    return tripleStore;
  }
  
  /**
   * get the tripleStore from the given input Stream
   * @param in
   * @return - the TripleStore
   * @throws Exception
   */
  public static TripleStore fromStream(InputStream in) throws Exception {
    TripleStore tripleStore = TripleStore.fromSiDIFStream(getSiDIFReader(),in);
    return tripleStore;
  }
  
  /**
   * work on the given input in the given inputformat
   * @param input
   * @param inputformat
   * @throws Exception
   */
  private void workOn(String input, String inputformat, String output) throws Exception {
    if ("sidif".equals(inputformat)) {
      TripleStore tripleStore;
      if ("-".equals(input)) {
        tripleStore = fromStream(System.in);
      } else {
        File sidifFile=new File(input);
        tripleStore = fromFile(sidifFile);
      }
      boolean canonical=true;
      String sidif=SiDIFWriter.asSiDIF(tripleStore, canonical);
      if ("-".equals(output)) {
        System.out.println(sidif);
      } else {
        File outputFile=new File(output);
        FileUtils.writeStringToFile(outputFile, sidif);
      }
    } else {
     throw new Exception("unknown input format "+inputformat); 
    }
  }

  /**
   * main instance - this is the non-static version of main - it will run as a
   * static main would but return it's exitCode to the static main the static
   * main will then decide whether to do a System.exit(exitCode) or not.
   * 
   * @param args
   *          - command line arguments
   * @return - the exit Code to be used by the static main program
   */
  protected int maininstance(String[] args) {
    parser = new CmdLineParser(this);
    try {
      parser.parseArgument(args);
      if (debug)
        showVersion();
      if (this.showVersion) {
        showVersion();
      } else if (this.showHelp) {
        showHelp();
      } else {
        if (this.input == null) {
          usage("no parameters specified");
        } else {
          workOn(this.input,this.inputformat,this.output);
          exitCode=0;
        }
      }
    } catch (CmdLineException e) {
      // handling of wrong arguments
      usage(e.getMessage());
    } catch (Exception e) {
      handle(e);
      exitCode = 1;
    }
    return exitCode;
  }

  /**
   * main routine for TripleStore Builder
   * @param args
   */
  public static void main(String[] args) {
    TripleStoreBuilder tripleStoreBuilder = new TripleStoreBuilder();
    int result = tripleStoreBuilder.maininstance(args);
    if (!testMode && result != 0)
      System.exit(result);
  }


}
