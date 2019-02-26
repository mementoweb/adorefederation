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

package gov.lanl.registryclient;

import gov.lanl.registryclient.parser.OAIDCMetadata;
import junit.framework.TestCase;

/**
 * test cached view with dummyregistry
 * 
 * @author liu_x
 * 
 */
public class CachedViewTest extends TestCase {
    DummyRegistry registry = null;

    CachedView<OAIDCMetadata> view = null;

    public void setUp() throws Exception {
        registry = new DummyRegistry();
        view = new CachedView<OAIDCMetadata>(registry, null,"oai_dc");
    }

    public void testGetrecord() throws Exception {
        assertEquals(DummyRegistry.ID1, view.getRecord(DummyRegistry.ID1)
                .getIdentifier());
        assertEquals(DummyRegistry.ID2, view.getRecord(DummyRegistry.ID2)
                .getIdentifier());
    }

    public void testNullRecord() throws Exception {
        try {
            view.getRecord(DummyRegistry.NO_EXIST_ID);
            assertTrue(false);
        } catch (ItemNotExist ex) {
            assertTrue(true);
        }

    }

    public void testAccessTimes() throws Exception {
        view.getRecord(DummyRegistry.ID1);
        view.getRecord(DummyRegistry.ID2);
        view.getRecord("chinatown");
        view.getRecord("crash");
        assertEquals(1, registry.getListAccessTimes());
        assertEquals(2, registry.getGetAccessTimes());

    }

}
