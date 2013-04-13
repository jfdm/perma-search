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
package uk.ac.stand.cs.jfdm.cs4099.search;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;

import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutation;
import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutationCode;
import uk.ac.stand.cs.jfdm.cs4099.utils.Log;

/**
 * Tests the ability of the SearchClass class to perform a search.
 * 
 * @author jfdm
 * @version 1
 * 
 */
public class SearchClassTest {

	/*
	 * Used to log various aspects of the objects operation.
	 */
	private Logger logger = Log.getLogger(SearchClassTest.class.getName());

	/**
	 * \test Performs a search on a set of permutations constructed with out an
	 * automorphism group and compares the output with the expected output.
	 * 
	 * <h2>Input</h2>
	 * <ul>
	 * <li>The file ./data/test/search_test_simple.txt that contains the
	 * permutations for the set 12345.</li>
	 * <li>Hamming Distance = 5</li>
	 * </ul>
	 * 
	 * <h2>Expected Output</h2>
	 * The following permutations:
	 * <ul>
	 * <li>12345</li>
	 * <li>43152</li>
	 * <li>54213</li>
	 * <li>35421</li>
	 * <li>21534</li>
	 * </ul>
	 * 
	 * @throws IOException
	 * @throws SearchException
	 */
	@Test
	public void testSimpleSearch() throws IOException, SearchException {
		logger
				.info("Testing search for Permutation Code with Hamming Distance 5, size of permutation 5");

		int hamming_distance = 5;
		String input_file = "./data/test/search_test_simple.txt";

		byte[][] expected_perm = { { 1, 2, 3, 4, 5 }, { 4, 3, 1, 5, 2 },
				{ 5, 4, 2, 1, 3 }, { 3, 5, 4, 2, 1 }, { 2, 1, 5, 3, 4 } };

		SearchClass sc = new SearchClass(input_file, hamming_distance);

		logger.info("Set up search");
		IPermutationCode results = sc.performSearch();

		logger.info("Checking Expected results against results");

		assertEquals(5, sc.getNumberOfCodeWords());
		logger.info("Number of code words was expected");

		int counter = 0;
		for (IPermutation perm : results.getCodeWords()) {

			assertEquals(IPermutation.Factory
					.createPermutation(expected_perm[counter]), perm);
			counter++;
			logger.info("Permutation " + counter + " matches");
		}

		logger.info("Results the same");
		logger.info("End of Test");

	}

	/**
	 * \test Performs a search on a set of permutations constructed with out an
	 * automorphism group and compares the output with the expected output.
	 * 
	 * <h2>Input</h2>
	 * <ul>
	 * <li>The file ./data/test/search_test_complex.txt that contains the
	 * permutations for the set 12345.</li>
	 * <li>Hamming Distance = 5</li>
	 * </ul>
	 * 
	 * <h2>Expected Output</h2>
	 * The following permutations:
	 * <ul>
	 * <li>12345</li>
	 * <li>43152</li>
	 * <li>54213</li>
	 * <li>35421</li>
	 * <li>21534</li>
	 * </ul>
	 * 
	 * @throws IOException
	 * @throws SearchException
	 */
	@Test
	public void testComplexSearch() throws IOException, SearchException {
		logger
				.info("Testing search for Permutation Code with Hamming Distance 5, size of permutation 5");

		int hamming_distance = 5;
		String input_file = "./data/test/search_test_complex.txt";

		byte[][] expected_perm = { { 1, 2, 3, 4, 5 }, { 4, 3, 1, 5, 2 },
				{ 5, 4, 2, 1, 3 }, { 3, 5, 4, 2, 1 }, { 2, 1, 5, 3, 4 } };

		SearchClass sc = new SearchClass(input_file, hamming_distance);

		logger.info("Set up search");
		IPermutationCode results = sc.performSearch();

		logger.info("Checking Expected results against results");

		assertEquals(5, sc.getNumberOfCodeWords());
		logger.info("Number of code words was expected");
		int counter = 0;
		for (IPermutation perm : results.getCodeWords()) {

			assertEquals(IPermutation.Factory
					.createPermutation(expected_perm[counter]), perm);
			counter++;
			logger.info("Permutation " + counter + " matches");
		}

		logger.info("Results the same");
		logger.info("End of Test");
	}
}
