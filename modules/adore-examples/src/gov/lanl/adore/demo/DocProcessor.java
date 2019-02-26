/*
 * Copyright (c) 2006  The Regents of the University of California
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

package gov.lanl.adore.demo;

import java.io.InputStream;

import gov.lanl.arc.heritrixImpl.ARCFileWriter;

/**
 * <code>DocProcessor</code> provides a simple interface upon which
 * pre-processing implementations can be created. This tutorial provides
 * examples of METS and DIDL implementations.
 * 
 * @author Ryan Chute <rchute@lanl.gov>
 * 
 */
public interface DocProcessor {

    /**
     * Reads in an InputStream for processing
     * 
     * @param source
     *            InputStream contain document to process
     * @throws TutorialException
     */
    public abstract void read(InputStream source) throws TutorialException;

    /**
     * Performs any pre-processing routines defined by the implemenation.
     * 
     * @throws TutorialException
     */
    public abstract void process() throws TutorialException;

    /**
     * Serializes processed document to XML String format
     * 
     * @return Escaped XML String instance of document
     * @throws TutorialException
     */
    public abstract String write() throws TutorialException;

    /**
     * Gets the last modified date of the TapeRecord. For example:
     * 2006-03-07T12:00:00Z
     * 
     * @return UTC Date as String
     */
    public abstract String getDate();

    /**
     * Gets local repository id for document (i.e. OAI-Identifier). For example:
     * info:lanl-repo/i/dd7b17ea-bddf-11d9-9de5-c11b6cd8559
     * 
     * @return URI as String
     */
    public abstract String getId();

    /**
     * Sets the ARCFileWriter and ARC Resolver Base Url. These are used by
     * pre-processing implementation if you wish to harvest referenced resources
     * to store locally in ARCFiles.
     * 
     * @param arcw
     *            Initialized ARCFileWriter instance
     * @param arcUrl
     *            BaseUrl to ARC File Resolver instance
     */
    public abstract void setARCOutput(ARCFileWriter arcw, String arcUrl);

}