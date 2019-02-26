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

import java.util.*;

/**
 * Implements an Least Recently Used (LRU) cache Manager.
 * 
 * @param max_cache
 *            the maximum cache size that will be kept in the cache.
 */
public class RecordAccessorCacheManager {
	private LinkedHashMap<String, RecordAccessor> cacheMap; // For fast search/remove

	private final int max_cache;

	private static final float loadFactor = 0.75F;

	private static final boolean accessOrder = true; 

	/** The class constructor */
	public RecordAccessorCacheManager(int max_cache) {
		this.max_cache = max_cache;
		this.cacheMap = new LinkedHashMap<String, RecordAccessor>(max_cache, loadFactor, accessOrder) {
			private static final long serialVersionUID = 1;

			protected synchronized boolean removeEldestEntry(Map.Entry<String, RecordAccessor> eldest) {
				if (size() > RecordAccessorCacheManager.this.max_cache) {
					eldest.getValue().close();
				}
				return size() > RecordAccessorCacheManager.this.max_cache;
			};
		};
	}

	public synchronized RecordAccessor put(String key, RecordAccessor val) {
		return cacheMap.put(key, val);
	}

	public synchronized RecordAccessor remove(String key) {
		cacheMap.get(key).close();
		return cacheMap.remove(key);
	}

	public synchronized RecordAccessor get(String key) {
		return cacheMap.get(key);
	}

	public synchronized boolean containsKey(String key) {
		return cacheMap.containsKey(key);
	}

	public synchronized int size() {
		return cacheMap.size();
	}

	public synchronized void clear() {
		cacheMap.clear();
	}
}