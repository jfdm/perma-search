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
/**
 * \package uk.ac.stand.cs.jfdm.cs4099.grouptheory
 * 
 * Contains the interfaces for the permutation and permutation code representation classes.
 */
package uk.ac.stand.cs.jfdm.cs4099.grouptheory;

import uk.ac.stand.cs.jfdm.cs4099.grouptheory.impl.Permutation;

/**
 * Represents a permutation within the Java Program.
 * 
 * @author jfdm
 * @version 1
 * 
 */
public interface IPermutation {
	/**
	 * Returns the permutation as an int array.
	 * 
	 * @return An <code>int</code> array containing the permutation.
	 */
	public byte[] getArray();

	/**
	 * Returns the Size of the permutation.
	 * 
	 * @return An <code>int</code> containing the size.
	 */
	public int size();

	/**
	 * Used to create new IPermutation objects.
	 * 
	 * @author jfdm
	 * @version 2
	 * 
	 */
	public class Factory {
		/**
		 * Creates a new Permutation.
		 * 
		 * @param elements
		 *            The elements of the permutation.
		 * @return A new <code>Permutation</code>
		 */
		public static IPermutation createPermutation(String[] elements) {
			return new Permutation(elements);
		}

		/**
		 * Creates a new Permutation.
		 * 
		 * @param elements
		 *            The elements of the permutation.
		 * @return A new <code>Permutation</code>.
		 */
		public static IPermutation createPermutation(byte[] elements) {
			return new Permutation(elements);
		}
	}
}