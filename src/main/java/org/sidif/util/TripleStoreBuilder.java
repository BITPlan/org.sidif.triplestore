/**
 * Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 * 
 */
package org.sidif.util;

import java.io.File;
import java.io.InputStream;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.sidif.triple.TripleStore;
import org.sidif.visitor.SiDIFTripleStoreVisitor;

/**
 * builder for TripleStore
 * 
 * @author wf
 *
 */
public class TripleStoreBuilder {
  public static final String VERSION = "0.0.2";
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
   * create a TripleStore from the given file
   * 
   * @param file
   * @return the TripleStore
   * @throws Exception
   */
  public static TripleStore fromSiDIFFile(File file) throws Exception {
    TripleStore result = SiDIFTripleStoreVisitor.fromSiDIFFile(file);
    return result;
  }
  
  /**
   * create a TripleStore from the given inputstream
   * @param in
   * @return the TripleStore
   * @throws Exception
   */
  public TripleStore fromSiDIFStream(InputStream in) throws Exception {
    TripleStore result = SiDIFTripleStoreVisitor.fromSiDIFStream(in);
    return result;
  }

  /**
   * create a TripleStore from the given SiDIF Text
   * 
   * @param sidif
   * @return
   * @throws Exception
   */
  public static TripleStore fromSiDIFText(String sidif) throws Exception {
    TripleStore result = SiDIFTripleStoreVisitor.fromSiDIFText(sidif);
    return result;
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
        tripleStore = fromSiDIFStream(System.in);
      } else {
        File sidifFile=new File(input);
        tripleStore = fromSiDIFFile(sidifFile);
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
     throw new Exception("unknown inputformat "+inputformat); 
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
