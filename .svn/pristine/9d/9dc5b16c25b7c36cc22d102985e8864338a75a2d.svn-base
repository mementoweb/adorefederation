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

package gov.lanl.federator.schematable;

/**
 * Container for function information
 */

public class MapItem {
    // prefix
    String metadataPrefix;

    String schema;

    String metadataNamespace;

    String service;

    /**
     * @param prefix
     *            metadataprefix
     * @param schema
     * @param ns
     *            metadata namespace
     * @param service
     *            service ()
     */
    public MapItem(String prefix, String schema, String ns, String service) {
        this.metadataPrefix = prefix;
        this.schema = schema;
        this.metadataNamespace = ns;
        this.service = service;

    }

    public MapItem() {
    }

    public String getMetadataPrefix() {
        return metadataPrefix;
    }

    public String getSchema() {
        return schema;
    }

    public String getNS() {
        return metadataNamespace;
    }

    public String getService() {
        return service;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("--prefix:" + metadataPrefix + "\n");
        sb.append("--schema:" + schema + "\n");
        sb.append("--metadataNamespace:" + metadataNamespace + "\n");
        sb.append("--service:" + service + "\n");
        return (sb.toString());
    }
}
