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

package gov.lanl.xmltape.identifier.index.record.foxml;

import java.util.Properties;

public class FOXMLProperties extends Properties {
   
    public FOXMLProperties() {
        loadDefaults();
    }
    
    public void loadDefaults() {
        this.put("profile.name", "foxml");
        this.put("profile.namespace.1", "info:fedora/fedora-system:def/foxml#");
        this.put("profile.namespace.prefix.1", "foxml");
        this.put("profile.record.xpath", "//foxml:digitalObject/@PID");
        this.put("profile.datestamp.xpath", "//foxml:digitalObject/foxml:objectProperties/foxml:property[@NAME=\"info:fedora/fedora-system:def/model#createdDate\"]/@VALUE");
        // Datastream can exist in 2 places...
        // 1) //didl:DIDL/didl:Item/didl:Item/didl:Component/didl:Descriptor/didl:Statement/dii:Identifier
        // 2) //didl:DIDL/didl:Item/didl:Component/didl:Descriptor/didl:Statement/dii:Identifier
        this.put("profile.field.name.0", "datastreamId");
        this.put("profile.field.xpath.0", "//foxml:digitalObject/foxml:datastream/@ID");
    }
}
