/**
 *Copyright (c) 2000-2002 OCLC Online Computer Library Center,
 *Inc. and other contributors. All rights reserved.  The contents of this file, as updated
 *from time to time by the OCLC Office of Research, are subject to OCLC Research
 *Public License Version 2.0 (the "License"); you may not use this file except in
 *compliance with the License. You may obtain a current copy of the License at
 *http://purl.oclc.org/oclc/research/ORPL/.  Software distributed under the License is
 *distributed on an "AS IS" basis, WITHOUT WARRANTY OF ANY KIND, either express
 *or implied. See the License for the specific language governing rights and limitations
 *under the License.  This software consists of voluntary contributions made by many
 *individuals on behalf of OCLC Research. For more information on OCLC Research,
 *please see http://www.oclc.org/oclc/research/.
 *
 *The Original Code is JDBC2oai_dc.java.
 *The Initial Developer of the Original Code is Jeff Young.
 *Portions created by ______________________ are
 *Copyright (C) _____ _______________________. All Rights Reserved.
 *Contributor(s):______________________________________.
 */

package gov.lanl.repo.oaidb;

import java.util.HashMap;
import java.util.Properties;

import ORG.oclc.oai.server.crosswalk.Crosswalk;
import ORG.oclc.oai.server.verb.CannotDisseminateFormatException;

public class Text2Metadata extends Crosswalk {
    /**
     * The constructor assigns the schemaLocation associated with this
     * crosswalk. Since the crosswalk is trivial in this case, no properties are
     * utilized.
     * 
     * @param properties
     *            properties that are needed to configure the crosswalk.
     */
    public Text2Metadata(Properties properties) {

        super(properties.getProperty("JDBCLimitedOAICatalog.ns") + " "
                + properties.getProperty("JDBCLimitedOAICatalog.location"));
    }

    /**
     * Can this nativeItem be represented in DC format?
     * 
     * @param nativeItem
     *            a record in native format
     * @return true if DC format is possible, false otherwise.
     */
    public boolean isAvailableFor(Object nativeItem) {
        return true; // all records must support oai_dc according to the OAI
                     // spec.
    }

    /**
     * Perform the actual crosswalk.
     * 
     * @param nativeItem
     *            the native "item". In this case, it is already formatted as an
     *            OAI <record>element, with the possible exception that
     *            multiple metadataFormats are present in the <metadata>
     *            element.
     * @return a String containing the XML to be stored within the <metadata>
     *         element.
     * @exception CannotDisseminateFormatException
     *                nativeItem doesn't support this format.
     */

    public String createMetadata(Object nativeItem)
            throws CannotDisseminateFormatException {
        HashMap table = (HashMap) nativeItem;
        String record = null;

        try {
            if ((record = (String) table.get("data")) == null) {
                throw new Exception("No metadata contained in the 'data' field");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println("blob size:" + record.length());
        return record;

    }
}
