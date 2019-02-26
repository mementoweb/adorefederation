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

package gov.lanl.ockham;

import gov.lanl.util.properties.PropertiesUtil;
import junit.framework.TestCase;

public abstract class OckhamAbstractTestCase extends TestCase {

    protected String putbaseurl;

    protected String oaibaseurl;

    public OckhamAbstractTestCase() throws Exception {
        initProperties();
    }

    public OckhamAbstractTestCase(String testName) throws Exception {
        super(testName);
        initProperties();
    }

    protected void initProperties() throws Exception {
        putbaseurl = PropertiesUtil.getGlobalProperty(ServiceRegistryConstants.PUT_BASEURL);
        oaibaseurl = PropertiesUtil.getGlobalProperty(ServiceRegistryConstants.OAI_BASEURL);
    }
}
