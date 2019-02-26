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

package gov.lanl.ingest.oaitape;

import gov.lanl.arc.heritrixImpl.ARCFileWriter;

/**
 * DerefProcessor defines interface for a plug-in class
 */

public abstract class DerefProcessor {

    /**
     * processContent is need to be implemented for specific metadata
     * 
     * @param record
     *            oai pmh string record to process
     * @param arcwriter
     *            temporary arcwriter
     * @return ProcessInfo data structure with results
     */

    public abstract ProcessInfo processContent(String record, ARCFileWriter arcwriter);
    
    public void finalize() {};

}
