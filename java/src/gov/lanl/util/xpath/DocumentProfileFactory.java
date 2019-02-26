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

package gov.lanl.util.xpath;

import java.util.Properties;

public class DocumentProfileFactory {

    private static final String PROFILE_NAME = "profile.name";
    
    private static final String PROFILE_DOC_ID_XPATH = "profile.record.xpath";
    
    private static final String PROFILE_DOC_DATE_XPATH = "profile.datestamp.xpath";

    private static final String PROFILE_NS = "profile.namespace";
    
    private static final String PROFILE_NS_PREFIX = "profile.namespace.prefix";

    private static final String FIELD_NAME = "profile.field.name";

    private static final String FIELD_XPATH = "profile.field.xpath";

    public static DocumentProfile generateDocProfile(Properties props) {
        DocumentProfile profile = new DocumentProfile();
        profile.setDocProfileName(props.getProperty(PROFILE_NAME));
        profile.setDocIdXpath(props.getProperty(PROFILE_DOC_ID_XPATH));
        profile.setDocDatestampXpath(props.getProperty(PROFILE_DOC_DATE_XPATH));
        
        // Iterate through / set namespace profiles
        for (int i = 0; i < props.size(); i++) {
            if (props.containsKey(PROFILE_NS + "." + i)) {
                NamespaceProfile nsp = new NamespaceProfile();
                nsp.setNamespace(props.getProperty(PROFILE_NS + "." + i));
                if (props.getProperty(PROFILE_NS_PREFIX + "." + i) != null) {
                    nsp.setNamespacePrefix(props.getProperty(PROFILE_NS_PREFIX + "." + i));
                }
                profile.addNamespaceProfile(nsp);
            }
        }
        
        // Iterate through / set field profiles
        for (int i = 0; i < props.size(); i++) {
            if (props.containsKey(FIELD_NAME + "." + i)) {
                if (props.getProperty(FIELD_NAME + "." + i) != null) {
                    DocumentField df = new DocumentField();
                    df.setFieldName(props.getProperty(FIELD_NAME + "." + i));
                    if (props.getProperty(FIELD_XPATH + "." + i) != null) {
                        df.setXpath(props.getProperty(FIELD_XPATH + "." + i)); 
                    }

                    profile.addDocField(df);
                }
            }
        }

        return profile;
    }

}
