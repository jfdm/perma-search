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
package uk.ac.stand.cs.jfdm.cs4099.validate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Test;

import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutationCode;
import uk.ac.stand.cs.jfdm.cs4099.utils.Log;

/**
 * Tests the Validation Searches ability to extract the full permutations from a
 * results file and search for the specified permutation code and compare the
 * results with the known result.
 * 
 * @author jfdm
 * @version 1
 * 
 */
public class ValidateSearchTest {
	/*
	 * Used to log various aspects of the objects operation.
	 */
	private Logger logger = Log.getLogger(ValidateSearchTest.class.getName());

	/**
	 * \test Tests the ValidateSearch programs ability to validate a set of
	 * results.
	 * 
	 * <h2>Input</h2>
	 * <ul>
	 * <li>The file ./data/test/results_5_5.xml that contains the results for a
	 * (5,5) code.</li>
	 * </ul>
	 * 
	 * <h2>Expected Output</h2>
	 * <ul>
	 * <li>A boolean value of true.</li>
	 * <li>five codewords</li>
	 * </ul>
	 * 
	 * @throws Exception
	 */
	@Test
	public void validationSearchTest() throws Exception {
		logger.info("Begin testing validation search");

		ValidateSearch vs = new ValidateSearch("./data/test/results_5_5.xml");
		logger.info("Loaded in file");
		IPermutationCode pc = vs.performSearch();
		logger.info("Performing search");
		logger.info("Checking results");
		assertEquals(pc.getNumberOfCodeWords(), 5);
		logger.info("Expected number of results found");
		assertTrue(vs.compareResults());
		logger.info("Results file contains a valid permutation code.");
		logger.info("End of Test");
	}
}
