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

package gov.lanl.harvester;

import java.util.Date;
import gov.lanl.util.DateUtil;

/**
 * Provenance.java<br>
 * <br>
 * Generates OAI Administraive Information <br>
 * Data will be written to <record-admin> element
 * 
 * @author Xiaoming Liu
 */

public class Provenance {

    public static final String OAIHEADER = " <provenance xmlns=\"http://www.openarchives.org/OAI/2.0/provenance\""
            + "  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
            + " xsi:schemaLocation=\"http://www.openarchives.org/OAI/2.0/provenance "
            + " http://www.openarchives.org/OAI/2.0/provenance.xsd\">";

    public static String toXML(String baseURL, String identifier,
            String datestamp, String metadataNamespace, Date harvestDate,
            boolean alter) {
        StringBuffer sb = new StringBuffer();
        sb.append(OAIHEADER).append("<originDescription harvestDate=\"")
                .append(DateUtil.date2UTC(harvestDate)).append("\" altered=\"")
                .append(alter).append("\">\n");
        sb.append("<baseURL>").append(baseURL).append("</baseURL>\n");
        ;
        sb.append("<identifier>").append(identifier).append("</identifier>\n");
        sb.append("<datestamp>").append(datestamp).append("</datestamp>\n");
        sb.append("<metadataNamespace>").append(metadataNamespace).append(
                "</metadataNamespace>\n");
        sb.append("</originDescription>\n");
        sb.append("</provenance>\n");
        return sb.toString();

    }

}
