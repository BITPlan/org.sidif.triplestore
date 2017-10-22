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
package org.sidif.util;

import java.io.File;
import java.io.InputStream;

import org.sidif.triple.TripleStore;

/**
 * generic interface to read a TripleStore from SiDIF
 * @author wf
 *
 */
public interface SiDIFReader {

  /**
   * get a triple Store from the given SiDIF Text
   * 
   * @param sidifText
   * @throws Exception
   * @return the tripleStore derived form the given sidifText
   */
  public TripleStore fromSiDIFText(String sidifText) throws Exception;

  /**
   * get a TripleStore from the given SiDIF File
   * 
   * @param sidifFile
   * @return a triple Store
   * @throws Exception
   */
  public TripleStore fromSiDIFFile(File sidifFile) throws Exception;

  /**
   * create a TripleStore from the given SiDIF inputStream
   * 
   * @param in
   *          - the inputStream to read from
   * @return - the tripleStore
   * @throws Exception
   */
  public TripleStore fromSiDIFStream(InputStream in) throws Exception;
}
