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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class DocumentProfile {

    private Set<DocumentField> fields = new TreeSet<DocumentField>();
    
    private ArrayList<NamespaceProfile> nsProfiles = new ArrayList<NamespaceProfile>();

    private String profileName;
    private String docIdXpath;
    private String docDatestamp;

    public String getDocProfileName() {
        return this.profileName;
    }
    
    public String getDocIdXpath() {
        return this.docIdXpath;
    }

    public void setDocIdXpath(String xpath) {
        this.docIdXpath = xpath;
    }
    
    public String getDocDatestampXpath() {
        return this.docDatestamp;
    }

    public void setDocDatestampXpath(String xpath) {
        this.docDatestamp = xpath;
    }
    
    public void setDocProfileName(String docProfileName) {
        this.profileName = docProfileName;
    }

    public void addDocField(DocumentField docField) {
        fields.add(docField);
    }

    public Iterator getDocFields() {
        return fields.iterator();
    }

    public DocumentField getDocField(String fieldName) throws Exception {
        for (DocumentField df : fields) {
            if (df.getFieldName().equals(fieldName))
                return df;
        }
        throw new Exception("Unable to locate specified fieldName: " + fieldName);
    }

    public ArrayList<NamespaceProfile> getNamespaceProfiles() {
        return nsProfiles;
    }

    public void addNamespaceProfile(NamespaceProfile nsProfile) {
        nsProfiles.add(nsProfile);
    }

}
