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

package gov.lanl.xmltape;

import java.util.ArrayList;

import org.xml.sax.InputSource;

/**
 * TapeAdmin Representation
 *
 */
public class TapeAdmin implements TapeConstants {

    public static final String TB_NAMESPACE = "http://library.lanl.gov/2005-08/aDORe/XMLtapeBasics/";

    public static final String TB_SCHEMA_LOCATION = "http://purl.lanl.gov/aDORe/schemas/2006-09/XMLtapeBasics.xsd";

    public static final String TB_PREFIX = "ba";

    private String xmlTapeId;

    private ArrayList<String> arcFileIds = new ArrayList<String>();

    private String processSoftware;

    private String processTime;

    /**
     * Gets a list of ARCfiles associated with current XMLtape
     * @return
     *     list of ARCfile URIs
     */
    public ArrayList<String> getArcFileIds() {
        return arcFileIds;
    }

    /**
     * Add ARCfile to list of ARCfiles associated with current XMLtape
     * @param arcFileId
     *     an ARCfile URIs
     */
    public void addArcFileId(String arcFileId) {
        this.arcFileIds.add(arcFileId);
    }

    /**
     * Sets a list of ARCfiles associated with current XMLtape
     * @param arcFileIds
     *     list of ARCfile URIs
     */
    public void setArcFileIds(ArrayList<String> arcFileIds) {
        this.arcFileIds = arcFileIds;
    }

    /**
     * Gets software name used to process XMLtape
     * @return
     *     qualified path to processing class
     */
    public String getProcessSoftware() {
        return processSoftware;
    }

    /**
     * Sets software name used to process XMLtape
     * @param processSoftware
     *     qualified path to processing class
     */
    public void setProcessSoftware(String processSoftware) {
        this.processSoftware = processSoftware;
    }

    /**
     * Gets the time at which the XMLtape was processed
     * @return
     *    valid UTC date
     */
    public String getProcessTime() {
        return processTime;
    }

    /**
     * Sets the time at which the XMLtape was processed
     * @param processTime
     *    valid UTC date
     */
    public void setProcessTime(String processTime) {
        this.processTime = processTime;
    }

    /**
     * Gets the XMLtape Identifier
     * @return
     *    uri of XMLtape
     */
    public String getXmlTapeId() {
        return xmlTapeId;
    }

    /**
     * Sets the XMLtape Identifier
     * @param xmlTapeId
     *    uri of XMLtape
     */
    public void setXmlTapeId(String xmlTapeId) {
        this.xmlTapeId = xmlTapeId;
    }

    /**
     * Parses XML fragment to create a TapeAdmin object
     * @param xml
     *     TapeAdmin XML fragment
     * @return
     *       a populated TapeAdmin instance
     * @throws Exception
     */
    public static TapeAdmin read(InputSource xml) throws Exception {
        TapeAdmin ta = new TapeAdmin();
        TapeAdminParser parser = new TapeAdminParser();
        parser.parse(ta, xml);
        return ta;
    }

}
