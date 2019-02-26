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

package gov.lanl.xmltape.identifier.index.access;

import gov.lanl.identifier.IndexException;
import gov.lanl.xmltape.identifier.index.IdentifierIndexInterface;
import gov.lanl.xmltape.index.TapeIndexInterface;

import java.util.HashMap;
import java.util.Properties;

public class RecordAccessorRegistry {

	private static final RecordAccessorCacheManager recordAccessorCache = new RecordAccessorCacheManager(50);
	private static final HashMap<String, Integer> cnts = new HashMap<String, Integer>();

    
    public RecordAccessorRegistry() {
    }
    
    public RecordAccessor getRecordAccessor(String containerName) {
        return (RecordAccessor) (recordAccessorCache.get(containerName));
    }
    
    public RecordAccessor getRecordAccessor(Properties props) {
    	return getRecordAccessor(props, null, null);
    }
    
    public RecordAccessor getRecordAccessor(Properties props, TapeIndexInterface index, IdentifierIndexInterface idIdx) {
        String containerName = props.getProperty("RecordAccessor.xmltapeFile");
        if (recordAccessorCache.get(containerName) == null) {
            try {
                RecordAccessor ra = new RandomTapeAccessor();
                ra.init(props, index, idIdx);
                recordAccessorCache.put(containerName, ra);
            } catch (IndexException e) {
                e.printStackTrace();
            }
        }
        return (RecordAccessor) (recordAccessorCache.get(containerName));
    }
}
