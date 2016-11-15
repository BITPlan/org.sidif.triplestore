/**
 * Copyright (C) 2015-2016 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 * 
 */
package org.sidif.triplestore;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
/**
 * Testsuite for SiDIF 
 * @author wf
 *
 */
@Suite.SuiteClasses({TestSiDIFParser.class,TestSiDIFWriter.class,TestTripleStore.class,TestTripleStoreBuilder.class,TestGraph.class, TestTypes.class})
public class TestSuite {
 
}
