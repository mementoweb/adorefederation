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
import java.util.Map;

/**
 * registry is an abstraction of two typical registry in aDORe environment,
 * oai-pmh based registry or file based registry. The concept is largely
 * modelled by OAI-PMH repository, however the OAI-PMH basic concept can be
 * mirrored to directory registry easily. How to mirror it however is up to
 * implementor of a registry.
 * 
 * 
 * 
 * @author liu_x
 * 
 */
public interface RegistryInterface<T extends Metadata> {

    public Map<String, RegistryRecord<T>> listRecords(String from, String until,
            String setSpec, String prefix) throws RegistryException;

    public RegistryRecord<T> getRecord(String identifier, String prefix)
            throws RegistryException, ItemNotExist;

}
