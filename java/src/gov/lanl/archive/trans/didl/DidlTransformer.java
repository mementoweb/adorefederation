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

package gov.lanl.archive.trans.didl;

import gov.lanl.arc.heritrixImpl.ARCFileWriter;

import java.util.Properties;
import java.util.Vector;

/**
 * DIDL Conversion Interface
 * 
 */
public interface DidlTransformer {

    /**
     * Passes a properties object to the underlying implmentation
     * 
     * @param props
     *            Properties object for use by implmentation
     * @throws Exception
     */
    public void setProperties(Properties props) throws Exception;

    /**
     * Performs a DIDL conversion, writing resources to specified ARCFileWriter
     * 
     * @param olddidl
     *            DIDL record to be transformed
     * @param id
     *            DIDL Package Identifier 
     * @param arcwriter
     *            ARCFileWriter instance to write dereferenced resources to
     * @return
     *            DIDL XML at index 0, Datestamp at index 1
     * @throws Exception
     */
    public Vector process(String olddidl, String id, ARCFileWriter arcwriter)
            throws Exception;
}
