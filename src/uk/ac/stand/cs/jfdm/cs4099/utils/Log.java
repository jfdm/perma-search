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
package uk.ac.stand.cs.jfdm.cs4099.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Used to aid in the creation of a Logger Class used to log various areas of
 * the system.
 * 
 * @author jfdm
 * @version 2
 * 
 */
public class Log {
	/**
	 * Returns a system logger specific to the class that called the method.
	 * 
	 * @param name
	 *            The name of the class that will use the logger.
	 * @return A new <code>Logger</code>
	 */
	public static Logger getLogger(String name) {
		Logger logger = Logger.getLogger(name);
		DOMConfigurator.configure("./data/log4jConfigFile.xml");
		return logger;

	}
}
