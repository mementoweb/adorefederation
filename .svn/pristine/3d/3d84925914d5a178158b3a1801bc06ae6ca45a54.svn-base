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

package gov.lanl.registryclient.parser;

import java.util.HashMap;

/**
 * Maintains relationship between a prefix and its implementation class
 * 
 */
public class ParserContext {
    // map table from key to implementation
    private HashMap<String, Class> implMap;

    /**
     * Creates a new ParserContext
     */
    public ParserContext() {
        implMap = new HashMap<String, Class>();
    }

    /**
     * Create a context with only one mapping
     * 
     * @param prefix
     * @param cls
     */
    public ParserContext(String prefix, Class cls) {
        this();
        putImplClass(prefix, cls);
    }

    /**
     * Gets the implementation class for a prefix
     * 
     * @param prefix
     *            prefix of implementation class to return
     * @return a registry object implementation class
     */
    public Class getImplClass(String prefix) {
        return implMap.get(prefix);
    }

    /**
     * Adds a new prefix / registry object implementation class pairing
     * 
     * @param prefix
     *            an oai-pmh metadataPrefix
     * @param cls
     *            a registry object implementation class
     */
    public void putImplClass(String prefix, Class cls) {
        implMap.put(prefix, cls);
    }

}
