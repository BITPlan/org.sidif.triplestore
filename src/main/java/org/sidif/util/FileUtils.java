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
