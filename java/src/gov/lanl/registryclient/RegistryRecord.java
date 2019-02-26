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

package gov.lanl.registryclient;

import gov.lanl.registryclient.parser.Metadata;

import org.apache.log4j.Logger;



/**
 * maintain information about a specific tape
 */

public class RegistryRecord<T extends Metadata> {
    String identifier = null;

    String datestamp = null;

    T metadata;

    static Logger log = Logger.getLogger(RegistryRecord.class.getName());

    public RegistryRecord(String identifier, String datestamp,T data) {
	this.identifier = identifier;
	this.datestamp = datestamp;
	this.metadata = data;
    }

    public String getIdentifier() {
	return identifier;
    }

    public String getDatestamp() {
	return datestamp;
    }

    public T getMetaData() {
	return metadata;
    }

    public void setDatestamp(String datestamp) {
	this.datestamp = datestamp;
    }

    public void setIdentifier(String identifier) {
	this.identifier = identifier;
    }

    public void setMetadata(T metadata) {
	this.metadata = metadata;
    }

}
