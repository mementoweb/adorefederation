/*
 * Asset.java
 *
 * Created on October 13, 2005, 10:10 AM
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

package info.repo.didl.impl;

import info.repo.didl.DIDLVisitorType;
import info.repo.didl.ResourceType;
import info.repo.didl.StatementType;
import java.net.URI;

/**
 * <code>Asset</code> provides a base implementation for DIDL ResourceTypes
 * and StatementType objects.
 * 
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class Asset extends AttributableBase implements ResourceType, StatementType {
    
	/** Enumeration of Valid Asset Types **/
	public enum Type { STATEMENT, RESOURCE }
    
	/** Defines the default Asset Type **/
    private Type type = Type.RESOURCE;
    private Object content;
    private String mimeType;
    private URI ref;
    private String[] contentEncoding;
    private String encoding;
    
    /** 
     * Creates a new instance of Asset 
     * */
    private Asset() {}
    
    /**
     * Create a new instance of Asset defining its Type
     */
    public Asset(final Type type) {
        if (type == null) {
            throw new IllegalArgumentException();
        }
        
        this.type = type;
    }
    
    /**
     * Create a new instance of Asset defining its Type and DIDLBase Identifier
     */
    public Asset(final Type type, final String id) {
        if (type == null || id == null) {
            throw new IllegalArgumentException();
        }
        
        this.type = type;
        
        setId(id);
    }
    
    /**
     * Gets the MimeType for the current Asset instance
     */
    public String getMimeType() {
        return mimeType;
    }
    
    /**
     * Sets the MimeType for the current Asset instance
     */
    public void setMimeType(final String mimeType) {
        this.mimeType = mimeType;
    }
    
    /**
     * Gets the URI reference for the current Asset instance
     */
    public URI getRef() {
        return ref;
    }
    
    /**
     * Sets the URI reference for the current Asset instance
     */
    public void setRef(final URI ref) {
        this.ref = ref;
    }
    
    /**
     * Gets the content encoding type
     */
    public String[] getContentEncoding() {
        return contentEncoding;
    }
    
    /**
     * Gets the content encoding type
     */
    public void setContentEncoding(final String[] contentEncoding) {
        this.contentEncoding = contentEncoding;
    }
    
    /**
     * Gets the encoding type of the Asset (i.e. base64)
     */
    public String getEncoding() {
        return encoding;
    }
    
    /**
     * Sets the encoding type of the Asset (i.e. base64)
     */
    public void setEncoding(final String encoding) {
        this.encoding = encoding;
    }
    
    /**
     * Gets the Content object for this Asset instance
     */
    public Object getContent() {
        return content;
    }
    
    /**
     * Sets the Content object for this Asset instance
     */
    public void setContent(final Object content) {
        this.content = content;
    }
    
    /**
     * Implements Visitor pattern to DIDLInfo object
     */
    public void accept(final DIDLVisitorType visitor) {
        if (visitor == null) {
            throw new IllegalArgumentException();
        }
        
        if (type == Type.RESOURCE) {
            visitor.visitStart((ResourceType)this);
            visitor.visitEnd((ResourceType)this);
        } else {
            visitor.visitStart((StatementType)this);
            visitor.visitEnd((StatementType)this);
        }
    }
}