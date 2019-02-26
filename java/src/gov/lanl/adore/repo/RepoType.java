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

package gov.lanl.adore.repo;

import gov.lanl.archive.ArchiveCollection;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Plug-in Interface For Repository Specific Registration
 * 
 * @author rchute
 *
 */
public interface RepoType {
    
    /**
     * Register an  ArchiveCollection instance
     * @param collections
     * @throws RepoException
     */
    public void processCollections(ArrayList<ArchiveCollection> collections) throws RepoException;
    
    /**
     * Sets the RepoConfig properties object
     * @param config
     *        an initialized RepoConfig object
     */
    public void setRepoConfig(RepoConfig config);
    
    /**
     * Return status of registered collections
     */
    public LinkedHashMap<String, Boolean> getStatus();
}
