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

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

import gov.lanl.registryclient.parser.OAIDCMetadata;

/**
 * A dummy registry for testing purpose only
 * 
 * 
 * @author liu_x
 * 
 */

class DummyRegistry implements RegistryInterface {
    public final static String NO_EXIST_ID = "no_exist_id";

    public final static String ID1 = "001";

    public final static String ID2 = "002";

    private int listAccessTimes = 0;

    private int getAccessTimes = 0;

    /**
     * dynamically generate a record in all cases, except the idenfier's value
     * is no_exist_id
     * 
     */

    public RegistryRecord getRecord(String identifier, String prefix)
            throws RegistryException, ItemNotExist {
        getAccessTimes++;
        if (NO_EXIST_ID.equals(identifier))
            throw new ItemNotExist(identifier);
        else {
            OAIDCMetadata meta = new OAIDCMetadata();
            Set<String> identifiers = new HashSet<String>();
            identifiers.add(" dc identifier " + identifier);
            meta.setIdentifiers(identifiers);
            return new RegistryRecord(identifier, identifier, meta);
        }
    }

    /**
     * generate at least two dummy record at any case, if there is an until
     * value, dummyregistry generates a third record.
     * 
     * 
     */
    public Map<String, RegistryRecord> listRecords(String from, String until,
            String setSpec, String prefix) throws RegistryException {
        listAccessTimes++;
        Map<String, RegistryRecord> records = new HashMap<String, RegistryRecord>();
        OAIDCMetadata meta = new OAIDCMetadata();
        Set<String> identifiers = new HashSet<String>();
        identifiers.add(ID1);
        meta.setIdentifiers(identifiers);
        records.put(ID1, new RegistryRecord(ID1, ID1, meta));

        OAIDCMetadata meta2 = new OAIDCMetadata();
        Set<String> identifiers2 = new HashSet<String>();
        identifiers2.add(ID2);
        meta2.setIdentifiers(identifiers2);
        records.put(ID2, new RegistryRecord(ID2, ID2, meta2));

        if (until != null) {
            OAIDCMetadata meta3 = new OAIDCMetadata();
            Set<String> identifiers3 = new HashSet<String>();
            identifiers3.add(until);
            meta3.setIdentifiers(identifiers3);
            records.put(until, new RegistryRecord(until, until, meta3));
        }

        return records;
    }

    public int getListAccessTimes() {
        return listAccessTimes;
    }

    public int getGetAccessTimes() {
        return getAccessTimes;
    }

}
