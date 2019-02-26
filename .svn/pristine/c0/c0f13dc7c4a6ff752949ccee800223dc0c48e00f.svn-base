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

package gov.lanl.resource.filesystem.index.bdb;

import gov.lanl.resource.ResourceRecord;

import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleOutput;
import com.sleepycat.bind.tuple.TupleBinding;

public class ResourceBinding extends TupleBinding {
    public void objectToEntry(Object obj, TupleOutput to) {
    	ResourceRecord record = (ResourceRecord) obj;
        to.writeString(record.getDate());
        to.writeString(record.getIdentifier());
        to.writeString(record.getMimetype());
        to.writeString(record.getChecksum());
        to.writeString(record.getLength());
        to.writeString(record.getResourceUri());
        to.writeString(record.getRepositoryId());
        to.writeString(record.getSourceUri());
    }

    public Object entryToObject(TupleInput ti) {
    	ResourceRecord record = new ResourceRecord();
    	record.setDate(ti.readString());
    	record.setIdentifier(ti.readString());
    	record.setMimetype(ti.readString());
    	record.setChecksum(ti.readString());
    	record.setLength(ti.readString());
    	record.setResourceUri(ti.readString());
    	record.setRepositoryId(ti.readString());
    	record.setSourceUri(ti.readString());
        return record;
    }

}
