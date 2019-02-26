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

package gov.lanl.didlwriter.profile.adore;

import org.adore.didl.content.DII;

/**
 * Simplified DII Interface for aDORe usage
 * 
 * @author rchute
 */
public class AdoreDIIIdentifier {
    private String id;

    /**
     * Gets the DII Identifier value
     * 
     * @return dii of item or component
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the DII Identifier value
     * 
     * @param id
     *            dii of item or component
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Creates a DII instance for the provided id
     * 
     * @return initialized DII with setId()
     */
    public DII create() {
        if (id == null)
            throw new NullPointerException();
        return new DII(DII.IDENTIFIER, id);
    }

    /**
     * Populates AdoreDIIIdentifier instance from DII
     * 
     * @param dii
     *            initialized DII
     */
    public void parse(DII dii) {
        id = dii.getValue();
    }
}
