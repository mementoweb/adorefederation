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

package gov.lanl.YAParser;

/**
 * DocHandler Interface used by YAParser
 * 
 * @author liu_x
 */

public interface DocHandler {
    /**
     * Allows for custom handling at start of specified element
     */
    public void startElement(String tag) throws Exception;

    /**
     * Allows for custom handling at end of specified element
     */
    public void endElement(String tag) throws Exception;

    /**
     * Actions to perform at beginning of document
     */
    public void startDocument() throws Exception;

    /**
     * Actions to perform once end of document is reached
     */
    public void endDocument() throws Exception;

    /**
     * Handles text values by setting absolute byte offset and length
     * values for the specified element
     * @param buffer
     *          byte array containing value
     * @param start
     *          absolute byte offset from tape start
     * @param length
     *          byte length of value
     * @throws Exception
     */
    public void text(byte[] buffer, int start, int length) throws Exception;
}
