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
/**\package uk.ac.stand.cs.jfdm.cs4099.grouptheory.impl
 * 
 * Contains the various implementations and implementation specific classes for the 
 * interfaces defined in the <code>uk.ac.stand.cs.jfdm.cs4099.grouptheory</code> package.
 */
package uk.ac.stand.cs.jfdm.cs4099.grouptheory.impl;

import java.util.ArrayList;
import java.util.Collection;

import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutation;
import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutationCode;

/**
 * Represents the common features that all permutation code implementations
 * should have. They are:
 * <ul>
 * <li>Hamming Distance</li>
 * <li>Size of each Permutation</li>
 * <li>Calculation of Hamming Distance between two permutations</li>
 * </ul>
 * 
 * @author jfdm
 * @version 1
 * 
 */
public abstract class AbstractPermutationCode implements IPermutationCode {

	/**
	 * Represents the minimum hamming distance between words.
	 */
	protected int hamming_distance = 0;
	/**
	 * Number of elements to be in each codeword.
	 */
	protected int no_elements = 0;

	/**
	 * Used to store the permutations.
	 */
	protected Collection<IPermutation> coding = null;

	/**
	 * Creates a new Abstract Permutation Code.
	 * 
	 * @param hamming_distance
	 *            Minimum Hamming Distance.
	 * @param no_elements
	 *            Number of elements in a codeword.
	 */
	public AbstractPermutationCode(int hamming_distance, int no_elements) {
		this.hamming_distance = hamming_distance;
		this.no_elements = no_elements;
		coding = new ArrayList<IPermutation>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutationCode#getMinHammingDistance()
	 */
	public int getMinHammingDistance() {
		return hamming_distance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutationCode#getCodeWordSize()
	 */
	public int getCodeWordSize() {
		return no_elements;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutationCode#getNumberOfCodeWords()
	 */
	public int getNumberOfCodeWords() {
		return coding.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutationCode#getCodeWords()
	 */
	public Collection<IPermutation> getCodeWords() {
		return coding;
	}
}