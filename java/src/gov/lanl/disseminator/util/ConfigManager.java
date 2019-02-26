/*
 * Copyright (c) 2008  Los Alamos National Security, LLC.
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

package gov.lanl.disseminator.util;

import gov.lanl.util.properties.GenericPropertyManager;
import gov.lanl.util.properties.PropertiesUtil;
import org.apache.log4j.Logger;

/**
 * ConfigManger is introduced to initialize the property only once
 * 
 * usually config should be done in servlet.init() method, but we don't have
 * direct control of servlet in oom-j framework.
 * 
 * @author liu_x
 * 
 */
public final class ConfigManager {
	private static GenericPropertyManager loader;

	private static Logger logger = Logger.getLogger(ConfigManager.class
			.getName());

	static {
		loader = new GenericPropertyManager();
		try {
			loader
					.addAll(PropertiesUtil
							.loadConfigByCP("etc/adore.properties"));
		} catch (Exception ex) {
			logger.fatal(ex);
		}
	}

	public static GenericPropertyManager getPropertyManager() {
		return loader;
	}

}
