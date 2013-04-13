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
package uk.ac.stand.cs.jfdm.cs4099.grouptheory.impl;

import org.apache.log4j.Logger;

import uk.ac.stand.cs.jfdm.cs4099.grouptheory.GroupTheoryUtil;
import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutation;
import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutationCode;
import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutationCodeException;
import uk.ac.stand.cs.jfdm.cs4099.utils.Log;

/**
 * Contains a implementation of the <code>IPermutationCode</code> class that
 * compares the Hamming Distance of two <code>IPermutation</code> objects when
 * adding to the code.
 * 
 * @author jfdm
 * @version 1
 * 
 * 
 */
public class SimplePermutationCode extends AbstractPermutationCode implements
		IPermutationCode {

	/**
	 * Used to log various aspects of the objects operation.
	 */
	private static Logger logger = Log.getLogger(SimplePermutationCode.class
			.getName());

	/**
	 * Creates a new Permutation Code.
	 * 
	 * @param hamming_distance
	 *            Minimum Hamming Distance.
	 * @param no_elements
	 *            Number of elements in a codeword.
	 */
	public SimplePermutationCode(int hamming_distance, int no_elements) {
		super(hamming_distance, no_elements);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutationCode#addNewPermutation(uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutation)
	 */
	public boolean addNewPermutation(IPermutation permutation)
			throws IPermutationCodeException {
		boolean can_add = true;

		if (coding.size() > 0)
			for (IPermutation p : coding)

				if (!(can_add = GroupTheoryUtil.checkDistance(permutation, p,
						hamming_distance))) {
					break;
				}

		if (can_add) {
			coding.add(permutation);
			logger.info("Added Permutation " + permutation.toString());
		}
		return can_add;
	}
}
