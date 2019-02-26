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

package gov.lanl.resource.filesystem.index.bdb;

import com.sleepycat.je.OperationStatus;

/**
 * A simple error message handler.
 */
public class ErrorHandler {

    /**
     * Prints an exception's error message; does not exit.
     * 
     * @param e
     *            an exception
     */
    public static void error(Exception e) {
        error(e, false);
    }

    /**
     * Prints an exception's error message to <code>System.err</code> and
     * exits if <var>exit</var> is <code>true</code>.
     * 
     * @param e
     *            an exception
     * @param exit
     *            if <code>true</code>, exits after printing <var>msg</var>
     */
    public static void error(Exception e, boolean exit) {
        error(e.toString(), exit);
    }

    /**
     * Prints <var>msg</var> to <code>System.err</code>; does not exit.
     * 
     * @param msg
     *            an error message
     */
    public static void error(String msg) {
        error(msg, false);
    }

    /**
     * Prints <var>msg</var> to <code>System.err</code> and exits if
     * <var>exit</var> is <code>true</code>.
     * 
     * @param msg
     *            an error message
     * @param exit
     *            if <code>true</code>, exits after printing <var>msg</var>
     */
    public static void error(String msg, boolean exit) {
        System.err.println(msg);
        if (exit)
            System.exit(1);
    }

    /**
     * Prints <var>msg</var> and an exception's error message to
     * <code>System.err</code>; does not exit.
     * 
     * @param msg
     *            an error message
     * @param e
     *            an exception
     */
    public static void error(String msg, Exception e) {
        error(msg, e, false);
    }

    /**
     * Prints <var>msg</var> and an exception's error message to
     * <code>System.err</code> and exits if <var>exit</var> is
     * <code>true</code>.
     * 
     * @param msg
     *            an error message
     * @param e
     *            an exception
     * @param exit
     *            if <code>true</code>, exits after printing <var>msg</var>
     */
    public static void error(String msg, Exception e, boolean exit) {
        if (msg == null)
            msg = "";
        else
            msg += ": ";
        error(msg + e.toString(), exit);
        if (e instanceof RuntimeException)
            e.printStackTrace();
    }

    /**
     * Prints optional <var>msg</var> and printable version of <var>status</var>;
     * does not exit.
     * 
     * @param msg
     *            an optional message; may be <code>null</code>
     * @param status
     *            a database operation status code
     */
    public static void error(String msg, OperationStatus status) {
        error(msg, status, false);
    }

    /**
     * Prints optional <var>msg</var> and printable version of <var>status</var>
     * and optionally exits.
     * 
     * @param msg
     *            an optional message; may be <code>null</code>
     * @param status
     *            a database operation status code
     * @param exit
     *            if <code>true</code>, exits after printing
     */
    public static void error(String msg, OperationStatus status, boolean exit) {
        if (msg == null)
            msg = "";
        else if (msg.length() > 0)
            msg += ": ";

        if (status == OperationStatus.KEYEMPTY)
            msg += "KEYEMPTY (current rec deleted)";
        else if (status == OperationStatus.KEYEXIST)
            msg += "KEYEXIST (key already exists; overwrite not allowed";
        else if (status == OperationStatus.NOTFOUND)
            msg += "NOTFOUND (requested key/data pair not found)";
        else
            // OperationStatus.SUCCESS
            msg += "SUCCESS";

        error(msg, exit);
    }

}
