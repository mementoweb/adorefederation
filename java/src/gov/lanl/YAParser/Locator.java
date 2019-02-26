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
 * Manages byte offset location during parsing routine
 * 
 * @author liu_x
 *  
 */

public class Locator {
    //index of the beginning and ending character
    private long begin, end;

    /**
     * Gets start byte offset value for element
     * @return
     *        byte inset value to element start
     */
    public long getStartIndex() {
        return begin;
    }

    /**
     * Gets end byte offset value for element
     * @return
     *        byte inset value to element end
     */
    public long getEndIndex() {
        return end;
    }

    /**
     * Sets start byte offset value for element
     * @param begin
     *           byte inset value to element start
     */
    public void setStartIndex(long begin) {
        this.begin = begin;
    }

    /**
     * Sets end byte offset value for element
     * @param end
     *           byte inset value to element end
     */
    public void setEndIndex(long end) {
        this.end = end;
    }

    /**
     * Cast beginning and end values to string delimited by colon
     */
    public String toString() {
        return begin + ":" + end;
    }

}
