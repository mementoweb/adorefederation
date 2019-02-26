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

package gov.lanl.harvester;

import java.util.Hashtable;

/**
 * ErrorCode.java<br>
 * <br>
 * List of all error codes which are returned by commandline program 
 * 
 * @author Xiaoming Liu 
 */

public final class ErrorCode {
    public static Hashtable errorhash;

    public static int OK = 0;

    public static int CMDLINE_ERR = 1;

    public static int FILE_LOCK = 2;

    public static int CONFIGFILE_ERR = 3;

    public static int HARVEST_ERR = 4;

    static {
        errorhash = new Hashtable();
        errorhash.put(new Integer(OK), "OK");
        errorhash.put(new Integer(CMDLINE_ERR), "Command Line error");
        errorhash.put(new Integer(FILE_LOCK), "File Locked");
        errorhash.put(new Integer(CONFIGFILE_ERR), "Configuration file error");
        errorhash.put(new Integer(HARVEST_ERR), "Harvest error");
    }

    /**
     * Get Error String
     * 
     * @param no
     *            number of error code
     * @return error message
     */
    public static String getErrorMessage(int no) {
        return (String) (errorhash.get(new Integer(no)));
    }

}
