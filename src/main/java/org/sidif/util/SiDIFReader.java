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
import java.util.List;

import org.sidif.triple.Triple;

/**
 * generic interface to read a TripleStore from SiDIF
 * @author wf
 *
 */
public interface SiDIFReader {

  /**
   * get a a list of triples from the given SiDIF Text
   * 
   * @param sidifText
   * @throws Exception
   * @return the list of triples derived form the given sidifText
   */
  public List<Triple> fromSiDIFText(String sidifText) throws Exception;

  /**
   * get a list of triples from the given SiDIF File
   * 
   * @param sidifFile
   * @return a a list of triples
   * @throws Exception
   */
  public List<Triple> fromSiDIFFile(File sidifFile) throws Exception;

  /**
   * get a a list of triples from the given SiDIF inputStream
   * 
   * @param in
   *          - the inputStream to read from
   * @return - the list of triples
   * @throws Exception
   */
  public List<Triple> fromSiDIFStream(InputStream in) throws Exception;
}
