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

package gov.lanl.disseminator.adore.didl2model;

import gov.lanl.disseminator.DmtAbstractTestCase;
import gov.lanl.disseminator.model.Entity;

/**
 * Test DIDL2Entity converter The test is based on a didl example under
 * ex/sampledidl.xml
 * 
 * @author liu_x
 * 
 */
public class DIDL2EntityTest extends DmtAbstractTestCase {

	public DIDL2EntityTest() throws Exception {
		super();
	}

	public void testParse() throws Exception {
		DIDL2Entity d2e = new DIDL2Entity(didlxml);
		Entity entity = d2e.getEntity();
		assertNotNull(entity);
		assertEquals(didId, entity.getProperty(Entity.IDENTIFIER_ATT));
		assertFalse(entity.isDataStream());

		assertEquals(1, entity.getEntities().size());
		assertFalse(entity.getEntities().get(0).isDataStream());
		assertEquals(3, entity.getEntities().get(0).getEntities().size());
		assertEquals(2, entity.getEntities().get(0).getEntities().get(0)
				.getEntities().size());
		assertNotNull(entity.getEntities().get(0).getEntities().get(0)
				.getEntities().get(0).getProperty(Entity.REF_ATT));
		assertTrue(entity.getEntities().get(0).getEntities().get(0)
				.getEntities().get(0).isDataStream());
		assertNotNull(entity.getEntities().get(0).getEntities().get(0)
				.getEntities().get(0).getContent());
	}
}
