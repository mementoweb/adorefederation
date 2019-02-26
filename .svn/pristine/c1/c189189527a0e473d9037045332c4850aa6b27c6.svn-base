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
package gov.lanl.federator;

import ORG.oclc.oai.server.crosswalk.Crosswalk;
import ORG.oclc.oai.server.crosswalk.CrosswalkItem;

public class FedCrosswalk extends Crosswalk {

    /**
     * The constructor assigns the schemaLocation associated with this
     * crosswalk.
     * 
     * @param crosswalkItem
     *            properties that are needed to configure the crosswalk.
     */
    public FedCrosswalk(CrosswalkItem crosswalkItem) {
        super(crosswalkItem.getMetadataNamespace() + " "
                + crosswalkItem.getSchema());

    }

    /**
     * Can this nativeItem be represented in DC format?
     * 
     * @param nativeItem
     *            a record in native format
     * @return true if DC format is possible, false otherwise.
     */
    public boolean isAvailableFor(Object nativeItem) {
        return true;
    }

    /**
     * Perform the actual crosswalk. we never use this feature in federator
     * 
     * @param nativeItem
     *            the native "item".
     * @return a String containing the XML to be stored within the <metadata>
     *         element.
     */
    public String createMetadata(Object nativeItem) {
        return null;
    }
}
