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

package gov.lanl.adore.helper;

import gov.lanl.util.resource.Resource;
import java.util.Properties;
import junit.framework.TestCase;

public class ResourceManagerTest extends TestCase {
    Properties props;

    String sampleContentId = "info:doi/10.1016/j.ejor.2004.02.028";
    String sampleDSId="info:lanl-repo/ds/f75b1028-63c5-4445-83f6-ee5d2f5222fc";
    String sampleDid="info:lanl-repo/i/d78d9758-bddf-11d9-9de5-c11b6cd85594";

    protected void setUp() throws Exception {
        super.setUp();
        props = new Properties();
        props.setProperty(ResourceManager.ADORE_ID_LOCATOR_RESOLVERURL,
                "http://cox.lanl.gov:8080/adore-id-locator/resolver");
        props.setProperty(ResourceManager.ADORE_SERVICE_REGISTRY_OAIURL,
                "http://cox.lanl.gov:8080/Registry/oaicat/OAIHandler");
    }

    public void testGetDIDL() throws Exception {
        ResourceManager manager = new ResourceManager(props);
        Resource resource = manager.getResource(sampleContentId, ResourceManager.TYPE_XMLTAPE);
        assertTrue(resource.getBytes().length > 0);
        assertTrue(new String(resource.getBytes()).contains("<didl:DIDL"));
    }

    public void testGetDataStream() throws Exception{
    	 ResourceManager manager = new ResourceManager(props);
         Resource resource = manager.getResource(sampleDSId, ResourceManager.TYPE_ARCFILE);
         assertTrue(resource.getBytes().length > 0);
    }
    
    public void testType() throws Exception{
   	 	ResourceManager manager = new ResourceManager(props);
   	 	assertEquals(ResourceManager.TYPE_XMLTAPE,manager.getResourceType(sampleContentId));
   	 	assertEquals(ResourceManager.TYPE_ARCFILE,manager.getResourceType(sampleDSId));
    }
    
    public void testLocatorList() throws Exception{
    	ResourceManager manager=new ResourceManager(props);
    	assertEquals(1, manager.getLocations(sampleContentId).size());
    	assertEquals(2,manager.getLocations(sampleDSId).size());
    	assertEquals(1, manager.getLocations(sampleDid).size());
    
    }
    
    
}
