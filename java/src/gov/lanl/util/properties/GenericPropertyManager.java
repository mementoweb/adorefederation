/*
 * Copyright (c) 2007  Los Alamos National Security, LLC.
 *
 * Los Alamos National Laboratory
 * Research Library
 * Digital Library Research & Prototyping Team
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * 
 */

package gov.lanl.util.properties;

import java.util.Properties;

/**
 * A generic property loader, it can be moved ot gov.lanl.util if other project
 * requires it.
 * 
 * In aDORe environment, a property can be set in mutliple ways: <p/> (a) load
 * from properties file in classpath <p/> (b) load from properties file with
 * full path <p/> (c) direct set/get <p/> (d) read from system environment <p/>
 * (e) read from system property <p/>
 * 
 * Though any combinations are possible, GenericPropertyLoader try to generalize
 * a bit with following algorithms: <p/> (i)create a properties cache <p/>
 * (ii)user can choose setup from any combinations of (a),(b),(c), new load
 * takes precedence <p/> (iii) user can get any property from the cache, <p/>
 * however, in case no valid match exists, system willl try to load from system
 * environment, and if failed, load from system property, and cache the
 * property.
 * 
 */

public class GenericPropertyManager {

	private Properties props;

	public GenericPropertyManager() {
		props = new Properties();
	}

	/**
	 * Get the property manager object
	 * 
	 * @return all properties
	 */
	public Properties getProperties() {
		return props;
	}

    /**
     * Sets a new key/value pair
     */
	public void setProperty(String key, String value) {
		props.put(key, value);
	}

    /**
     * Adds all properties to the property manager
     * @param prop
     */
	public void addAll(Properties prop) {
		props.putAll(prop);
	}

	/**
     * Gets configuration value <p/> This operation is by system property first,
     * if no system property setup, the value is to be readed from environment
     * 
     * @param key
     *            property key name
     * @return value if available in property manager or java system
     */
	public String getProperty(String key) {

		// Format key to OS ENV Parameter format
		// s/[^a-z0-9]/_/g

		String standardKey = key.trim().replaceAll("[^a-zA-Z0-9]", "_")
				.toUpperCase();
		if (props.getProperty(key) != null)
			return props.getProperty(key);
		else if (props.getProperty(standardKey) != null)
			return props.getProperty(standardKey);
		// Check if available in Java System Properties
		else if (System.getProperty(key) != null)
			return System.getProperty(key);
		else if (System.getProperty(standardKey) != null)
			return System.getProperty(standardKey);
		else if (System.getenv(key) != null)
			return System.getenv(key);
		else if (System.getenv(standardKey) != null)
			return System.getenv(standardKey);
		else
			throw new NullPointerException(key + " is not configured");

	}
}
