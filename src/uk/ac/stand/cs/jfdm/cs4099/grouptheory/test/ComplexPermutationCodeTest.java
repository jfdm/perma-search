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
/**\package uk.ac.stand.cs.jfdm.cs4099.grouptheory.test
 * 
 * Contains the various tests for interfaces defined in the 
 * <code>uk.ac.stand.cs.jfdm.cs4099.grouptheory</code> package.
 * 
 */
package uk.ac.stand.cs.jfdm.cs4099.grouptheory.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import org.apache.log4j.Logger;
import org.junit.Test;

import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutation;
import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutationCodeException;
import uk.ac.stand.cs.jfdm.cs4099.grouptheory.impl.ComplexPermutationCode;
import uk.ac.stand.cs.jfdm.cs4099.utils.Log;

/**
 * Tests various aspects of the ComplexPermutationCode Class. These aspects are:
 * <ul>
 * <li>Creation of the IPermutationCode</li>
 * <li>Addition of Permutations that can be added and cannot be added</li>
 * <li>The calculation of Hamming Distances.</li>
 * </ul>
 * 
 * @author jfdm
 * @version 1
 * 
 */
public class ComplexPermutationCodeTest {
	/*
	 * Used to log various aspects of the objects operation.
	 */
	private Logger logger = Log.getLogger(ComplexPermutationCodeTest.class
			.getName());

	/**
	 * \test Tests the setup of the ComplexPermutationCode object.
	 * 
	 * <h2>Input</h2>
	 * <ul>
	 * <li>Minimum Hamming Distance 3</li>
	 * <li>Number of elements in Permutation 4</li>
	 * <li>Generators = 1234 and 2341</li>
	 * </ul>
	 * 
	 * <h2>Expected Output</h2>
	 * <ul>
	 * <li>Minimum Hamming Distance 3</li>
	 * <li>Number of elements in Permutation 4</li>
	 * </ul>
	 */
	@Test
	public void testIPermutationCodeSetUp() {
		logger.info("Begining Testing of Hamming Distance Calculation");
		IPermutation[] generators = {
				IPermutation.Factory.createPermutation(new String[] { "1", "2",
						"3", "4", }),
				IPermutation.Factory.createPermutation(new String[] { "2", "3",
						"4", "1" }) };
		ComplexPermutationCode pc = new ComplexPermutationCode(3, 4, generators);
		int expected_distance = 3;
		int expected_no_elements = 4;

		logger
				.info("Created IPermutationCode with distance 3 and no elements 4");

		assertEquals(expected_distance, pc.getMinHammingDistance());
		logger.info("Distance is correct");
		assertEquals(expected_no_elements, pc.getCodeWordSize());
		logger.info("No of elements is correct");

		logger.info("End of Test");
	}

	/**
	 * \test Tests the expansion of a representative.
	 * 
	 * <h2>Input</h2>
	 * <ul>
	 * <li>Minimum Hamming Distance 3</li>
	 * <li>Number of elements in Permutation 5</li>
	 * <li>Representative = 12345</li>
	 * <li>Generators = 23451</li>
	 * </ul>
	 * 
	 * <h2>Expected Output</h2>
	 * The following Permutations:
	 * <ul>
	 * <li>12345/li>
	 * <li>51234/li>
	 * <li>45123/li>
	 * <li>34512/li>
	 * <li>23451/li>
	 * </ul>
	 */
	@Test
	public void testExpansionOfRepresentative() {
		logger.info("Begining Testing of Expansion");

		IPermutation[] generators = { IPermutation.Factory
				.createPermutation(new String[] { "2", "3", "4", "5", "1" }) };
		logger.info("Created Generator 23451");

		byte[][] expected_expansion = { { 1, 2, 3, 4, 5 }, { 5, 1, 2, 3, 4 },
				{ 4, 5, 1, 2, 3 }, { 3, 4, 5, 1, 2 }, { 2, 3, 4, 5, 1 } };
		logger.info("Created Expected Results");

		ComplexPermutationCode pc = new ComplexPermutationCode(3, 5, generators);
		logger.info("Created ComplexPermutationCode");

		IPermutation representative = IPermutation.Factory
				.createPermutation(new String[] { "1", "2", "3", "4", "5" });
		logger.info("Created Representative 12345");

		IPermutation[] results = pc.expand(representative);
		logger.info("Expanding Representative");

		logger.info("Checking Expected results against results");
		assertEquals(5, results.length);
		logger.info("Size is correct " + 5);

		int counter = 0;
		for (IPermutation perm : results) {

			assertEquals(IPermutation.Factory
					.createPermutation(expected_expansion[counter]), perm);
			counter++;
			logger.info("Permutation 1 matches " + perm.toString());
		}

	}

	/**
	 * \test Series of tests that test the ComplexPermutationCode classes
	 * ability to add permutations.
	 * 
	 * <h2>Permutation Code parameters</h2>
	 * <ul>
	 * <li>Min Hamming Distance 3</li>
	 * <li>Size of Permutations 5</li>
	 * <li>Generator = 23451</li>
	 * </ul>
	 * 
	 * <h2>Test 1 Add first permutation</h2>
	 * 
	 * <h3>Input</h3>
	 * <ul>
	 * <li>Permutation 12345</li>
	 * </ul>
	 * 
	 * <h3>Expected Output</h3>
	 * <ul>
	 * <li>Can Add = true</li>
	 * <li>Number of Codewords = 1</li>
	 * </ul>
	 * 
	 * <h2>Test 2 Add another permutation that can be added</h2>
	 * 
	 * <h3>Input</h3>
	 * <ul>
	 * <li>Permutation 54321</li>
	 * </ul>
	 * 
	 * <h3>Expected Output</h3>
	 * <ul>
	 * <li>Can Add = true </li>
	 * <li>Number of Codewords = 2</li>
	 * </ul>
	 * 
	 * <h2>Test 3 Add permutation that cannot be added</h2>
	 * 
	 * <h3>Input</h3>
	 * <ul>
	 * <li>Permutation 12354</li>
	 * </ul>
	 * 
	 * <h3>Expected Output</h3>
	 * <ul>
	 * <li>Can Add = false</li>
	 * <li>Number of Codewords = 2</li>
	 * </ul>
	 * @throws IPermutationCodeException If there is an error adding a permutation.
	 */
	@Test
	public void testAdditionOfPermutationsToComplexPermutationCode() throws IPermutationCodeException {
		logger.info("Begin Testing Addition of Permutations");
		boolean result;
		IPermutation[] generators = { IPermutation.Factory
				.createPermutation(new String[] { "2", "3", "4", "5", "1" }) };
		logger.info("Created Generator 23451");
		// permutations that can be added
		IPermutation permutation1 = IPermutation.Factory
				.createPermutation(new String[] { "1", "2", "3", "4", "5" });
		IPermutation permutation2 = IPermutation.Factory
				.createPermutation(new String[] { "5", "4", "3", "2", "1" });
		// permutations that cannot be added
		IPermutation permutation3 = IPermutation.Factory
				.createPermutation(new String[] { "1", "2", "3", "5", "4" });
		logger.info("Permutations Created");
		// Coding to be tested
		ComplexPermutationCode pc = new ComplexPermutationCode(3, 5, generators);

		// ///////////////////
		// Test 1

		logger.info("Begin Test 1");
		result = pc.addNewPermutation(permutation1);
		assertTrue(result);
		assertEquals(1, pc.getNumberOfCodeWords());
		logger.info("Test 1 complete");

		// ///////////////////
		// Test 2

		logger.info("Begin Test 2");
		result = pc.addNewPermutation(permutation2);
		assertTrue(result);
		assertEquals(2, pc.getNumberOfCodeWords());
		assertEquals(2, pc.getNumberOfCodeWords());
		logger.info("Test 2 complete");

		// ///////////////////
		// Test 3

		logger.info("Begin Test 3");
		result = pc.addNewPermutation(permutation3);
		assertFalse(result);
		assertEquals(2, pc.getNumberOfCodeWords());
		logger.info("Test 3 complete");

		logger.info("End of Test");
	}
}
