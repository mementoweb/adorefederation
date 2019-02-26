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

package gov.lanl.disseminator;

import gov.lanl.disseminator.util.ConfigManager;
import gov.lanl.disseminator.util.Stream2Array;
import gov.lanl.util.properties.GenericPropertyManager;

import java.io.FileReader;

import junit.framework.TestCase;

public abstract class DmtAbstractTestCase extends TestCase {
	protected String homedir; // home directory

	protected String exdir; // example directory

	protected String didlxml; // sample didl

	protected String marcxml; // sample marc

	protected GenericPropertyManager loader;

	protected String contentId = "info:doi/10.1016/S0140-6736(04)16640-3";

	protected String dsId = "info:lanl-repo/ds/8ccef322-5a0b-4907-9de3-9147fd283a6c";

	protected String didId = "info:lanl-repo/i/7ac69d14-21ab-11da-9de5-c11b6cd85594";

	public DmtAbstractTestCase() throws Exception {
		initProperties();
	}

	public DmtAbstractTestCase(String testName) throws Exception {
		super(testName);
		initProperties();
	}

	protected void initProperties() throws Exception {
		loader = ConfigManager.getPropertyManager();
		homedir = loader.getProperty("HOMEDIR");
		exdir = homedir + "/ex/";
		didlxml = Stream2Array.read(new FileReader(exdir + "/sampledidl.xml"));
		marcxml = Stream2Array.read(new FileReader(exdir + "/samplemarc.xml"));
	}
}
