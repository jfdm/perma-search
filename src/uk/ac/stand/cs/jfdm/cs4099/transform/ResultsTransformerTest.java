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
package uk.ac.stand.cs.jfdm.cs4099.transform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.log4j.Logger;
import org.junit.Test;

import uk.ac.stand.cs.jfdm.cs4099.utils.Log;

/**
 * Tests the Results Transformers ability to transform the results file into an
 * input file.
 * 
 * @author jfdm
 * @version 1
 * 
 */
public class ResultsTransformerTest {
	/*
	 * Used to log various aspects of the objects operation.
	 */
	private Logger logger = Log.getLogger(ResultsTransformerTest.class
			.getName());

	/**
	 * \test Tests the Results Transformers ability to transform the results
	 * file into an input file.
	 * <h2>Input</h2>
	 * <ul>
	 * <li>The file ./data/test/results_5_5.xml that contains the results for a
	 * (5,5) code.</li>
	 * </ul>
	 * 
	 * <h2>Expected Output</h2>
	 * The file ./data/test/results_5_5_transformed.txt
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTransformer() throws Exception {
		logger.info("Begining Testing of Result File Transformation");
		ResultsTransformer rt = new ResultsTransformer(
				"./data/test/results_5_5.xml", "./data/tmp.txt");
		rt.save();

		logger.info("Getting Results to check");
		BufferedReader br = new BufferedReader(new FileReader("./data/tmp.txt"));
		String res = "";
		String tmp = "";
		while ((tmp = br.readLine()) != null)
			res += tmp;

		String results = res.replace("\t", "").replace(" ", "").replace("\n",
				"").trim();

		logger.info("Getting Expected Results");
		BufferedReader br2 = new BufferedReader(new FileReader(
				"./data/test/results_5_5_transformed.txt"));
		String ep = "";
		tmp = "";
		while ((tmp = br2.readLine()) != null)
			ep += tmp;

		String expected_results = ep.replace("\t", "").replace(" ", "")
				.replace("\n", "").trim();

		assertTrue(expected_results.equalsIgnoreCase(results));
		new File("./data/tmp.txt").delete();
		logger.info("Permutations where saved correctly");

		logger.info("End of Test");

	}

	/**
	 * \test Tests the Results Transformers ability to extract the Hamming
	 * Distance and Number of Codewords from the results file.
	 * <h2>Input</h2>
	 * <ul>
	 * <li>The file ./data/test/results_5_5.xml that contains the results for a
	 * (5,5) code.</li>
	 * </ul>
	 * 
	 * <h2>Expected Output</h2>
	 * <ul>
	 * <li>Hamming Distance = 5</li>
	 * <li>Number of Codewords = 5</li>
	 * </ul>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testExtractionOfDetails() throws Exception {
		logger.info("Begin Testing of Detail Extraction");
		ResultsTransformer rt = new ResultsTransformer(
				"./data/test/results_5_5.xml");

		assertEquals(rt.getHammingDistanceUsed(), 5);
		logger.info("Hamming Distance Equal");
		assertEquals(rt.getNumberCodeWords(), 5);
		logger.info("Number of CodeWords Equal");
		logger.info("End of Test");
	}
}
