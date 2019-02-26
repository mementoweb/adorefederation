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

import java.io.ByteArrayInputStream;

public class MetadataParser {
    private ParserContext parserContext = null;

    /**
     * Creates a new MetadataParser
     */
    public MetadataParser() {
        this(null);
    }

    /**
     * Create a metadata parser with pre-existing context
     * 
     * @param context
     *            the prefix/implementing class pairing
     */
    public MetadataParser(ParserContext context) {
        parserContext = context;
    }

    /**
     * Sets a context for parsing registry item
     * 
     * @param context
     *            the prefix/implementing class pairing
     */
    public void setParserContext(ParserContext context) {
        this.parserContext = context;
    }

    /**
     * Gets the parserContext
     * 
     * @return parsercontext
     *            the prefix/implementing class pairing
     */
    public ParserContext getParserContext() {
        return this.parserContext;
    }

    /**
     * Gets a metadata instance given xml and prefix
     * @param data
     *         xml record
     * @param prefix
     *         record type
     * @return a deserialized Metadata instance
     * @throws SerializationException
     */
    public Metadata parse(String data, String prefix)
            throws SerializationException {
        try {
            Class cl = parserContext.getImplClass(prefix);
            Metadata meta = (Metadata) cl.newInstance();
            meta.read(new ByteArrayInputStream(data.getBytes()));
            return meta;
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }
}
