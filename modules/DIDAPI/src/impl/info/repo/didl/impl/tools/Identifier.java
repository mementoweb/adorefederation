/*
 * Identifier.java
 *
 * Created on November 28, 2005, 2:52 PM
 *
 * Copyright (c) 2006  The Regents of the University of California and Ghent University
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
 */

package info.repo.didl.impl.tools;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * Identifier Utilities
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 */
public class Identifier {
	
	/**
	 * Create a UUID w/ specified prefix
	 */
    public static URI createDocumentIdentifier(String prefix) {
        URI uri = null;
        try {
            uri = new URI(prefix + UUID.randomUUID().toString());
        }
        catch (URISyntaxException e) {}
        return uri;
    }
    
    /** 
     * Create a UUID with the 'uuid-' prefix, used as an xmlid
     */
    public static String createXMLIdentifier() {
        return "uuid-" + UUID.randomUUID().toString();
    }
}
