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
package uk.ac.stand.cs.jfdm.cs4099.io.test;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.junit.Test;

import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutation;
import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutationCode;
import uk.ac.stand.cs.jfdm.cs4099.io.ResultsExporter;
import uk.ac.stand.cs.jfdm.cs4099.utils.Log;

/**
 * Tests various aspects of the ResultsExporter Class. These aspects include:
 * <ul>
 * <li>adding Hamming Distance </li>
 * <li>adding Permutation Code</li>
 * <li>inclusion of the Automorphism used</li>
 * </ul>
 * 
 * @author jfdm
 * @version 1
 * 
 */
public class ResultsExporterTest {

	/*
	 * Used to log various aspects of the objects operation.
	 */
	private Logger logger = Log.getLogger(ResultsExporterTest.class.getName());
	/*
	 * Contains the permutation code used in the test.
	 */
	private IPermutationCode test_coding = null;
	/*
	 * Hamming Distance used in the test.
	 */
	private int hamming_distance = 3;
	/*
	 * No of elements used in the permutation.
	 */
	private int no_elements = 5;

	/**
	 * \test Tests the ResultsExporter Class ability to export a simple
	 * permutation code.
	 * 
	 * <h2>Input</h2>
	 * 
	 * A Permutation Code with the following permutations.
	 * <ul>
	 * <li> 54321</li>
	 * <li> 12534</li>
	 * <li> 12345</li>
	 * </ul>
	 * 
	 * <h2>Expected Output</h2>
	 * <ul>
	 * <li>True</li>
	 * </ul>
	 */
	@Test
	public void testExportationOfSimpleResults() {
		logger.info("Begin Testing of exportation ");
		try {
			test_coding = IPermutationCode.Factory.createSimplePermutationCode(
					hamming_distance, no_elements);
			test_coding
					.addNewPermutation(IPermutation.Factory
							.createPermutation(new String[] { "5", "4", "3",
									"2", "1" }));
			test_coding
					.addNewPermutation(IPermutation.Factory
							.createPermutation(new String[] { "1", "2", "5",
									"3", "4" }));
			test_coding
					.addNewPermutation(IPermutation.Factory
							.createPermutation(new String[] { "1", "2", "3",
									"4", "5" }));

			logger.info("Permutation Code created");

			StringWriter string_writer = new StringWriter();
			ResultsExporter re = new ResultsExporter(new BufferedWriter(
					string_writer));

			logger.info("ResultsExporter and results stream created.");

			logger.info("Adding Permutation Code info");
			re.addResultsInformation(4, hamming_distance, no_elements);
			logger.info("Adding Permutation Code");
			re.addIPermutationCode(test_coding);

			logger.info("Saving to Stream");
			re.save();
			String results = string_writer.toString();

			results = results.replace("\t", "").replace(" ", "").replace("\n",
					"").trim();

			logger.info("Checking contents of saved data.");
			BufferedReader br = new BufferedReader(new FileReader(
					"./data/test/expected_simple_results.xml"));
			String ep = "";
			String tmp = "";
			while ((tmp = br.readLine()) != null)
				ep += tmp;

			String expected_results = ep.replace("\t", "").replace(" ", "")
					.replace("\n", "").trim();

			assertTrue(expected_results.equalsIgnoreCase(results));
			logger.info("Permutations where saved correctly");
		} catch (Exception e) {
			logger.error("Error Reading results back in", e);
		}

		logger.info("Test complete");
	}

	/**
	 * \test Tests the ResultsExporter Class ability to export a complex
	 * permutation code.
	 * 
	 * <h2>Input</h2>
	 * A Automorphism Group with the following info.
	 * <ul>
	 * <li> String Description = C5</li>
	 * <li> Generator = 23451</li>
	 * </ul>
	 * A Permutation Code with the following permutations.
	 * <ul>
	 * <li> 54321</li>
	 * <li> 12345</li>
	 * </ul>
	 * Other Information
	 * <ul>
	 * <li> Hamming Distance 3</li>
	 * <li> Number of Elements 5</li>
	 * </ul>
	 * 
	 * <h2>Expected Output</h2>
	 * <ul>
	 * <li>True</li>
	 * </ul>
	 */
	@Test
	public void testExportationOfComplexResults() {
		logger.info("Begin Testing of exportation of Permutations");
		try {
			IPermutation gen = IPermutation.Factory
					.createPermutation(new byte[] { 2, 3, 4, 5, 1 });
			String auto_descrip = "C5";
			Collection<IPermutation> generators = new LinkedList<IPermutation>();
			generators.add(gen);
			logger.info("Automorphism Info  created");

			test_coding = IPermutationCode.Factory
					.createComplexPermutationCode(hamming_distance,
							no_elements, new IPermutation[] { gen });
			test_coding
					.addNewPermutation(IPermutation.Factory
							.createPermutation(new String[] { "5", "4", "3",
									"2", "1" }));
			test_coding
					.addNewPermutation(IPermutation.Factory
							.createPermutation(new String[] { "1", "2", "3",
									"4", "5" }));
			logger.info("Complex Permutation Code created");

			StringWriter string_writer = new StringWriter();
			ResultsExporter re = new ResultsExporter(new BufferedWriter(
					string_writer));

			logger.info("ResultsExporter and results stream created.");
			logger.info("Adding Permutation Code Information");
			re.addResultsInformation(10, hamming_distance, no_elements);
			logger.info("Adding Automorphism Information");
			re.addAutoGroupInfo(auto_descrip, generators, 5);

			logger.info("Adding Permutation Code");
			re.addIPermutationCode(test_coding);

			logger.info("Saving to Stream");
			re.save();
			String results = string_writer.toString();

			results = results.replace("\t", "").replace(" ", "").replace("\n",
					"");

			logger.info("Checking contents of saved data.");
			BufferedReader br = new BufferedReader(new FileReader(
					"./data/test/expected_complex_results.xml"));
			String ep = "";
			String tmp = "";
			while ((tmp = br.readLine()) != null)
				ep += tmp;

			String expected_results = ep.replace("\t", "").replace(" ", "")
					.replace("\n", "");

			assertTrue(expected_results.equalsIgnoreCase(results));
			logger.info("Permutations where saved correctly");
		} catch (Exception e) {
			logger.error("Error Reading results back in", e);
		}

		logger.info("Test complete");
	}
}
