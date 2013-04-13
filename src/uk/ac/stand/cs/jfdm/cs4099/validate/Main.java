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
 * \package uk.ac.stand.cs.jfdm.cs4099.validate
 *  Contains a program used to validate the results of the SearchSystem by expanding 
 *  the results to their base permutations and performing a simple search through the list.
 */
package uk.ac.stand.cs.jfdm.cs4099.validate;

import org.apache.log4j.Logger;

import uk.ac.stand.cs.jfdm.cs4099.grouptheory.IPermutationCode;
import uk.ac.stand.cs.jfdm.cs4099.utils.CmdLineArgs;
import uk.ac.stand.cs.jfdm.cs4099.utils.Log;

/**
 * Main launchpoint for the <code>ValidateSearch</code> program that takes a
 * results file created by the SearchSystem and expands the permutation code to
 * its constituent permutations and searches through those for the same code
 * that the results file represents and compares the expected number of
 * codewords to be found against the number of codewords found as a result of
 * the search.
 * 
 * <h2>Program Usage</h2>
 * <ul>
 * <li>\verbatim-f<results file>\endverbatim</li>
 * </ul>
 * 
 * @author jfdm
 * @version
 * 
 */
public class Main {
	/**
	 * Used to log various aspects of the objects operation.
	 */
	public static Logger logger = Log.getLogger(Main.class.getName());
	/**
	 * Denotes the flag used to specifiy the input file.
	 */
	public static final String FLAG_INPUT_FILE = "-f";

	/**
	 * Contains the user input checks and is the launch point of the project.
	 * 
	 * @param args
	 *            The command line arguments.
	 */
	public static void main(String[] args) {
		CmdLineArgs arg = new CmdLineArgs();

		// if no args throw hissy fit
		if (args.length > 0) {
			try {
				arg.processArgs(args);
				String input_file = arg.getArg(FLAG_INPUT_FILE);
				ValidateSearch vs = new ValidateSearch(input_file);
				vs.performSearch();
				vs.compareResults();
			} catch (NumberFormatException nfe) {
				logger
						.error("Hamming Distance and Expected Results must be a number");
				usage();
			} catch (Exception e) {
				logger.error("Error performing results");
				usage();
			}
		} else {
			logger.error("No Inputs");
			usage();
		}
	}

	/**
	 * Displays usage information.
	 */
	public static void usage() {
		logger.info("Usage Information:");
		logger.info("Required");
		logger.info("     " + FLAG_INPUT_FILE + "<input filename>");
	}

}
