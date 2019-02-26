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

package gov.lanl.xmltape.identifier.index.access;

import gov.lanl.identifier.IndexException;
import gov.lanl.xmltape.identifier.index.IdentifierIndexInterface;
import gov.lanl.xmltape.identifier.index.record.Record;
import gov.lanl.xmltape.index.TapeIndexInterface;

import java.util.Properties;

public interface RecordAccessor {
    public abstract void init(Properties config) throws IndexException;
    public abstract void init(Properties config, TapeIndexInterface index, IdentifierIndexInterface idIdx) throws IndexException;
    public abstract Record next();
    public abstract boolean hasNext();
    public abstract String getMetadata(String id);
    public abstract void close();
}
