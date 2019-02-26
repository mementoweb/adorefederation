/*
 * DIEXT.java
 *
 * Created on October 17, 2005, 11:26 AM
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

package org.adore.didl.attribute;

import info.repo.didl.impl.AbstractAttribute;
import java.util.Date;
import org.adore.didl.serialize.DIEXTSerializer;

/**
 * The <code>DIEXT</code> object stores administrative data
 * about when a DIDL was created and last modified.  Refer to 
 * DIEXTSerializer for serialization implementations.
 * <p>
 * All dates should be in UTC format ("yyyy-MM-dd'T'HH:mm:ss'Z'").
 * <code>
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT+0"))
            
            // Serialize
            Date date = formatter.parse(created);
            
            // Deserialize
            String date = formatter.format(diext.getCreated());
 * </code>
 * 
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 * @see DIEXTSerializer
 */
public class DIEXT extends AbstractAttribute {
	/** Key Name of DIDL Creation Datestamp */
    public static final String CREATED_ATT = "DIDcreated";
    /** Key Name of DIDL Last Modified Datestamp */
    public static final String LASTMOD_ATT = "DIDmodified";
    
    /** 
     * Creates a new instance of DIEXT 
     */
    public DIEXT() {
    }
    
    /**
     * Creates a new instance of DIEXT with the specified creation/modified datestamp
     * @param created_modified creation Date object, also used as last mod date
     */
    public DIEXT(Date created_modified) {
        setCreated(created_modified);
        setLastModified(created_modified);
    }
    
    /**
     * Creates a new instance of DIEXT using the specified date stamps
     * @param created creation Date object
     * @param modified last modified Date object
     */
    public DIEXT(Date created, Date modified) {
        setCreated(created);
        setLastModified(modified);
    }
    
    /**
     * Gets the creation Date object
     */
    public Date getCreated() {
        return (Date) getValue(CREATED_ATT);
    }
    
    /**
     * Sets the creation Date object
     */
    public void setCreated(Date created) {
        setValue(CREATED_ATT, created);
    }
    
    /**
     * Gets the last modified Date object
     */
    public Date getLastModified() {
        return (Date) getValue(LASTMOD_ATT);
    }
    
    /**
     * Sets the last modified Date object
     */
    public void setLastModified(Date lastModified) {
        setValue(LASTMOD_ATT, lastModified);
    }
}
