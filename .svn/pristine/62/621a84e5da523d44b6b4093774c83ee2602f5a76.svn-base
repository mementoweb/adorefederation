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

package gov.lanl.ockham.client.app;

import java.net.URL;

import java.util.HashSet;

import gov.lanl.ockham.iesrdata.IESRCollection;
import gov.lanl.ockham.OckhamAbstractTestCase;

public class RegistryTest extends OckhamAbstractTestCase {

	Registry registry;

	IESRCollection coll;

	public RegistryTest() throws Exception {
		super();
	}

	public void setUp() throws Exception {

		registry = new Registry(new URL(putbaseurl));
		coll = new IESRCollection();
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

	}

	public void testPut() throws Exception {
		assertTrue(registry.put(coll));
		IESRCollection coll2 = registry.getCollection(coll.getIdentifier());
		assertEquals(coll2.getIdentifier(), coll.getIdentifier());
	}

	public void testDelete() throws Exception {
		registry.delete("info:lanl-repo/xmltape/123");
	}

	public void tearDown() throws Exception {
		registry.delete("info:lanl-repo/xmltape/123");
	}

}
