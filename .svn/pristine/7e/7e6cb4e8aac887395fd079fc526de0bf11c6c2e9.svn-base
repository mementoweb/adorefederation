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
import gov.lanl.locator.DbLoader;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Abstract Repository Registration Class to be extended by 
 * Repository Type Specific Service Registry Implemenation
 * @author rchute
 *
 */
public abstract class RepoRegister {

    public RepoConfig config;

    private DbLoader idLocator;

    /**
     * Constructor used to initialize the ID Locator db connections;
     * RepoConfig should be passed through from RepoType impl.
     * @param _config
     *        RepoConfig passed through RepoType impl.
     * @throws RepoException
     */
    public RepoRegister(RepoConfig _config) throws RepoException {
        this.config = _config;
        try {
            init();
        } catch (Exception e) {
            throw new RepoException(e.getMessage());
        }
    }

    /**
     * Repository Specific Service Registry Registration Implementation
     * @param c
     *        ArchiveCollection object to be registered
     * @throws RepoException
     */
    public abstract void addToServiceRegistry(ArchiveCollection c) throws RepoException;

    /**
     * Initializes the IDLocator Database Connections
     * @throws Exception
     */
    private void init() throws Exception {
        try {
            Properties idLocDb = new Properties();
            idLocDb.load(new FileInputStream(config.getIdLocatorDbProps()));
            idLocator = new DbLoader(idLocDb);
        } catch (Exception e) {
            throw new RepoException(
                    "Error in Id Locator DB Loader during ArchiveRegister init");
        }
    }

    /**
     * Register Repository Ids in the ID Locator
     * 
     * @param baseurl
     *            BaseURL of the repository openurl interface
     * @param repouri
     *            Full info uri identifier for repo
     * @throws RepoException
     */
    public final void publishIdentifiers(String baseurl, String repouri) throws RepoException {
        try {
            File file = idLocator.downloadFile(baseurl, repouri);
            idLocator.processFile(file, repouri);
        } catch (Exception ex) {
            throw new RepoException("Error adding repo ids to IdLocator : "
                    + ex.getMessage(), ex);
        }
    }
}
