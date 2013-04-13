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
package uk.ac.stand.cs.jfdm.cs4099.grouptheory;

import java.util.Collection;

import org.apache.log4j.Logger;

import uk.ac.stand.cs.jfdm.cs4099.grouptheory.impl.ComplexPermutationCode;
import uk.ac.stand.cs.jfdm.cs4099.grouptheory.impl.SimplePermutationCode;
import uk.ac.stand.cs.jfdm.cs4099.utils.Log;

/**
 * Represents a Permutation Code.
 * 
 * @author jfdm
 * @version 1
 * 
 */
public interface IPermutationCode {
	/**
	 * Returns the minimum Hamming distance between words.
	 * 
	 * @return An <code>int</code>.
	 */
	public int getMinHammingDistance();

	/**
	 * Returns the number of code words in the permutation code.
	 * 
	 * @return An <code>int</code>.
	 */
	public int getNumberOfCodeWords();

	/**
	 * Returns the Codewords of the coding.
	 * 
	 * @return A <code>Collection</code> of <code>IPermutation</code>
	 *         objects.
	 */
	public Collection<IPermutation> getCodeWords();

	/**
	 * Tests to see if the permutation can be added to the coding and if it can
	 * it will be added.
	 * 
	 * @param permutation
	 *            The potentially new permutation to be added.
	 * @return A <code>boolean</code>
	 *         <ul>
	 *         <li>True - if the permutation can be added</li>
	 *         <li>False - if the permutation can not be added.</li>
	 *         </ul>
	 * @throws IPermutationCodeException
	 *             If the permutation list is not suitable for this search.
	 */
	public boolean addNewPermutation(IPermutation permutation)
			throws IPermutationCodeException;

	/**
	 * Returns the size of the permutations used.
	 * 
	 * @return An <code>int</code>.
	 */
	public int getCodeWordSize();

	/**
	 * Used to create new Permutation Codes.
	 * 
	 * @author jfdm
	 * @version 2
	 * 
	 * Added support for Complex Permutation code generation.
	 */
	public class Factory {

		/**
		 * Used to log various aspects of the objects operation.
		 */
		private static Logger logger = Log.getLogger(Factory.class.getName());

		/**
		 * Creates a <code>IPermutationCode</code> that uses a simple
		 * comparison of permutations to check for suitability.
		 * 
		 * @param hamming_distance
		 *            The minimum distance between permutations
		 * @param no_elements
		 *            The number of elements in the Permutation.
		 * @return A new <code>IPermutationCode</code>
		 */
		public static IPermutationCode createSimplePermutationCode(
				int hamming_distance, int no_elements) {
			logger.info("Created new Simple Permutation Code with d = "
					+ hamming_distance + " and n = " + no_elements);
			return new SimplePermutationCode(hamming_distance, no_elements);
		}

		/**
		 * Creates a <code>IPermutatonCode</code> that uses automorphisms to
		 * check various representatives of permutations lists to check for
		 * suitability.
		 * 
		 * @param hamming_distance
		 *            The minimum distance between permutations
		 * @param no_elements
		 *            The number of elements in the Permutation.
		 * @param generators
		 *            The Permutations used to expand the representatives.
		 * @return A new <code>IPermutationCode</code>
		 */
		public static IPermutationCode createComplexPermutationCode(
				int hamming_distance, int no_elements, IPermutation[] generators) {
			logger.info("Created new Complex Permutation Code with d = "
					+ hamming_distance + " and n = " + no_elements);
			return new ComplexPermutationCode(hamming_distance, no_elements,
					generators);
		}
	}
}