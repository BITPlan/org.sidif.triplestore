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
package org.sidif.triplestore;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.sidif.triple.TripleStore;
import org.sidif.util.FileUtils;
import org.sidif.util.SiDIFWriter;

/**
 * test the sidif Writer utility
 * 
 * @author wf
 *
 */
public class TestSiDIFWriter extends BaseSiDIFTest {

	/**
	 * show a diff
	 * 
	 * @param exampleName
	 * @param sidif
	 * @param sidifWrite
	 * @param sidifLines
	 * @param sidifWriteLines
	 * @throws Exception
	 */
	public void showDiff(String exampleName, String sidif, String sidifWrite,
			List<String> sidifLines, List<String> sidifWriteLines) throws Exception {
		System.out.println("original " + exampleName);
		System.out.println(sidif);
		System.out.println("writer " + exampleName);
		System.out.println(sidifWrite);
		int index = 1;
		for (String sidifLine : sidifLines) {
			System.out.println(index + ":" + sidifLine);
			String other = "-";
			if (sidifWriteLines.size() >= index) {
				other = sidifWriteLines.get(index - 1);
			}
			System.out.println(index + ":" + other);
			assertEquals(exampleName + "(" + index + ")", sidifLine, other);
			index++;
		}
	}

	@Test
	public void testRoundTrip() throws Exception {
		// run two round - first with no assertions to potentially update
		// the canonical files (if outdated)
		checkRoundTrip(false);
		// now run with assertions which should be o.k. when canonical files
		// have been updated
		checkRoundTrip(true);
	}

	/**
	 * check all examples for "round trip" that is read a sidif file to a
	 * TripleStore and create a canonical version from it the canonical result
	 * should be the same when read as the one that was written
	 * 
	 * @param withAssertions
	 * @throws Exception
	 */
	public void checkRoundTrip(boolean withAssertions) throws Exception {
		final int maxLength = 10000;
		// debug = true;
		// String examples[] = { "example1", "example2", "utf8" };
		for (String exampleName : super.getAllExampleNames()) {
			File exampleFile = this.getExampleFile(exampleName);
			if (exampleFile.length() < maxLength) {
				File canonicalSiDIFFile = new File(exampleFile.getPath().replace(
						"/sidif", "/sidif.canonical"));
				File inputFile = exampleFile;
				if (canonicalSiDIFFile.exists()
						&& canonicalSiDIFFile.lastModified() > exampleFile.lastModified()) {
					inputFile = canonicalSiDIFFile;
				}
				String sidif = FileUtils.readFileToString(inputFile);
				List<String> sidifLines = FileUtils.readLines(inputFile);
				if (isDebug())
					System.out.println(sidifLines.size());
				TripleStore tripleStore = this.getTripleStoreFromExample(exampleName);
				boolean canonical = true;
				String sidifWrite = SiDIFWriter.asSiDIF(tripleStore, canonical);
				if (!canonicalSiDIFFile.exists()
						|| (canonicalSiDIFFile.lastModified() <= exampleFile
								.lastModified())) {
					FileUtils.writeStringToFile(canonicalSiDIFFile, sidifWrite);
				}
				String[] sidifWriteLinesArray = sidifWrite.split("\n");
				List<String> sidifWriteLines = new ArrayList<String>(
						Arrays.asList(sidifWriteLinesArray));
				if (isDebug()) {
					System.out.println(sidifWriteLines.size());
					System.out.println(sidifWrite);
				}
				Collections.sort(sidifLines);
				Collections.sort(sidifWriteLines);
				if (withAssertions) {
					if (sidifLines.size() != sidifWriteLines.size()) {
						showDiff(exampleName, sidif, sidifWrite, sidifLines,
								sidifWriteLines);
					}
					assertEquals(exampleName, sidifLines.size(), sidifWriteLines.size());
					if (!sidifLines.equals(sidifWriteLines)) {
						showDiff(exampleName, sidif, sidifWrite, sidifLines,
								sidifWriteLines);
					}
				}
				if (withAssertions)
					assertEquals(exampleName, sidifLines, sidifWriteLines);
			}
		}
	}

}
