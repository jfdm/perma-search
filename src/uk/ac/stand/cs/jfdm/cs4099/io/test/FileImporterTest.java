/*
 * Copyright (C) 2007-2008 
 * 			Jan de Muijnck-Hughes <jfdm@st-andrews.ac.uk>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * See LICENSE.txt for details
 *
 */
/**\package uk.ac.stand.cs.jfdm.cs4099.io.test
 * 
 * Contains the tests for the classes contained in the 
 * <code>uk.ac.stand.cs.jfdm.cs4099.io</code> package.
 * 
 */
package uk.ac.stand.cs.jfdm.cs4099.io.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.Queue;

import org.apache.log4j.Logger;
import org.junit.Test;

import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutation;
import uk.ac.stand.cs.jfdm.cs4099.io.FileImporter;
import uk.ac.stand.cs.jfdm.cs4099.utils.Log;

/**
 * Tests various aspects of the FileImporter Class. These aspects include:
 * <ul>
 * <li>Importing a File</li>
 * <li>Extraction of the Permutations</li>
 * <li>Extraction of the 'n' value</li>
 * </ul>
 * The test file is located in the 'data/test' folder of the project root
 * directory and is called 'gapout_test.txt'.
 * 
 * @author jfdm
 * @version 1
 */
public class FileImporterTest {

	/*
	 * Used to log various aspects of the objects operation.
	 */
	private Logger logger = Log.getLogger(FileImporterTest.class.getName());

	/**
	 * \test Tests the classes ability to open the file and extract the
	 * MetaData.
	 * 
	 * <h2>Test 1 - Extract Size</h2>
	 * <h3>Input</h3>
	 * <ul>
	 * <li>file = ./data/test/gapoutput_test.txt</li>
	 * </ul>
	 * 
	 * <h3>Expected Output</h3>
	 * <ul>
	 * <li>Size = 4</li>
	 * 
	 * <h2>Test 2 - Extract Automorphism Group and Generator</h2>
	 * <h3>Input</h3>
	 * <ul>
	 * <li>file = ./data/test/gapoutput_test.txt</li>
	 * </ul>
	 * 
	 * <h3>Expected Output</h3>
	 * <ul>
	 * <li>Autogroup Present = true</li>
	 * <li>Autogroup = C4</li>
	 * <li>Generator = 2341</li>
	 * </ul>
	 */
	@Test
	public void testOpenAndExtractionOfMetaData() {
		logger.info("Testing extraction of contents from input file");

		String testfile = "./data/test/gapoutput_test.txt";
		int expected_size = 4;
		boolean successful_extraction = true;
		String expected_auto_descrip = "C4";
		IPermutation expected_generator = IPermutation.Factory
				.createPermutation(new byte[] { 2, 3, 4, 1 });
		logger.info("Generated Expected Data");

		FileImporter fi = null;
		try {
			fi = new FileImporter(testfile);
			logger.info("File has been read in");
		} catch (FileNotFoundException e) {
			successful_extraction = false;
			logger.error("File has not been read in", e);
		}
		assertTrue(successful_extraction);
		logger.info("Metadata extracted successfully");
		fi.closeFileStream();

		// ///////////////////
		// Test 1

		logger.info("Begin Test 1 Testing existance of Size metadata");
		int size = fi.getPermutationSize();
		assertEquals(expected_size, size);
		logger.info("Size exists");
		logger.info("Finishsed Test 1");

		// ///////////////////
		// Test 2

		logger.info("Begin Test 2 Extracting Automorphism Data");
		assertTrue(fi.wasAutoGroupUsed());
		logger.info("Automorphism Group Present");

		String result_descrip = fi.getAutogroup_descrip();
		assertEquals(expected_auto_descrip, result_descrip);
		logger.info("Description Correct");
		Queue<IPermutation> result_gen = (Queue<IPermutation>) fi
				.getGenerators();
		assertEquals(expected_generator, result_gen.peek());
		logger.info("Generators Correct");
		logger.info("Test 2 complete");

		logger.info("Test Finished");
	}

	/**
	 * \test Tests the classes ability to extract the permutations
	 * 
	 * <h2>Input</h2>
	 * <ul>
	 * <li>file = ./data/test/gapoutput_test.txt</li>
	 * </ul>
	 * 
	 * <h2> Expected Output</h2>
	 * <ul>
	 * <li>Permutation 1 = 1234</li>
	 * <li>Permutation 2 = 4321</li>
	 * </ul>
	 */
	@Test
	public void extractPermutations() {
		logger.info("Testing access to extracted contents");

		String testfile = "./data/test/gapoutput_test.txt";
		FileImporter fi = null;
		IPermutation expected_permutation1 = IPermutation.Factory
				.createPermutation(new byte[] { 1, 2, 3, 4 });
		IPermutation expected_permutation2 = IPermutation.Factory
				.createPermutation(new byte[] { 4, 3, 2, 1 });

		try {
			fi = new FileImporter(testfile);
		} catch (FileNotFoundException e) {
			/* Ignore */
		}

		logger.info("Testing reading of permutation 1");
		IPermutation p1 = fi.readInPermutation();

		assertEquals(expected_permutation1, p1);
		logger.info("Permutation " + p1.toString() + "  matches");

		logger.info("Permutation 1 has been read in");

		logger.info("Testing reading of permutation 2");
		IPermutation p2 = fi.readInPermutation();

		assertEquals(expected_permutation2, p2);
		logger.info("Permutation " + p1.toString() + "  matches");

		logger.info("Permutation 2 has been read in");

		logger.info("Finishsed Test");
	}
}
