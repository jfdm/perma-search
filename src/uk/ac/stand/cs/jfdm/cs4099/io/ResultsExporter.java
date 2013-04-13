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
import java.io.IOException;
import java.util.Collection;

import org.apache.log4j.Logger;

import nl.tue.win.riaca.openmath.io.OMXMLWriter;
import nl.tue.win.riaca.openmath.lang.OMApplication;
import nl.tue.win.riaca.openmath.lang.OMInteger;
import nl.tue.win.riaca.openmath.lang.OMString;
import nl.tue.win.riaca.openmath.lang.OMSymbol;
import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutation;
import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutationCode;
import uk.ac.stand.cs.jfdm.cs4099.utils.Log;

/**
 * Used to export the results of the search to an openmath format to be sent to
 * a <code>BufferedWriter</code>.
 * 
 * @author jfdm
 * @version 2
 * 
 */
public class ResultsExporter {

	/**
	 * Used to log various aspects of the objects operation.
	 */
	private Logger logger = Log.getLogger(ResultsExporter.class.getName());
	/**
	 * Denotes the default filename used for the results file.
	 */
	public static final String DEFAULT_FILENAME = "results.xml";
	/**
	 * Pre-processing of XML document.
	 */
	private static final String XML_VERSION_1_0_ENCODING_UTF_8 = "<?xml version='1.0' encoding='UTF-8'?>";
	/**
	 * Used to write the OpenMath Objects to a file.
	 */
	private OMXMLWriter writer = null;
	/**
	 * Performs the writing of objects to a file.
	 */
	private BufferedWriter toStream = null;

	/**
	 * Creates a new ResultsExporter to save the Results.
	 * 
	 * @param bw
	 *            The stream to save to.
	 * @throws java.io.IOException
	 *             If there are problems writing to the stream.
	 */
	public ResultsExporter(BufferedWriter bw) throws IOException {
		this.toStream = bw;
		setup(bw);
	}

	/**
	 * Saves the results to a file.
	 * 
	 * @param filename
	 *            The name of the file to save to.
	 * @throws java.io.IOException
	 *             If there are problems setting up the file.
	 */
	public ResultsExporter(String filename) throws IOException {
		toStream = new BufferedWriter(new FileWriter(filename, false));
		setup(toStream);
	}

	/**
	 * Helper method that sets up the initial code fragments of the file and
	 * creates the OpenMath Object Writer.
	 * 
	 * @param bw
	 *            The stream used to write the results.
	 * @throws java.io.IOException
	 *             If there are problems writing to the stream.
	 */
	private void setup(BufferedWriter bw) throws IOException {
		bw.write(XML_VERSION_1_0_ENCODING_UTF_8);
		bw.write("\n<OMOBJ>\n");
		writer = new OMXMLWriter(bw, true, false);

	}

	/**
	 * Saves the Results to closes the writers.
	 * 
	 * @return The stream used to write the results.
	 */
	public BufferedWriter save() {
		try {
			toStream.write("</OMOBJ>\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			logger.error("Error writing to stream", e);
		}
		logger.info("Data saved to Stream");
		return toStream;
	}

	/**
	 * Adds the number of codewords and Hamming distance to the results file.
	 * 
	 * @param no_codewords
	 *            The number of code words found.
	 * @param distance
	 *            The hamming distance.
	 * @param size_permutations
	 *            The size of the permutations to be searched for.
	 * @throws IOException
	 *             If there where problems saving the Size of the Permutation
	 *             Code.
	 */
	public void addResultsInformation(int no_codewords, int distance,
			int size_permutations) throws IOException {
		OMApplication app = new OMApplication();
		app.addElement(new OMSymbol("CS4099_MajorSP_openmath_cd", "pcodeinfo"));
		app.addElement(new OMString("Size of Permutations"));
		app.addElement(new OMInteger(size_permutations));
		app.addElement(new OMString("Hamming Distance"));
		app.addElement(new OMInteger(distance));
		app.addElement(new OMString("Number of Codewords Found"));
		app.addElement(new OMInteger(no_codewords));
		writer.writeObject(app);
		writer.flush();
		logger.info("Exporting Number of Codewords " + no_codewords);
		logger.info("Exporting Hamming Distance " + distance);
	}

	/**
	 * Adds the permutation code to the OpenMath Object.
	 * 
	 * @param pcode
	 *            The permutation code to add.
	 * @throws IOException
	 *             If there where problems saving the Permutation Code.
	 */
	public void addIPermutationCode(IPermutationCode pcode) throws IOException {
		writer.write("<OMA>\n");
		writer.writeObject(new OMSymbol("list1", "List"));
		writer.writeObject(new OMString("Permutations"));
		writer.flush();

		for (IPermutation perm : pcode.getCodeWords()) {
			writer.writeObject(createIPermutationOpenMathObject(perm));
			writer.flush();
		}

		writer.write("</OMA>\n");
		writer.flush();
		logger.info("Exported Permutations");

	}

	/**
	 * Creates an OpenMath representation of a Permutation from an IPermutation
	 * object.
	 * 
	 * @param permutation
	 *            The permutation to be converted.
	 * @return An <code>OMApplication</code> object.
	 */
	private OMApplication createIPermutationOpenMathObject(
			IPermutation permutation) {
		OMApplication om_permutation = new OMApplication();

		om_permutation.addElement(new OMSymbol("permut1", "Permutation"));

		for (int i : permutation.getArray()) {
			om_permutation.addElement(new OMInteger(i));
		}

		return om_permutation;

	}

	/**
	 * Used to add the automorphism group information to the output file.
	 * 
	 * @param auto_group_descrip
	 *            A textual description of the Automorphism Group.
	 * @param auto_group_generators
	 *            The generators of the Automorphism Group.
	 * @param size
	 *            The size of the automorphism group.
	 * @throws IOException
	 *             If there where problems saving the Permutation Code.
	 */
	public void addAutoGroupInfo(String auto_group_descrip,
			Collection<IPermutation> auto_group_generators, int size)
			throws IOException {
		writer.write("<OMA>\n");
		writer.writeObject(new OMSymbol("CS4099_MajorSP_openmath_cd",
				"autogroupinfo"));

		writer.writeObject(new OMString("Automorphism Group Information"));
		writer.writeObject(new OMString(auto_group_descrip));

		writer.writeObject(new OMString("Automorphism Group Size"));
		writer.writeObject(new OMInteger(size));

		writer.flush();

		writer.write("<OMA>\n");
		writer.writeObject(new OMSymbol("list1", "List"));
		writer.writeObject(new OMString("Generators"));

		for (IPermutation p : auto_group_generators) {
			writer.writeObject(createIPermutationOpenMathObject(p));
			writer.flush();
		}
		writer.write("</OMA>\n</OMA>\n");
		writer.flush();
		logger.info("Exported Automorphism Group Information");

	}
}
