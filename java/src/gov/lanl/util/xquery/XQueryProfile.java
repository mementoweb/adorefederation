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

package gov.lanl.util.xquery;

import java.util.HashMap;
import java.util.Properties;

public class XQueryProfile {
    private static final String DEFAULT_TAPE_PATH = "/ta:tape/ta:tapeRecord/ta:record";
    private HashMap<String, String> nsProfiles = new HashMap<String, String>();

    private String profileId;
    private String xQuery;
    private String filterPath;
    private Properties props;

    public HashMap<String, String> getNamespaceProfiles() {
        return nsProfiles;
    }

    public void addNamespaceProfile(String prefix, String ns) {
        nsProfiles.put(prefix, ns);
    }

    public String getFilterPath() {
        if (filterPath != null)
            return filterPath;
        else
            return DEFAULT_TAPE_PATH;
    }

    public void setFilterPath(String filterPath) {
        this.filterPath = filterPath;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getXQuery() {
        return xQuery;
    }

    public void setXQuery(String query) {
        xQuery = query;
    }

    public Properties getProperties() {
        return props;
    }

    public void setProperties(Properties props) {
        this.props = props;
    }
}
