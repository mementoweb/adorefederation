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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import gov.lanl.registryclient.parser.Metadata;

import org.apache.log4j.*;

/**
 * CachedView is a typical use case for taperegistry,arcregistry,formatregistry.
 * If there is a match, the cache returns the result; otherwise the cache will
 * refresh.
 * <p>
 * In this use case, <code>from</code>,<code>until</code>, and
 * <code>set</code> are usually not a concern, because a client is only
 * interested in <em>current</em> registry status.
 * 
 * This simple cache mechanism is only applicable to
 * 
 * @author liu_x
 * 
 */
public class CachedView <T extends Metadata>{
    RegistryInterface registry;

    String prefix = null;
    String setSpec=null;

    Map<String, RegistryRecord<T>> cache = new HashMap<String, RegistryRecord<T>>();

    Logger logger = Logger.getLogger(CachedView.class);
    
    FutureTask<String> future = null;
    ExecutorService executor = null;

    public CachedView(RegistryInterface<T> registry, String setSpec, String prefix) {
        this.registry = registry;
        this.prefix = prefix;
        this.setSpec = setSpec;
    }

    public RegistryRecord<T> getRecord(String identifier)
            throws RegistryException, ItemNotExist {
    	
    	RegistryRecord<T> r = null;
	    if ((r = cache.get(identifier)) == null) {
            r = registry.getRecord(identifier, prefix);
            cache.put(identifier, r);
	    }
	    
    	return r;
    }
}