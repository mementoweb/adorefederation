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

package gov.lanl.adore.demo.mets.identifier.index;

import java.util.ArrayList;
import java.util.Iterator;

import gov.lanl.identifier.Identifier;
import gov.lanl.util.xpath.DOM4JImpl;
import gov.lanl.util.xpath.DocumentField;
import gov.lanl.util.xpath.DocumentProfile;
import gov.lanl.util.xpath.DocumentProfileFactory;
import gov.lanl.xmltape.identifier.index.record.Record;

public class MetsRecordXPath implements Record  {
    
    private String didlDocId;
    private String didlDatestamp;
    private ArrayList<Identifier> ids = new ArrayList<Identifier>();
    private static MetsProperties props;
    private static DocumentProfile docProfile;
    private DOM4JImpl doc;
    
    public MetsRecordXPath() {
        props = new MetsProperties();
        docProfile = DocumentProfileFactory.generateDocProfile(props);
        doc = new DOM4JImpl();
        doc.setNamespaces(docProfile.getNamespaceProfiles());
    }
    
    public void createRecord(String record) {
        try {
            doc.setDocument(record);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set DIDLDocumentId
        didlDocId = (String) doc.xpath(docProfile.getDocIdXpath()).get(0);
        
        // Set DIDL Datestamp
        didlDatestamp = (String) doc.xpath(docProfile.getDocDatestampXpath()).get(0);
        
        // Populate Metadata Object
        for (Iterator i = docProfile.getDocFields(); i.hasNext();) {
            DocumentField df = (DocumentField) i.next();
            String field = df.getFieldName();
            String xpath = df.getXpath();
            ArrayList values = doc.xpath(xpath);
            for (Iterator k = values.iterator(); k.hasNext();) {
                String value = (String) k.next();
                if (k != null) {
                    if (field.equals("contentId")) {
                        ids.add(new Identifier(didlDocId, value, didlDatestamp));
                    } else if (field.equals("localContentId")) {
                        ids.add(new Identifier(didlDocId, value, didlDatestamp));
                    } else {
                        ids.add(new Identifier(didlDocId, value));
                    }
                }
            } 
        }
    }

    public ArrayList<Identifier> getIdentifiers() {
        return ids;
    }

}
