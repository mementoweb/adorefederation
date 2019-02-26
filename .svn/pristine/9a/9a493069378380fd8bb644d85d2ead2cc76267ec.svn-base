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

package gov.lanl.ockham.client.adore;

import gov.lanl.ockham.client.app.UnrecognizedIdentifierException;

/**
 * adore service identifier logic
 * 
 * @author rchute
 * 
 */
public class Util {

    public static final String PREFIX_OPENURL = "info:lanl-repo/int/";
    public static final String SUFFIX_OPENURL_ADORE1 = "/openurl-aDORe1";
    public static final String SUFFIX_OPENURL_ADORE2 = "/openurl-aDORe2";
    public static final String SUFFIX_OPENURL_ADORE3 = "/openurl-aDORe3";
    public static final String SUFFIX_OPENURL_ADORE4 = "/openurl-aDORe4";
    public static final String SUFFIX_OPENURL_ADORE7 = "/openurl-aDORe7";
    
    /**
     * get repository type from its identifier in adore we took the convention
     * of info:lanl-repo/xmltape/xxx or info:lanl-repo/arc/xxx
     * 
     * @param identifier
     * @return
     */
    static String getRepositoryType(String repoIdentifier)
            throws UnrecognizedIdentifierException {
        String[] fragments = repoIdentifier.split("/");
        if (!"info:lanl-repo".equals(fragments[0])) {
            throw new UnrecognizedIdentifierException("prefix of "
                    + repoIdentifier);
        }

        if ("xmltape".equals(fragments[1])) {
            return "xmltape";
        } else if ("arc".equals(fragments[1])) {
            return "arc";
        } else
            throw new UnrecognizedIdentifierException("prefix of "
                    + repoIdentifier);
    }

    static String getOAIPMHServiceId(String repoIdentifier)
            throws UnrecognizedIdentifierException {
        if ("xmltape".equals(getRepositoryType(repoIdentifier))) {
            String[] fragments = repoIdentifier.split("/");
            return "info:lanl-repo/int/" + fragments[2] + "/oaipmh2";
        } else
            throw new IllegalArgumentException(
                    " arc file doesn't have OAI-PMH interface");
    }

    static String getOpenURLaDORe1ServiceId(String repoIdentifier) 
            throws UnrecognizedIdentifierException {
        return getOpenURLaDOReServiceId(repoIdentifier, SUFFIX_OPENURL_ADORE1);
    }
    
    static String getOpenURLaDORe2ServiceId(String repoIdentifier)
            throws UnrecognizedIdentifierException {
        return getOpenURLaDOReServiceId(repoIdentifier, SUFFIX_OPENURL_ADORE2);
    }
    
    static String getOpenURLaDORe3ServiceId(String repoIdentifier)
            throws UnrecognizedIdentifierException {
        return getOpenURLaDOReServiceId(repoIdentifier, SUFFIX_OPENURL_ADORE3);
    }

    static String getOpenURLaDORe4ServiceId(String repoIdentifier)
            throws UnrecognizedIdentifierException {
        return getOpenURLaDOReServiceId(repoIdentifier, SUFFIX_OPENURL_ADORE4);
    }
    
    static String getOpenURLaDORe7ServiceId(String repoIdentifier)
            throws UnrecognizedIdentifierException {
        return getOpenURLaDOReServiceId(repoIdentifier, SUFFIX_OPENURL_ADORE7);
    }
    
    static String getOpenURLaDOReServiceId(String repoIdentifier, String suffix)
            throws UnrecognizedIdentifierException {
        String[] fragments = repoIdentifier.split("/");
        if ("xmltape".equals(getRepositoryType(repoIdentifier))
                || "arc".equals(getRepositoryType(repoIdentifier))) {
            return PREFIX_OPENURL + fragments[2] + suffix;
        } else
            throw new UnrecognizedIdentifierException("prefix of "
                    + repoIdentifier);
    }
}
