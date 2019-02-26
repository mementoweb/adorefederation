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

import java.io.File;
import java.util.ArrayList;

/**
 * Plug-in Interface For AdoreArchive Pre-processing
 * 
 * @author Ryan Chute <rchute@lanl.gov>
 *
 */
public interface ProfileProcessor {

    /**
     * Gets list of ARCfile File Objects for indexing & registration
     * @return
     *        ArrayList of File objects for post-processing
     */
    public ArrayList<File> getArcFileList();
    
    /**
     * Gets list of XMLtape File Objects for indexing & registration
     * @return
     *        ArrayList of File objects for post-processing
     */
    public ArrayList<File> getXMLTapeList();
    
    /**
     * Sets ArchiveConfig Properties
     * @param archiveConfig
     *        ArchiveConfig for adore archive
     */
    public void setArchiveConfig(ArchiveConfig archiveConfig);
    
    /**
     * Sets ArchiveProfile Pre-processing Properties
     * @param archiveProfile
     *        ArchiveProfile for collection pre-processing
     */
    public void setArchiveProfile(ArchiveProfile archiveProfile);
    
    /**
     * Main processing method
     * @throws Exception
     */
    public void runIt() throws Exception;

    /**
     * Sets list of files to be processed
     * @param type
     *        Content Type Identifier 
     * @param source
     *        List of files to be preprocessed
     */
    public void setSourceArray(int type, ArrayList source);
    
}
