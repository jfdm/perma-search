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
package uk.ac.stand.cs.jfdm.cs4099.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutation;
import uk.ac.stand.cs.jfdm.cs4099.utils.Log;

/**
 * Used to denote the input file and its contents, used as part of the Results
 * Transformer Class. The static variables are also used in the FileImporter
 * 
 * @author jfdm
 * @version 1
 * 
 */
public class InputFile {

	/**
	 * Used in the text file to denote a line that contains a permutation.
	 */
	static final String PERMUTATION_ID = "[";
	/**
	 * Separator used to distinguish between elements in the permutation.
	 */
	static final String PERMUTATION_ELEMENT_SEPARATOR = ",";
	/**
	 * Denotes the line that contains the permutations size.
	 */
	static final String FILE_METADATA_SIZE = "#Size";
	/**
	 * Denotes the line that contains a generator.
	 */
	static final String FILE_METADATA_GENERATORS = "#Generators";
	/**
	 * Denotes the line that contains the Automorphism Group description.
	 */
	static final String FILE_METADATA_AUTOGROUP = "#AutoGroup";
	/**
	 * Denotes the results that specifies no prescribed automorphism.
	 */
	static final String FILE_METADATA_AUTOGROUP_NONE = "NONE";
	/**
	 * Denotes the size of the automorphism group.
	 */
	static final String FILE_METADATA_AUTOGROUP_SIZE = "#AutoGroupSize";
	/**
	 * Denotes the Start of the Metadata region of the file.
	 */
	static final String FILE_METADATA_BEGIN = "#BeginMetadata";
	/**
	 * Denotes the End of the Metadata region of the file.
	 */
	static final String FILE_METADATA_END = "#EndMetadata";
	/**
	 * Used to identify assignment in the file.
	 */
	static final String FILE_METADATA_EQUALS = "=";
	/**
	 * Used to log various aspects of the objects operation.
	 */
	private Logger logger = Log.getLogger(InputFile.class.getName());
	/**
	 * Contains the results of the search.
	 */
	private Collection<IPermutation> permutations;
	/**
	 * The automorphism group information.
	 */
	private Collection<IPermutation> auto_group_generators;
	/**
	 * The size of the permutation.
	 */
	private int size;
	/**
	 * The automorphism group information.
	 */
	private String auto_group_info;
	/**
	 * The size of the automorphism group.
	 */
	private int auto_group_size;
	/**
	 * The PrintWriter used to save the information.
	 */
	private PrintWriter save_file;

	/**
	 * Creates a new Input File.
	 */
	public InputFile() {
		permutations = new LinkedList<IPermutation>();
		auto_group_generators = new LinkedList<IPermutation>();
	}

	/**
	 * Save the Input file information to file.
	 * 
	 * @param filename
	 *            The name of the file to save to.
	 * 
	 * @return The Writer used save the information to file.
	 */
	public PrintWriter save(String filename) {
		logger.info("Saving Information");
		try {
			save_file = new PrintWriter(new BufferedWriter(new FileWriter(
					filename)), true);
			save_file.println(FILE_METADATA_BEGIN);
			save_file.println(FILE_METADATA_SIZE.concat(FILE_METADATA_EQUALS)
					.concat(String.valueOf(size)));

			try {
				save_file.println(FILE_METADATA_AUTOGROUP.concat(
						FILE_METADATA_EQUALS).concat(auto_group_info));
				save_file.println(FILE_METADATA_AUTOGROUP_SIZE.concat(
						FILE_METADATA_EQUALS).concat(
						String.valueOf(auto_group_size)));

				for (IPermutation g : auto_group_generators) {
					save_file.println(FILE_METADATA_GENERATORS.concat(
							FILE_METADATA_EQUALS).concat(g.toString()));
				}
			} catch (Exception e) {
				logger.error("No Autogroup Information exists, skipping", e);
			}
			save_file.println(FILE_METADATA_END);
			save_file.flush();

			for (IPermutation p : permutations) {
				save_file.println(p.toString());
				save_file.flush();
			}
			save_file.flush();
			save_file.close();
			logger.info("Information Saved");
		} catch (Exception e) {
			logger.error("Error Saving File", e);
		}
		return save_file;
	}

	/**
	 * Add the permutations to the input file.
	 * 
	 * @param perm
	 *            The permutations to be added.
	 */
	public void addPermutation(Collection<IPermutation> perm) {
		permutations = perm;
	}

	/**
	 * 
	 * @return
	 */
	public Collection<IPermutation> getPermutations() {
		return permutations;
	}

	/**
	 * 
	 * @return
	 */
	public Collection<IPermutation> getGenerators() {
		return auto_group_generators;
	}

	/**
	 * Add Generators to the input file.
	 * 
	 * @param gen
	 *            The generators to be added.
	 */
	public void addGenerator(Collection<IPermutation> gen) {
		auto_group_generators = gen;
	}

	/**
	 * Set the size of the permutations.
	 * 
	 * @param size
	 *            The size.
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Set the Textual Description of the Automorphism Group.
	 * 
	 * @param auto_group_info
	 *            `the textual Description.
	 */
	public void setAuto_group_info(String auto_group_info) {
		this.auto_group_info = auto_group_info;
	}

	/**
	 * Set the size of the automorphism groups orbit.
	 * 
	 * @param auto_group_size
	 *            The size of the orbit.
	 */
	public void setAuto_group_size(int auto_group_size) {
		this.auto_group_size = auto_group_size;
	}

	/**
	 * Return the size of the permutations.
	 * 
	 * @return An integer.
	 */
	public int getSize() {
		return size;
	}

}
