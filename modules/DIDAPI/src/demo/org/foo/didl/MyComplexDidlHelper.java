/*
 * MyComplexDidlHelper.java
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

package org.foo.didl;

import info.repo.didl.ComponentType;
import info.repo.didl.DIDLType;
import info.repo.didl.ItemType;
import info.repo.didl.StatementType;
import info.repo.didl.impl.content.ByteArray;
import info.repo.didl.impl.tools.Identifier;

import java.net.URL;

/**
 * <code>MyComplexDidlHelper</code> is a DIDL utility class for the simplified creation
 * of various DIDL elements.
 * 
 * @author Ryan Chute <rchute@lanl.gov>
 * 
 */

public class MyComplexDidlHelper {

    private static final String DEFAULT_MIME_TYPE = "application/xml; charset=utf-8";

    /**
     * Creates a new DIDL Statement with the contents of obj for the 
     * specified DIDLType instance.
     * @param didl DIDLType instance to add Statement to
     * @param obj Content Type object to be used for Statement Type
     * @return statement object containing xml content
     */
    public static StatementType newXMLStatement(DIDLType didl, Object obj) {
        StatementType stmt = didl.newStatement();
        stmt.setMimeType(DEFAULT_MIME_TYPE);
        stmt.setContent(obj);
        return stmt;
    }

    /**
     * Creates a new xmlid using an UUID
     */
    public static String createXMLIdentifier() {
        return Identifier.createXMLIdentifier();
    }

    /**
     * Creates a new DIDL Item for specified DIDLType instance
     * @param didl DIDLType instance to add Item to
     * @return ItemType instance initialized with uuid xmlid
     */
    public static ItemType newItem(DIDLType didl) {
        ItemType item = didl.newItem();
        item.setId(createXMLIdentifier());
        return item;
    }

    /**
     * Creates a new DIDL Component for specified DIDLType instance
     * @param didl DIDLType instance to add Component to
     * @return ComponentType instance initialized with uuid xmlid
     */
    public static ComponentType newComponent(DIDLType didl) {
        ComponentType com = didl.newComponent();
        com.setId(createXMLIdentifier());
        return com;
    }

    /**
     * Creates a new ByteArray instance for specified String
     */
    public static ByteArray newByteArray(String str) {
        ByteArray ba = new ByteArray(str);
        return ba;
    }

    /**
     * Creates a new ByteArray instance for specified url, 
     * resource will be resolved and content s rendered as 
     * ByteArray
     */
    public static ByteArray newByteArray(URL url) {
        ByteArray ba = new ByteArray(url);
        return ba;
    }
}
