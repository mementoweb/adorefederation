/*
 * DIDException.java
 *
 * Created on October 8, 2005, 10:49 AM
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

package info.repo.didl;

/**
 * Signals that a DIDLException of some sort has occurred. 
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 */
public final class DIDLException extends RuntimeException {
    public short code;
    public final static short UNKNOWN_ERROR = -1;
    public final static short NOT_FOUND = 0; // Not in use
    public final static short ALREADY_DEFINED = 1;
    public final static short CONFIGURATION_ERROR = 2;
    
    public DIDLException(short code, String message) {
        super(message);
        this.code = code;
    }
    
    public DIDLException(short code, Throwable throwable) {
        super("DIDLException", throwable);
        this.code = code;
    }
}
