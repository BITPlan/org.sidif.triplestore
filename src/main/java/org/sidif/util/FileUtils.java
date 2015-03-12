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
import java.io.IOException;
import java.nio.charset.Charset;
// Java 8 approach
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * same signatures as apache.commons.io but no dependency
 * this needs Java 8 - so if you need an older Java version you might want to delegate to apache.commons.io
 * @author wf
 *
 */
public class FileUtils {

  /**
   * read the given input file to a String
   * 
   * @param inputFile
   * @return - the string read
   * @throws IOException
   */
  public static String readFileToString(File inputFile) throws IOException {
    String result = new String(Files.readAllBytes(Paths.get(inputFile.toURI())));
    return result;
  }

  /**
   * read lines from the given inputFile
   * @param inputFile
   * @return a list of Strings
   * @throws Exception
   */
  public static List<String> readLines(File inputFile)
      throws Exception {
    List<String> list = Files.readAllLines(inputFile.toPath(), Charset.defaultCharset() );
    return list;
  }

  /**
   * write the given String to a the given file
   * 
   * @param file
   * @param text
   * @throws Exception 
   */
  public static void writeStringToFile(File file, String text) throws Exception {
    Files.write(Paths.get(file.toURI()), text.getBytes());
  }

}
