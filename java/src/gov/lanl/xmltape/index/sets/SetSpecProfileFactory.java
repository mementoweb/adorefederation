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

package gov.lanl.xmltape.index.sets;

import java.util.Properties;

/**
 * Factory to create a SetSpecProfile from values defined in a
 * properties files.
 * 
 * @author rchute
 *
 */
public class SetSpecProfileFactory {

	private static final String PROFILE_NAME = "profile.name";

	private static final String PROFILE_NS = "profile.namespace";
    
    private static final String PROFILE_NS_PREFIX = "profile.namespace.prefix";

	private static final String PROFILE_XPATH = "profile.xpath";

	private static final String PROFILE_XPATH_PREFIX = "profile.xpath.prefix";
    
    public static int iterations = 20;

	public static SetSpecProfile generateSetSpecProfile(Properties props) {
        SetSpecProfile profile = new SetSpecProfile();
		profile.setProfileName(props.getProperty(PROFILE_NAME));
		for (int i = 0; i < iterations; i++) {
			if (props.containsKey(PROFILE_NS + "." + i)) {
				if (props.getProperty(PROFILE_NS + "." + i) != null) {
                     SetSpecNamespace ssn = new SetSpecNamespace();
                     ssn.setNamespace(props.getProperty(PROFILE_NS + "." + i));
					if (props.getProperty(PROFILE_NS_PREFIX + "." + i) != null) {
						ssn.setNamespacePrefix(props.getProperty(PROFILE_NS_PREFIX + "." + i));
					}
					profile.addSetSpecNamespace(ssn);
				}
			}
            if (props.containsKey(PROFILE_XPATH + "." + i)) {
                if (props.getProperty(PROFILE_XPATH + "." + i) != null) {
                    SetSpecXPath ssx = new SetSpecXPath();
                    ssx.setXpath(props.getProperty(PROFILE_XPATH + "." + i));
                    if (props.getProperty(PROFILE_XPATH_PREFIX + "." + i) != null) {
                        ssx.setXpathPrefix(props.getProperty(PROFILE_XPATH_PREFIX + "." + i));
                    }
                    profile.addSetSpecXPaths(ssx);
                }
            }
		}

		return profile;
	}

}
