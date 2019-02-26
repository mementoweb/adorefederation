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

package gov.lanl.arc.registry;

import gov.lanl.arc.ARCEnvConfig;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Registry.java
 * 
 * ARC File Registry, maintain information about arcfile, its location, indexes,
 * and other configuration for oaicat
 */

public abstract class Registry {
    //tape map should be initialized by subclass, either by file system
    //or arc file registry

    HashMap tapemap;

    public Registry() {
        tapemap = new HashMap();
    }

    public ARCEnvConfig getARCConfig(String tapename) throws RegistryException {
        if (tapemap.get(tapename) == null)
            tapemap.put(tapename,readARCConfig(tapename));

        return (ARCEnvConfig) (tapemap.get(tapename));
    }

    /**
     * read tape configuration, implemented by underlying system This is mostly
     * used for freshness check purpose.
     */
    abstract ARCEnvConfig readARCConfig(String tapename) throws RegistryException;

    public Iterator iterator() {
        return tapemap.values().iterator();
    }

}
