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
/**\package uk.ac.stand.cs.jfdm.cs4099.utils
 * 
 * Contains Classes that provided extra functionality to the program.
 * 
 */
package uk.ac.stand.cs.jfdm.cs4099.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Used to process command line arguments given to the Main Method. Arguments
 * used with flags should be of the form -f<value>, where f is the flag.
 * 
 * @author jfdm
 * @version 2
 * 
 */
public class CmdLineArgs {
	/**
	 * Contain the entered arguments.
	 */
	private Map<String, String> map = new HashMap<String, String>();

	/**
	 * Processes the command line arguments.
	 * 
	 * @param args
	 *            The command line arguments.
	 */
	public void processArgs(String[] args) {
		for (String s : args) {
			String flag = "";
			String value = "";
			if (s.length() == 2) {
				flag = s;
				value = "";
			} else if (s.length() > 1) {
				flag = s.substring(0, 2);
				value = s.substring(2);

			}
			map.put(flag, value);
		}
	}

	/**
	 * Get the argument denoted by the flag.
	 * 
	 * @param flag
	 *            The flag of the argument.
	 * @return A <code>String</code> containing the value.
	 */
	public String getArg(String flag) {
		return map.get(flag);
	}

}
