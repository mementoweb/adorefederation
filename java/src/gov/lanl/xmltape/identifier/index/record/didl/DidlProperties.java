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

package gov.lanl.xmltape.identifier.index.record.didl;

import java.util.Properties;

public class DidlProperties extends Properties {
   
    public DidlProperties() {
        loadDefaults();
    }
    
    public void loadDefaults() {
        this.put("profile.name", "didl");
        this.put("profile.namespace.1", "urn:mpeg:mpeg21:2002:02-DIDL-NS");
        this.put("profile.namespace.prefix.1", "didl");
        this.put("profile.namespace.2", "urn:mpeg:mpeg21:2002:01-DII-NS");
        this.put("profile.namespace.prefix.2", "dii");
        this.put("profile.namespace.3", "http://library.lanl.gov/2005-08/aDORe/DIDLextension/");
        this.put("profile.namespace.prefix.3", "diext");
        this.put("profile.record.xpath", "//didl:DIDL/@DIDLDocumentId");
        this.put("profile.datestamp.xpath", "//didl:DIDL/@diext:DIDLDocumentCreated");
        this.put("profile.field.name.0", "sourceContentId");
        this.put("profile.field.xpath.0", "//didl:DIDL/didl:Item/didl:Descriptor/didl:Statement/dii:Identifier");
        this.put("profile.field.name.1", "localContentId");
        this.put("profile.field.xpath.1", "//didl:DIDL/didl:Item/didl:Item/didl:Descriptor/didl:Statement/dii:Identifier");
        // Datastream can exist in 2 places...
        // 1) //didl:DIDL/didl:Item/didl:Item/didl:Component/didl:Descriptor/didl:Statement/dii:Identifier
        // 2) //didl:DIDL/didl:Item/didl:Component/didl:Descriptor/didl:Statement/dii:Identifier
        this.put("profile.field.name.2", "datastreamId");
        this.put("profile.field.xpath.2", "//didl:Component/didl:Descriptor/didl:Statement/dii:Identifier");
    }
}
