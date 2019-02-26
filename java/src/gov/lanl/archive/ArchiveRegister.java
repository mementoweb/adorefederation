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

package gov.lanl.archive;

import gov.lanl.archive.wrappers.ARCWrapper;
import gov.lanl.archive.wrappers.TapeWrapper;

import java.text.SimpleDateFormat;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Wrapper class for aDORe Archive to index and register XMLtapes & ARCfiles
 * 
 * @author Ryan Chute <rchute@lanl.gov>
 */
public class ArchiveRegister {
    
    static final Logger log = Logger.getLogger(ArchiveRegister.class.getName());
    static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    
    private ArchiveConfig config;
    private ARCWrapper aw;
    private TapeWrapper tw;
   
    /**
     * Constructor
     * @param _config
     *            ArchiveConfig used to initialize class
     */
    public ArchiveRegister(ArchiveConfig _config) {
        this.config = _config;
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() throws Exception {
        Properties props = config.getProperties();
        aw = new ARCWrapper(props);
        tw = new TapeWrapper(props);
    }
    
    /**
     * Index ARCfile; generates a CDX index file in ARC Index Dir
     * @param arcname
     *            Name of arc file to be indexed, minus extension
     * @throws ArchiveException
     */
    public int indexARCFile(String collection, String arcname) throws ArchiveException {
        int cnt = 0;
        try {
            cnt = aw.indexARCFile(collection, arcname);
        } catch (Exception ex) {
            throw new ArchiveException(
                    "Error in index arc file " + arcname, ex);
        }
        return cnt;
    }

    /**
     * Register ARC file, it's implemented by constructing a property list based
     * on configuration and transfer to arc register program.
     * 
     * @param arcname
     *            Name of arc file, minus extension
     */
    public void registerARCFile(String collection, String arcname) 
      throws ArchiveException {
        try {
            aw.registerARCFile(collection, arcname);
        } catch (Exception ex) {
            throw new ArchiveException("Error in register arc file "
                    + arcname + ": " + ex.getMessage(), ex);
        }
    }

    /**
     * Index XMLtape file; uses tape indexing plug-in specified in ArchiveConfig
     * @param tapename
     *            Name of xml tape file
     * @throws ArchiveException
     */
    public int indexTape(String collection, String tapename) throws ArchiveException {
        int cnt = 0;
        try {
            cnt = tw.indexTapeFile(collection, tapename);
        } catch (Exception ex) {
            throw new ArchiveException("Error in tapeindexer " + tapename,
                    ex);
        }
        return cnt;
    }
    
    /**
     * Index XMLtape file identifiers; uses id record indexing plug-in specified in ArchiveConfig
     * @param tapename
     *            Name of xml tape file
     * @throws ArchiveException
     */
    public void indexIdentifiers(String collection, String tapename) throws ArchiveException {
        try {
            tw.indexTapeIdentifiers(collection, tapename);
        } catch (Exception ex) {
            throw new ArchiveException("Error in identifier indexer " + tapename,
                    ex);
        }
    }
    
    /**
     * Register xml tape in adore-xmltape-registry
     * @param collection
     *            Collection Profile Prefix to which the tape belongs
     * @param tapename
     *            Name of xml tape file, minus extension
     * @param tapeadmin
     *            TapeAdmin XML fragament from tapeAdmin
     * @throws ArchiveException
     */
    public void registerTape(String collection, String tapename, String tapeadmin) 
       throws ArchiveException {
        try {
            tw.registerTapeFile(collection, tapename, tapeadmin);
        } catch (Exception ex) {
            throw new ArchiveException("Error in register tape file "
                    + tapename + ": " + ex.getMessage(), ex);
        }
    }
}
