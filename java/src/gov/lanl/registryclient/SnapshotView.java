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

import gov.lanl.registryclient.parser.Metadata;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

/**
 * Snapshot view returns a snapshot of a registry at the requested time This
 * snapshot is static and should not be changed afterwards, a registry may have
 * multiple snapshot and they are all maintained by mapping table
 * <code>snapshotIndex</code> <p/>
 * 
 * a snapshot is periodically pruned from cache. However this behaviour is
 * invisible to users of this class, because same request will dynamically
 * create a cache again. <p/>
 * 
 * SnapshotView is used by federator, or other applications which need a
 * snapshot of repostiorty.
 * 
 * @author liu_x
 * 
 */
public class SnapshotView <T extends Metadata>{
    public static long DAY = 24 * 60 * 60 * 1000;

    private long pruneInterval = 0;

    RegistryInterface<T> registry;

    private Map<String, Map<String, RegistryRecord<T>>> snapshotIndex;

    private Map<String, Date> lastAccessTime;

    /**
     * constructor
     * 
     * @param registry
     */

    public SnapshotView(RegistryInterface<T> registry) {
        this(registry, DAY);

    }

    public SnapshotView(RegistryInterface<T> registry, long interval) {
        this.registry = registry;
        pruneInterval = interval;
        snapshotIndex = new HashMap<String, Map<String, RegistryRecord<T>>>();
        lastAccessTime = new HashMap<String, Date>();
    }

    /**
     * get a snapshot
     * 
     * @param until
     *            until date, usually issued by oai request.
     * 
     */
    public synchronized Map<String, RegistryRecord<T>> getSnapshot(String from,
            String until, String setSpec, String prefix)
            throws RegistryException {
        String key = from + until + setSpec + prefix;
        if (snapshotIndex.get(key) == null) {
            prune();
            Map<String, RegistryRecord<T>> snapshot = registry.listRecords(from,
                    until, setSpec, prefix);
            snapshotIndex.put(key, snapshot);
        }
        lastAccessTime.put(key, new Date());
        return snapshotIndex.get(key);
    }

    public long getPruneInterval() {
        return pruneInterval;
    }

    public void setPruneInterval(long interval) {
        this.pruneInterval = interval;
    }

    Map<String, Map<String, RegistryRecord<T>>> getSnapshotIndex() {
        return snapshotIndex;
    }

    Map<String, Date> getLastAccessTime() {
        return lastAccessTime;
    }

    /**
     * 
     * Periodically the tapeindex should be pruned, otherwise system will never
     * release memory used by previous harvester.
     */
    private synchronized void prune() {
        long currenttime = new Date().getTime();
        for (Map.Entry<String, Date> entry : lastAccessTime.entrySet()) {
            if (currenttime - entry.getValue().getTime() >= pruneInterval) {
                snapshotIndex.remove(entry.getKey());
                lastAccessTime.remove(entry.getKey());
            }
        }
    }
}
