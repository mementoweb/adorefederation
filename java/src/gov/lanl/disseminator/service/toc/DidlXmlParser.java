/*
 * Copyright (c) 2008  Los Alamos National Security, LLC.
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

package gov.lanl.disseminator.service.toc;
import gov.lanl.util.xpath.DOM4JImpl;
import gov.lanl.util.xpath.DocumentField;
import gov.lanl.util.xpath.DocumentProfile;
import gov.lanl.util.xpath.DocumentProfileFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

public class  DidlXmlParser {
    public static HashMap processContent(byte[] raw) throws Exception {
        HashMap map = new HashMap<String, String>();
        try {
            // Check the length
            int contentLength = raw.length;
            if (contentLength == 0) {
               throw new Exception("Unable to process content with a length of 0");
            }
            
            //MarcXmlProperties props = new MarcXmlProperties();
            //DidlProperties props = new  DidlProperties();
           Properties props = new Properties();
            //props.put("profile.name", "didl");
            props.put("profile.namespace.1", "urn:mpeg:mpeg21:2002:02-DIDL-NS");
            props.put("profile.namespace.prefix.1", "didl");
            props.put("profile.namespace.2", "urn:mpeg:mpeg21:2002:01-DII-NS");
            props.put("profile.namespace.prefix.2", "dii");
            props.put("profile.namespace.3", "http://library.lanl.gov/2005-08/aDORe/DIDLextension/");
            props.put("profile.namespace.prefix.3", "diext");
            //props.put("profile.record.xpath", "//didl:DIDL/@DIDLDocumentId");
            props.put("profile.field.name.6", "dateCreated");
            props.put("profile.field.xpath.6", "//didl:DIDL/@diext:DIDLDocumentCreated");
            
            DocumentProfile docProfile = DocumentProfileFactory.generateDocProfile(props);

            String xml = new String(raw, "UTF-8");
            
            DOM4JImpl doc = new DOM4JImpl();
            try {
                doc.setDocument(xml);
                doc.setNamespaces(docProfile.getNamespaceProfiles());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Populate Metadata Object
            for (Iterator i = docProfile.getDocFields(); i.hasNext();) {
                DocumentField df = (DocumentField) i.next();
                String field = df.getFieldName();
                String xpath = df.getXpath();
                
                ArrayList values = doc.xpath(xpath);
                for (Iterator k = values.iterator(); k.hasNext();) {
                   String value = (String) k.next();
                    if (k != null) {
                        map.put(field,value);
                    }
                }
            }
        } catch (Exception e) { // run time exception
            throw new Exception("General exception in MarcXmlParser: " + e.getMessage());
        }
        return map;
    }
}
