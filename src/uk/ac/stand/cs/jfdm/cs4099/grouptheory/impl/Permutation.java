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

import uk.ac.stand.cs.jfdm.cs4099.grouptheory.GroupTheoryUtil;
import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutation;

/**
 * Contains the <code>byte</code> array implementation of the IPermutation
 * Interface.
 * 
 * @author jfdm
 * @version 2
 * 
 */
public class Permutation implements IPermutation {

	/**
	 * Represents the permutation.
	 */
	private byte[] permutation = null;

	/**
	 * Constructs a permutation from a string array, where each element in the
	 * array corresponds to an element in the permutation.
	 * 
	 * @param elements
	 *            The elements of the permutation.
	 */
	public Permutation(String[] elements) {
		permutation = new byte[elements.length];
		String tmp = "";
		int index = 0;
		for (String s : elements) {
			tmp += s;
			permutation[index] = (byte) Integer.parseInt(s.trim());
			index++;
		}
	}

	/**
	 * Assign the <code>byte</code> array provided to represent the
	 * permutation.
	 * 
	 * @param elements
	 *            The elements of the permutation.
	 */
	public Permutation(byte[] elements) {
		this.permutation = elements;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutation#getByteArray()
	 */

	public byte[] getArray() {
		return permutation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutation#size()
	 */
	public int size() {
		return permutation.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String str = "[";

		for (int i : permutation)
			str += i + ",";

		return str.substring(0, str.length() - 1) + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		// taken from http://mindprod.com/jgloss/hashcode.html
		int hash = 0;
		for (byte b : permutation) {
			// rotate left
			hash <<= 1;
			if (hash < 0) {
				hash |= 1;
			}

			// xor
			hash ^= b;
		}
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// check if same object
		if (this == obj) {
			return true;
		}

		// check if same type
		if (!(obj instanceof Permutation)) {
			return false;
		}

		// else check numerical equality via hamming distance.
		Permutation other = (Permutation) obj;
		return (!GroupTheoryUtil.checkDistance(this, other, 3));
	}
}