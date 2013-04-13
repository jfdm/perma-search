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

import java.util.Collection;

import org.apache.log4j.Logger;

import uk.ac.stand.cs.jfdm.cs4099.grouptheory.GroupTheoryUtil;
import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutation;
import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutationCode;
import uk.ac.stand.cs.jfdm.cs4099.io.InputFile;
import uk.ac.stand.cs.jfdm.cs4099.transform.ResultsTransformer;
import uk.ac.stand.cs.jfdm.cs4099.utils.Log;

/**
 * Searches through a results file that was constructed with automorphisms for
 * the permutation code that it is supposed to represent by expanding the code
 * to get all its permutations.
 * 
 * @author jfdm
 * @version 1
 * 
 */
public class ValidateSearch {
	/**
	 * Used to log various aspects of the objects operation.
	 */
	private Logger logger = Log.getLogger(ValidateSearch.class.getName());

	/**
	 * Represents the file that is to be searched.
	 */
	private InputFile inputfile;
	/**
	 * The resulting Permutation code
	 */
	private IPermutationCode pcode;
	/**
	 * The expected result.
	 */
	private int expected_result;

	/**
	 * Creates a new <code>ValidateSearch</code> that searches through the
	 * results file for the permutation code that it is supposed to represent.
	 * 
	 * @param file
	 *            The file containing the Permutation Code that is to be
	 *            validated.
	 * @throws Exception
	 *             If there is a problem accessing the results file.
	 */
	public ValidateSearch(String file) throws Exception {
		logger.info("Initialising new Validation Search");

		ResultsTransformer rt;
		try {
			rt = new ResultsTransformer(file);

			this.expected_result = rt.getNumberCodeWords();
			if ((inputfile = rt.getInput()) != null) {
				pcode = IPermutationCode.Factory.createSimplePermutationCode(rt
						.getHammingDistanceUsed(), inputfile.getSize());
			} else
				throw new Exception();
		} catch (Exception e) {
			logger.error("No Input File Found", e);
			throw new Exception("No Input File Found");
		}

	}

	/**
	 * Performs the validation search, by expanding each of the representatives
	 * and then adds them to the <code>IPermutationCode</code>.
	 * 
	 * @return The results as an <code>IPermutationCode</code>.
	 * @throws Exception
	 */
	public IPermutationCode performSearch() throws Exception {
		logger.info("Performing Validation Search");
		Collection<IPermutation> perms = inputfile.getPermutations();
		Collection<IPermutation> gens = inputfile.getGenerators();
		if (perms != null || gens != null) {
			for (IPermutation coset : perms) {
				IPermutation[] tmp = GroupTheoryUtil.expand(coset, gens);

				for (IPermutation p : tmp) {
					pcode.addNewPermutation(p);
				}
			}
		} else {
			throw new Exception("No Permutations or Generators found");
		}
		return pcode;
	}

	/**
	 * Compares the number of codewords found in the validation to the expected
	 * result.
	 * 
	 * @return A <code>Boolean</code>:
	 *         <ul>
	 *         <li>True - if the same</li>
	 *         <li>False - if different</li>
	 *         </ul>
	 */
	public boolean compareResults() {
		if (pcode.getNumberOfCodeWords() == expected_result) {
			logger.info("Valid Result");
			logger.info("Expected Result = " + expected_result);
			logger.info("Actual Result = " + pcode.getNumberOfCodeWords());
			return true;
		} else {
			logger.info("Invalid Result");
			logger.info("Expected Result = " + expected_result);
			logger.info("Actual Result = " + pcode.getNumberOfCodeWords());
			return false;
		}
	}

}
