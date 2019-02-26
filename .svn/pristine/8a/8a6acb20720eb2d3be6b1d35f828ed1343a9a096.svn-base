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

package gov.lanl.ockham.client.oai;

import gov.lanl.ockham.client.app.Registry;
import gov.lanl.ockham.iesrdata.IESRCollection;
import gov.lanl.ockham.iesrdata.IESRService;
import gov.lanl.registryclient.RegistryRecord;
import gov.lanl.ockham.OckhamAbstractTestCase;

import java.net.URL;
import java.util.HashSet;
import java.util.Map;

public class OckhamOAIRegistryTest extends OckhamAbstractTestCase {
	Registry registry;

	OckhamOAIRegistry oairegistry = null;

	public OckhamOAIRegistryTest() throws Exception {
		super();
	}

	public void setUp() throws Exception {
		registry = new Registry(new URL(putbaseurl));
		IESRCollection coll = new IESRCollection();
		coll.setIdentifier("info:lanl-repo/xmltape/123");
		coll.setContentRange("2000-01-01");
		coll.setTitle("ISI 2000");
		HashSet<String> isPartOf = new HashSet<String>();
		isPartOf.add("info:sid/isi");
		coll.setIsPartOf(isPartOf);
		HashSet<String> types = new HashSet<String>();
		types.add("xmltape");
		coll.setTypes(types);

		HashSet<String> serves = new HashSet<String>();
		serves.add("http://info.info/oaipmh/123");
		serves.add("http://info.info/openurl/123");
		coll.setServices(serves);
		registry.put(coll);

		IESRService service = new IESRService();
		service.setIdentifier("http://info.info/oaipmh/123");
		service.setLocator("http://example.com");
		service.setServes("info:lanl-repo/xmltape/123");
		service.setSupportsStandard("OAI-PMH");
		service.setTitle("oaipmh interface");
		service.setType("oai-pmh");
		registry.put(service);

		oairegistry = new OckhamOAIRegistry(oaibaseurl);

	}

	public void testListCollections() throws Exception {
		Map<String, RegistryRecord<IESRCollection>> collections = oairegistry.listCollections(
				null, null);
		for (Map.Entry<String, RegistryRecord<IESRCollection>> entry : collections.entrySet()) {
			assertTrue(entry.getValue().getMetaData() instanceof gov.lanl.ockham.iesrdata.IESRCollection);
		}
		assertNotNull(collections.get("info:lanl-repo/xmltape/123"));
	}

	public void testListServices() throws Exception {
		Map<String, RegistryRecord<IESRService>> services = oairegistry.listServices(null,
				null);
		for (Map.Entry<String, RegistryRecord<IESRService>> entry : services.entrySet()) {
			assertTrue(entry.getValue().getMetaData() instanceof gov.lanl.ockham.iesrdata.IESRService);
		}
		assertNotNull(services.get("http://info.info/oaipmh/123"));
	}

	public void tearDown() throws Exception {
		registry.delete("info:lanl-repo/xmltape/123");
	}

}
