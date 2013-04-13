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

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.log4j.Logger;

import uk.ac.stand.cs.jfdm.cs4099.grouptheory.GroupTheoryUtil;
import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutation;
import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutationCode;
import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutationCodeException;
import uk.ac.stand.cs.jfdm.cs4099.utils.Log;

/**
 * Represents a Complex Permutation Code that is constructed using automorphism
 * groups.
 * 
 * @author jfdm
 * @version 1
 * 
 */
public class ComplexPermutationCode extends AbstractPermutationCode implements
		IPermutationCode {

	/**
	 * Used to log various aspects of the objects operation.
	 */
	private static Logger logger = Log.getLogger(ComplexPermutationCode.class
			.getName());
	/**
	 * Represents the automorphism group.
	 */
	private IPermutation[] generators = null;
	/**
	 * Used in the expansion of a representative.
	 */
	private LinkedHashSet<IPermutation> expanded = new LinkedHashSet<IPermutation>();
	/**
	 * Used in the expansion of a representative.
	 */
	private Queue<IPermutation> tmp_q = new LinkedList<IPermutation>();

	/**
	 * Constructs a new Permutation code that uses Automorphisms to help reduce
	 * the search space.
	 * 
	 * @param hamming_distance
	 *            The minimum distance between permutations.
	 * @param no_elements
	 *            The number of elements in each permutation.
	 * @param generators
	 *            The generators of the prescribed automorphism group.
	 */
	public ComplexPermutationCode(int hamming_distance, int no_elements,
			IPermutation[] generators) {
		super(hamming_distance, no_elements);
		this.generators = generators;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutationCode#addNewPermutation(uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutation)
	 */
	public boolean addNewPermutation(IPermutation permutation)
			throws IPermutationCodeException {
		boolean can_add = true;

		IPermutation[] orbit = expand(permutation);
		if (GroupTheoryUtil.isOrbitFeasible(orbit, hamming_distance)) {

			// if not first check for compatibility
			if (coding.size() > 0) {

				// if orbit compatible with other code words add it other wise
				// leave it
				for (IPermutation rep : coding) {
					for (IPermutation o : orbit) {
						if (!(can_add = GroupTheoryUtil.checkDistance(o, rep,
								hamming_distance))) {
							break;
						}
					}
					if (!can_add) {
						break;
					}
				}
			}
		} else {
			logger.warn("List not Feasible");
			throw new IPermutationCodeException(
					"Permutation List if not feasible for this Search");
		}

		if (can_add) {
			coding.add(permutation);
			logger.info("Added Permutation " + permutation.toString());
		}
		return can_add;
	}

	/**
	 * Expands the representative using the generators of the prescribed
	 * automorphism.
	 * 
	 * @param representative
	 *            The IPermutation to be expanded.
	 * @return A <code>Collection</code> of <code>IPermutation</code>.
	 */
	public IPermutation[] expand(IPermutation representative) {
		expanded.clear();
		tmp_q.clear();
		IPermutation x, z;
		tmp_q.add(representative);
		expanded.add(representative);

		while (tmp_q.isEmpty() == false) {
			x = tmp_q.poll();

			for (IPermutation y : generators) {
				z = GroupTheoryUtil.multiply(x, y);
				if (expanded.contains(z) == false) {
					tmp_q.add(z);
					expanded.add(z);
				}
			}
		}
		return expanded.toArray(new IPermutation[] {});
	}
}