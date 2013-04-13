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

import java.io.FileNotFoundException;
import java.util.Collection;

import org.apache.log4j.Logger;

import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutation;
import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutationCode;
import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutationCodeException;
import uk.ac.stand.cs.jfdm.cs4099.io.FileImporter;
import uk.ac.stand.cs.jfdm.cs4099.utils.Log;

/**
 * Performs the search for a permutation code.
 * 
 * @author jfdm
 * @version 2
 * 
 */
public class SearchClass {
	/**
	 * Used to log various aspects of the objects operation.
	 */
	private Logger logger = Log.getLogger(SearchClass.class.getName());
	/**
	 * Used to extract the information from the input file.
	 */
	private FileImporter file = null;

	/**
	 * Represents the permutation code that is constructed as a result of the
	 * search process.
	 */
	private IPermutationCode pcode = null;

	/**
	 * Creates a new search for a permutation code.
	 * 
	 * @param filename
	 *            The file containing the permutations.
	 * @param hamming_distance
	 *            The hamming distance of the code to search for.
	 * @throws FileNotFoundException
	 *             If there was trouble reading the input file.
	 * @throws SearchException
	 *             If the input file was invalid.
	 * 
	 */
	public SearchClass(String filename, int hamming_distance)
			throws FileNotFoundException, SearchException {
		logger.info("Creating new Search Class");
		file = new FileImporter(filename);

		validateFile(file, hamming_distance);
		if (file.wasAutoGroupUsed()) {

			pcode = IPermutationCode.Factory.createComplexPermutationCode(
					hamming_distance, file.getPermutationSize(), file
							.getGenerators().toArray(new IPermutation[] {}));
		} else {
			pcode = IPermutationCode.Factory.createSimplePermutationCode(
					hamming_distance, file.getPermutationSize());
		}
	}

	/**
	 * Performs a series of checks on the input file to determine if it is
	 * usable.
	 * 
	 * @param filename
	 *            The file containing the permutations.
	 * @param hamming_distance
	 *            The hamming distance of the code to search for.
	 * @throws SearchException
	 *             If the input file was invalid.
	 */
	private void validateFile(FileImporter file, int hamming_distance)
			throws SearchException {
		if (file.getPermutationSize() < hamming_distance) {

			throw new SearchException(
					"Hamming Distance Specified is greater than the Permutation Size");

		} else if (file.wasAutoGroupUsed() && file.getGenerators().size() == 0)
			throw new SearchException(
					"File is inconsistent - implies prescribed automorphism but no generators");

		for (IPermutation p : file.getGenerators())
			if (p.size() != file.getPermutationSize())
				throw new SearchException(
						"Generators are not the same size as indicated");

	}

	/**
	 * Searches for a permutation code by adding the permutations from the file
	 * to the permutation code object and returning the permutation code once it
	 * has finished adding the permutations.
	 * 
	 * @return A <code>IPermutationCode</code> containing the results of the
	 *         search.
	 * @throws SearchException
	 *             If search not feasible.
	 */
	public IPermutationCode performSearch() throws SearchException {
		logger.info("Performing Search");
		IPermutation permutation = null;

		while ((permutation = file.readInPermutation()) != null)
			try {
				pcode.addNewPermutation(permutation);
			} catch (IPermutationCodeException e) {
				logger.error("Permutation List is not feasible", e);
				throw new SearchException("Search not Feasible");
			}

		file.closeFileStream();
		logger.info("Finished Search and returning results.");
		return pcode;
	}

	/**
	 * Returns the size of the Permutations.
	 * 
	 * @return An <code>Integer</code>
	 */
	public int getPermutationSize() {
		return file.getPermutationSize();
	}

	/**
	 * Checks to see is an Automorphism Group was specified.
	 * 
	 * @return A <code>boolean</code>
	 *         <ul>
	 *         <li>true - if one was used</li>
	 *         <li>False - if other wise</li>
	 *         </ul>
	 */
	public boolean wasAutoGroupUsed() {
		return file.wasAutoGroupUsed();
	}

	/**
	 * Returns the String Description of the auotmorphism group.
	 * 
	 * @return A <code>String</code>.
	 */
	public String getAuto_group_descrip() {
		return file.getAutogroup_descrip();
	}

	/**
	 * Returns the size of the automorphism group.
	 * 
	 * @return An int.
	 */
	public int getAutoGroupSize() {
		return file.getSizeAutoGroup();
	}

	/**
	 * Returns the Generators used by the automorphism Group.
	 * 
	 * @return the auto_group_generators
	 */
	public Collection<IPermutation> getAuto_group_generators() {
		return file.getGenerators();
	}

	/**
	 * Returns the total number of codewords found in the search.
	 * 
	 * @return An <code>Integer</code>
	 */
	public int getNumberOfCodeWords() {
		if (file.wasAutoGroupUsed()) {
			return pcode.getNumberOfCodeWords() * file.getSizeAutoGroup();
		} else
			return pcode.getNumberOfCodeWords();
	}

}
