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

import junit.framework.TestCase;
import java.util.Map;

public class SnapshotViewTest extends TestCase {
    DummyRegistry registry = null;

    SnapshotView view = null;

    public void setUp() throws Exception {
        registry = new DummyRegistry();
        view = new SnapshotView(registry);
    }

    public void testGetSnapshot() throws Exception {
        Map<String, RegistryRecord> map = view.getSnapshot(null, null, null,
                "oai_dc");
        assertEquals(DummyRegistry.ID1, map.get(DummyRegistry.ID1)
                .getIdentifier());
        assertEquals(DummyRegistry.ID2, map.get(DummyRegistry.ID2)
                .getIdentifier());
    }

    public void testNullRecord() throws Exception {
        Map<String, RegistryRecord> map = view.getSnapshot(null, null, null,
                "oai_dc");
        assertFalse(map.containsKey(DummyRegistry.NO_EXIST_ID));
        assertTrue(map.containsKey(DummyRegistry.ID1));

    }

    public void testGetsnapshotWithUntil() throws Exception {
        String time = "2000-01-01";
        Map<String, RegistryRecord> map = view.getSnapshot(null, time, null,
                "oai_dc");
        assertEquals(time, map.get(time).getIdentifier());
        assertEquals(1, registry.getListAccessTimes());
    }

    public void testSameRequest() throws Exception {
        String time = "2000-01-01";
        for (int i = 0; i < 10; i++) {
            view.getSnapshot(null, time, null, "oai_dc");
        }
        assertEquals(1, registry.getListAccessTimes());
    }

    public void testDifferentRequest() throws Exception {
        for (int i = 0; i < 10; i++) {
            view.getSnapshot(null, Integer.toString(i), null, "oai_dc");
        }
        assertEquals(10, registry.getListAccessTimes());
    }

    public void testPrune() throws Exception {
        view = new SnapshotView(registry, 10);
        Map<String, RegistryRecord> map = view.getSnapshot(null, null, null,
                "oai_dc");
        assertEquals(1, view.getSnapshotIndex().size());
        Thread.sleep(11);
        map = view.getSnapshot(null, "2000-01-01", null, "oai_dc");

        // null,null,null should be pruned at this time
        assertEquals(1, view.getSnapshotIndex().size());

        view.setPruneInterval(10000);
        map = view.getSnapshot(null, null, null, "oai_dc");
        assertEquals(2, view.getSnapshotIndex().size());
        Thread.sleep(11);
        map = view.getSnapshot(null, "2000-01-01", null, "oai_dc");
        assertEquals(2, view.getSnapshotIndex().size());
    }

}
