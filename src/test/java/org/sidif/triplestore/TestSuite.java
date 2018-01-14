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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
/**
 * Testsuite for SiDIF
 * 
 * @author wf
 *
 */
@Suite.SuiteClasses({ TestDebug.class,TestSiDIFLanguageParser.class,TestSiDIFParser.class, TestSiDIFWriter.class,
    TestTripleStore.class, TestTripleStream.class,TestTripleStoreBuilder.class, TestGraph.class,
    TestTypes.class })
public class TestSuite {

}
