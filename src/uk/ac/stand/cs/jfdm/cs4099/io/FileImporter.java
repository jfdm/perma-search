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
/**\package uk.ac.stand.cs.jfdm.cs4099.io
 * 
 * Contains classes used to facilitate the import and export of data to and from the program.
 * 
 */
package uk.ac.stand.cs.jfdm.cs4099.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutation;
import uk.ac.stand.cs.jfdm.cs4099.utils.Log;

/**
 * Imports a list of permutations contained in a simple text file.
 * 
 * @author jfdm
 * @version 2
 * 
 * Added support for files containing list constructed via automorphisms.
 */
public class FileImporter {

	/**
	 * Used to log various aspects of the objects operation.
	 */
	private Logger logger = Log.getLogger(FileImporter.class.getName());

	/**
	 * Used to read in the files contents.
	 */
	private BufferedReader file_reader = null;

	/**
	 * Contains the size of the permutations.
	 */
	private int size = 0;
	/**
	 * Contains the string description of the automorphism group.
	 */
	private String autogroup_descrip = "";
	/**
	 * Holds the size of the automorphism group.
	 */
	private int auto_size = 0;

	/**
	 * Contains the generators of the Automorphism Group.
	 */
	private Collection<IPermutation> generators = null;

	/**
	 * Denotes if automorphism was used to construct list.
	 */
	private boolean autogroup_present = false;

	/**
	 * Creates a new FileImporter object used to input the permutations and the
	 * construction details to the program.
	 * 
	 * @param filename
	 *            The name of the file containing the permutations.
	 * @throws FileNotFoundException
	 *             If the file does not exist.
	 */
	public FileImporter(String filename) throws FileNotFoundException {
		logger.info("Importing Permutation information");
		file_reader = new BufferedReader(new FileReader(filename));

		extractMetaData(file_reader);
	}

	/**
	 * Returns a textual description of the Automorphism group.
	 * 
	 * @return A string.
	 */
	public String getAutogroup_descrip() {
		return autogroup_descrip;
	}

	/**
	 * Returns the generators of the automorphism group.
	 * 
	 * @return A <code>Collection</code> of <code>IPermutations</code>
	 */
	public Collection<IPermutation> getGenerators() {
		return generators;
	}

	/**
	 * Returns a boolean value indicating is automorphism group was used for
	 * construction.
	 * 
	 * @return A <code>boolean</code>
	 *         <ul>
	 *         <li>True - if one was present</li>
	 *         <li>False - otherwise</li>
	 *         </ul>
	 */
	public boolean wasAutoGroupUsed() {
		return autogroup_present;
	}

	/**
	 * Returns the size of the automorphism group.
	 * 
	 * @return An <code>int</code>
	 */
	public int getSizeAutoGroup() {
		return auto_size;
	}

	/**
	 * Returns the size of the permutation used.
	 * 
	 * @return An <code>int</code>.
	 */
	public int getPermutationSize() {
		return size;
	}

	/**
	 * Reads in a Permutation from the input file and returns null if one cannot
	 * be found.
	 * 
	 * @return One of the following:
	 *         <ul>
	 *         <li>IPermutation if line is of the form '[1,2,...,n]'</li>
	 *         <li>null if other wise</li>
	 *         </ul>
	 */
	public IPermutation readInPermutation() {
		String line = "";
		try {
			line = file_reader.readLine();
			if (line != null)
				if (line.startsWith(InputFile.PERMUTATION_ID))
					return this.extractPermutation(line);
				else
					return null;
			else
				return null;
		} catch (NullPointerException npe) {
			logger.error("End of File", npe);
			return null;
		} catch (IOException e) {
			logger.debug("Error in reading permutation.", e);
			return null;
		}

	}

	/**
	 * Terminates the Reader used to read in the file.
	 */
	public void closeFileStream() {
		try {
			file_reader.close();
		} catch (IOException e) {
			logger.error("Error Closing File Stream", e);
		}
	}

	/**
	 * Extracts the MetaData from the input file.
	 * 
	 * @param file_reader
	 *            Contains the content of the file.
	 */
	private void extractMetaData(BufferedReader file_reader) {
		logger.info("Extracting Metadata");
		String line = "";
		generators = new LinkedList<IPermutation>();
		do {
			try {
				line = file_reader.readLine();

				if (line.startsWith(InputFile.FILE_METADATA_SIZE)) {

					size = extractSize(line);
					logger.info("Extracted Size = " + size);

				} else if (line.startsWith(InputFile.FILE_METADATA_AUTOGROUP)
						&& !line
								.startsWith(InputFile.FILE_METADATA_AUTOGROUP_SIZE)) {

					if (!line.endsWith(InputFile.FILE_METADATA_AUTOGROUP_NONE)) {
						autogroup_descrip = extractAutoGroupDescription(line);
						autogroup_present = true;
					}

				} else if (line.startsWith(InputFile.FILE_METADATA_GENERATORS)) {

					String[] temp = line.split(InputFile.FILE_METADATA_EQUALS);
					generators.add(extractPermutation(temp[1]));

				} else if (line
						.startsWith(InputFile.FILE_METADATA_AUTOGROUP_SIZE))
					auto_size = extractAutoGroupSize(line);

			} catch (IOException e) {
				logger.error("Error Reading Line", e);
			}

		} while (!line.equals(InputFile.FILE_METADATA_END));

	}

	/**
	 * Extracts the size of the automorphism group.
	 * 
	 * @param line
	 *            The line that contains the size.
	 * @return The size of the automorphism group.
	 */
	private int extractAutoGroupSize(String line) {
		return extractSize(line);
	}

	/**
	 * Extracts a permutation from a line.
	 * 
	 * @param line
	 *            The line that contains the permutation.
	 * @return An <code>IPermutation</code> object.
	 */
	private IPermutation extractPermutation(String line) {
		String elements_str = line.substring(1, line.length() - 1);
		return IPermutation.Factory.createPermutation(elements_str
				.split(InputFile.PERMUTATION_ELEMENT_SEPARATOR));
	}

	/**
	 * Extracts the Automorphism groups string description from the line
	 * 
	 * @param line
	 *            The line that contains the string description.
	 * @return The textual description of the automorphism group.
	 */
	private String extractAutoGroupDescription(String line) {
		String[] line_array = line.split(InputFile.FILE_METADATA_EQUALS);
		return line_array[1].trim();
	}

	/**
	 * Extracts the Metadata information regarding the Size of a set from the
	 * line.
	 * 
	 * @param line
	 *            The line that contains the Size information.
	 * @return The size of a permutation.
	 */
	private int extractSize(String line) {
		String[] line_array = line.split(InputFile.FILE_METADATA_EQUALS);
		return Integer.parseInt(line_array[1].trim());
	}
}
