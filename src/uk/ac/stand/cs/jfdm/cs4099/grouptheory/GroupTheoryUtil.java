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
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;

import uk.ac.stand.cs.jfdm.cs4099.grouptheory.impl.Permutation;

/**
 * Contains various utility Methods that are used to aid in the
 * 
 * @author jfdm
 * @version 1
 * 
 */
public class GroupTheoryUtil {
	/**
	 * Used in the expansion of a representative.
	 */
	private static LinkedHashSet<IPermutation> expanded = new LinkedHashSet<IPermutation>();
	/**
	 * Used in the expansion of a representative.
	 */
	private static Queue<IPermutation> tmp_q = new LinkedList<IPermutation>();

	/**
	 * Multiplies two permutations together.
	 * 
	 * @param r
	 *            A Permutation.
	 * @param g
	 *            The other Permutation.
	 * @return An <code>IPermutation</code> containing the result.
	 */
	public static IPermutation multiply(IPermutation r, IPermutation g) {

		byte[] results = new byte[r.size()];
		byte[] elements = r.getArray();

		int counter = 0;

		for (int i : g.getArray())
			results[i - 1] = elements[counter++];

		return new Permutation(results);
	}

	/**
	 * Checks to see if the two permutations are far enough apart so they can be
	 * used. As soon as the distance between the permutations is greater than
	 * the needed distances it returns a boolean.
	 * 
	 * @param permutation1
	 *            A Permutation.
	 * @param permutation2
	 *            Another Permutation.
	 * @param hamming_distance
	 *            The minimum Hamming Distance, needed.
	 * @return A <code>boolean</code>
	 *         <ul>
	 *         <li>True - if distance greater than or equal to Hamming distance
	 *         </li>
	 *         <li>False - if not</li>
	 *         </ul>
	 */
	public static boolean checkDistance(IPermutation permutation1,
			IPermutation permutation2, int hamming_distance) {

		byte[] perm1 = permutation1.getArray();
		byte[] perm2 = permutation2.getArray();

		int h_distance = 0;
		for (int i = 0; i < perm1.length; i++) {
			if (perm1[i] != perm2[i])
				h_distance++;

			if (h_distance >= hamming_distance)
				return true;
		}

		return false;

	}

	/**
	 * Calculates the Hamming Distance between two permutations.
	 * 
	 * @param permutation1
	 *            A permutation.
	 * @param permutation2
	 *            Another permutation.
	 * @return An <code>Integer</code> containing the Hamming Distance.
	 */
	public static int calculateHammingDistance(IPermutation permutation1,
			IPermutation permutation2) {

		byte[] perm1 = permutation1.getArray();
		byte[] perm2 = permutation2.getArray();

		int h_distance = 0;
		for (int i = 0; i < perm1.length; i++) {
			if (perm1[i] != perm2[i])
				h_distance++;
		}

		return h_distance;
	}

	/**
	 * Checks the orbit for feasibility with the given Hamming distance.
	 * 
	 * @param orbit
	 *            The orbit to be checked.
	 * @param hamming_distance
	 *            The minimum ditance needed.
	 * @return A <code>boolean</code>
	 *         <ul>
	 *         <li>True - if feasible </li>
	 *         <li>False - if not</li>
	 *         </ul>
	 */
	public static boolean isOrbitFeasible(IPermutation[] orbit,
			int hamming_distance) {

		boolean feasible = false;
		for (int i = 0; i < orbit.length; i++) {
			for (int k = i + 1; k < orbit.length; k++) {
				feasible = checkDistance(orbit[i], orbit[k], hamming_distance);
				if (!feasible)
					break;
			}
			if (!feasible)
				break;
		}
		return feasible;
	}

	/**
	 * Expands the representative using the generators of the prescribed
	 * automorphism.
	 * 
	 * @param representative
	 *            The IPermutation to be expanded.
	 * @param generators
	 *            The generators of the automorphism group.
	 * @return A <code>Collection</code> of <code>IPermutation</code>.
	 */
	public static IPermutation[] expand(IPermutation representative,
			Collection<IPermutation> generators) {
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