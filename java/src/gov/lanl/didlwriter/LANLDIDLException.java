/*
 * DIDException.java
 *
 * Created on October 8, 2005, 10:49 AM
 *
 * $Revision: 1.1.1.1 $
 *
 * $Date: 2006/01/29 01:18:58 $
 *
 * Copyright (c) 2004-2005, Los Alamos National Laboratory and Ghent University.
 * All rights reserved.
 */

package gov.lanl.didlwriter;

/**
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 */
public class LANLDIDLException extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LANLDIDLException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
