/*
 * Copyright (c) 2006  The Regents of the University of California
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

package gov.lanl.adore.demo;

/**
 * <code>TutorialException</code> signals that a processing exception of some
 * sort occurred.
 * 
 * @author Ryan Chute <rchute@lanl.gov>
 * 
 */
public class TutorialException extends Exception {

    /**
     * Constructs a <code>TutorialException</code> with the specified details
     * message
     * 
     * @param message
     *            the detail message
     * 
     */
    public TutorialException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with a message and cause.
     * 
     * @param message
     *            brief description of cause
     * @param cause
     *            the cause (which is saved for later retrieval by the
     *            {@link #getCause()} method.
     * 
     */
    public TutorialException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the cause.
     * 
     * @param cause
     *            the cause (which is saved for later retrieval by the
     *            {@link #getCause()} method.
     * 
     */
    public TutorialException(Throwable cause) {
        super("TutorialException", cause);
    }
}
