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
package uk.ac.stand.cs.jfdm.cs4099.grouptheory.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.junit.Test;

import uk.ac.stand.cs.jfdm.cs4099.grouptheory.GroupTheoryUtil;
import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutation;
import uk.ac.stand.cs.jfdm.cs4099.utils.Log;

/**
 * Tests various aspects of various classes from the GroupTheory Package. The
 * classes looked at are:
 * <h4> IPermutation Class</h4>
 * Aspects tested:
 * <ul>
 * <li>Creation</li>
 * <li>Access of the Permutation</li>
 * <li>Size of the Permutation</li>
 * </ul>
 * 
 * <h4> GroupTheoryUtil Class</h4>
 * Aspects tested:
 * <ul>
 * <li>Creation</li>
 * <li>Access of the Permutation</li>
 * <li>Size of the Permutation</li>
 * </ul>
 * 
 * @author jfdm
 * @version 2
 * 
 */
public class GroupTheoryUtilTest {

	/*
	 * Used to log various aspects of the objects operation.
	 */
	private Logger logger = Log.getLogger(GroupTheoryUtilTest.class.getName());

	/**
	 * \test Tests the creation of a Permutation.
	 * 
	 * <h2>Input</h2>
	 * <ul>
	 * <li>Permutation 1234</li>
	 * </ul>
	 * <h2>Expected Output</h2>
	 * <ul>
	 * <li>Permutation object containing 1234 as a byte array.</li>
	 * </ul>
	 */
	@Test
	public void createPermutation() {
		logger.info("Testing creation of a permutation");
		String[] input_permutation = { "1", "2", "3", "4" };
		IPermutation desired_output = IPermutation.Factory
				.createPermutation(new byte[] { 1, 2, 3, 4 });

		IPermutation test_perm = IPermutation.Factory
				.createPermutation(input_permutation);
		logger.info("Created Permutation");

		assertEquals(desired_output, test_perm);

		logger.info("Finishsed Test");
	}

	/**
	 * \test Tests the IPermutationCode classes ability to calculate Hamming
	 * Distance.
	 * 
	 * <h2>Input</h2>
	 * <ul>
	 * <li>Permutation 1234</li>
	 * <li>Permutation 1342</li>
	 * </ul>
	 * 
	 * <h2>Expected Output</h2>
	 * <ul>
	 * <li>Distance 3</li>
	 * </ul>
	 */
	@Test
	public void testHammingDistanceCalc() {
		logger.info("Begining Testing of Hamming Distance Calculation");

		int expected_distance = 3;

		IPermutation permutation1 = IPermutation.Factory
				.createPermutation(new String[] { "1", "2", "3", "4" });
		IPermutation permutation2 = IPermutation.Factory
				.createPermutation(new String[] { "1", "3", "4", "2" });

		logger.info("Created permutations 1234, 1243");

		int distance = GroupTheoryUtil.calculateHammingDistance(permutation1,
				permutation2);
		assertEquals(expected_distance, distance);
		logger.info("Distance has been calculated");

		logger.info("End of Test");
	}

	/**
	 * \test Tests multiplication of two permutations
	 * 
	 * <h2>Input</h2>
	 * <ul>
	 * <li>Permutation 12345</li>
	 * <li>Permutation 23451</li>
	 * </ul>
	 * <h2>Expected Output</h2>
	 * <ul>
	 * <li>Permutation object containing 51234.</li>
	 * </ul>
	 */
	@Test
	public void multiplicationTest() {
		logger.info("Testing Multiplication of a permutations");
		String[] group_1 = { "1", "2", "3", "4", "5" };
		String[] group_2 = { "2", "3", "4", "5", "1" };

		IPermutation expected_output = IPermutation.Factory
				.createPermutation(new byte[] { 5, 1, 2, 3, 4 });

		IPermutation test_perm_1 = IPermutation.Factory
				.createPermutation(group_1);
		IPermutation test_perm_2 = IPermutation.Factory
				.createPermutation(group_2);

		logger.info("Created Permutations");

		logger.info("Multipling 12345 and 23451");
		IPermutation result = GroupTheoryUtil
				.multiply(test_perm_1, test_perm_2);

		logger.info("Checking Results");
		assertEquals(expected_output, result);

		logger.info("Finishsed Test");
	}

	/**
	 * \test Two tests that check the operation of the feasibility method to
	 * determine the feasibility of two orbits with a Hamming distance of 5.
	 * 
	 * <h2>Test 1 Feasible Orbit</h2>
	 * 
	 * <h3>Input</h3>
	 * <ul>
	 * <li>12345</li>
	 * <li>23451</li>
	 * <li>34512</li>
	 * <li>45123</li>
	 * <li>51234</li>
	 * </ul>
	 * 
	 * <h3>Expected Output</h3>
	 * <ul>
	 * <li>Feasible = true</li>
	 * </ul>
	 * 
	 * <h2>Test 2 Orbit is not feasible</h2>
	 * 
	 * <h3>Input</h3>
	 * <ul>
	 * <li>23415</li>
	 * <li>23451</li>
	 * <li>34512</li>
	 * <li>45123</li>
	 * <li>51234</li>
	 * </ul>
	 * 
	 * <h3>Expected Output</h3>
	 * <ul>
	 * <li>Feasible = false</li>
	 * </ul>
	 */
	@Test
	public void feasibilityTest() {
		logger.info("Testing Feasibility Method");

		IPermutation[] feasible_orbit = {
				IPermutation.Factory.createPermutation(new byte[] { 1, 2, 3, 4,
						5 }),
				IPermutation.Factory.createPermutation(new byte[] { 2, 3, 4, 5,
						1 }),
				IPermutation.Factory.createPermutation(new byte[] { 3, 4, 5, 1,
						2 }),
				IPermutation.Factory.createPermutation(new byte[] { 4, 5, 1, 2,
						3 }),
				IPermutation.Factory.createPermutation(new byte[] { 5, 1, 2, 3,
						4 }) };
		IPermutation[] non_feasible_orbit = {

				IPermutation.Factory.createPermutation(new byte[] { 2, 3, 4, 5,
						1 }),
				IPermutation.Factory.createPermutation(new byte[] { 2, 3, 4, 1,
						5 }),
				IPermutation.Factory.createPermutation(new byte[] { 3, 4, 5, 1,
						2 }),
				IPermutation.Factory.createPermutation(new byte[] { 4, 5, 1, 2,
						3 }),
				IPermutation.Factory.createPermutation(new byte[] { 5, 1, 2, 3,
						4 }) };

		logger.info("Created Orbits");

		// ///////////////////
		// Test 1

		logger.info("Begin Test 1 Feasible Orbit");
		assertTrue(GroupTheoryUtil.isOrbitFeasible(feasible_orbit, 5));
		logger.info("Orbit is feasible");
		logger.info("Finishsed Test 1");

		// ///////////////////
		// Test 2

		logger.info("Begin Test 2 Non-Feasible Orbit");
		assertFalse(GroupTheoryUtil.isOrbitFeasible(non_feasible_orbit, 5));
		logger.info("Orbit is not feasible");
		logger.info("Finishsed Test 2");

		logger.info("Finishsed Test");
	}

	/**
	 * \test Tests the operation of the expand method to correctly expand a
	 * permutation with a generator.
	 * 
	 * <h2>Input</h2>
	 * <ul>
	 * <li>Generator = 23451</li>
	 * <li>Permutation = 12345</li>
	 * </ul>
	 * 
	 * <h2>Expected Output</h2>
	 * The following permutations:
	 * <ul>
	 * <li>12345</li>
	 * <li>51234</li>
	 * <li>45123</li>
	 * <li>34512</li>
	 * <li>23451</li>
	 * </ul>
	 * 
	 */
	@Test
	public void expansionTest() {
		logger.info("Testing Expansion Method");

		Collection<IPermutation> generators = new LinkedList<IPermutation>();
		generators.add(IPermutation.Factory.createPermutation(new byte[] { 2, 3, 4, 5,
				1 }));
		logger.info("Created Generators");
		
		IPermutation permutation = IPermutation.Factory.createPermutation(new byte[] { 1, 2, 3, 4,
				5 });
		
		logger.info("Created Permutation representative");

		Collection<IPermutation> expected_output = new LinkedList<IPermutation>();
		
		expected_output.add(IPermutation.Factory.createPermutation(new byte[] { 1, 2, 3, 4,
						5 }));
		expected_output.add(IPermutation.Factory.createPermutation(new byte[] { 2, 3, 4, 5,
						1 }));
		expected_output.add(IPermutation.Factory.createPermutation(new byte[] { 3, 4, 5, 1,
						2 }));
		expected_output.add(IPermutation.Factory.createPermutation(new byte[] { 4, 5, 1, 2,
						3 }));
		expected_output.add(IPermutation.Factory.createPermutation(new byte[] { 5, 1, 2, 3,
						4 }));
		logger.info("Created Expected output ");
		
		 IPermutation[] actual_output = GroupTheoryUtil.expand(permutation, generators);
		 logger.info("Expanded Permutation");
		 
		 logger.info("Checking number of results");
		 assertEquals(actual_output.length,5);
		 logger.info("Number of expected Permutations is correct:" + 5);
		
		 logger.info("Checking results");
		 
		 for(IPermutation perm : actual_output){
			 assertTrue(expected_output.contains(perm));
			 logger.info("Output contains: " + perm.toString());
		 }
		 

		logger.info("Finishsed Test");
	}
}
