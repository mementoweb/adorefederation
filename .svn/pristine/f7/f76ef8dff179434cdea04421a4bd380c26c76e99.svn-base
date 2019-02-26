/*
 * DIEXTSerializer.java
 *
 * Created on October 18, 2005, 1:25 PM
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

package org.adore.didl.serialize;

import info.repo.didl.DIDLException;
import info.repo.didl.impl.serialize.AttributeSerializer;
import info.repo.didl.serialize.DIDLDeserializerType;
import info.repo.didl.serialize.DIDLSerializationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;
import org.adore.didl.attribute.DIEXT;

/**
 * <code>DIEXTSerializer</code> implements the <code>AttributeSerializer</code> and manages
 * the administrative data which is to be placed in the top level didl element.  The created 
 * and modified timestamps from the referenced schema are utilized.
 * 
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class DIEXTSerializer implements AttributeSerializer, DIDLDeserializerType {
    public static final String DIEXT_CREATED = "DIDLDocumentCreated";
    public static final String DIEXT_MODIFIED = "DIDLDocumentModified";
    public static final String DIEXT_PREFIX = "diext";
    public static final String DIEXT_NAMESPACE = "http://library.lanl.gov/2005-08/aDORe/DIDLextension/";
    public static final String DIEXT_SCHEMA = "http://purl.lanl.gov/aDORe/schemas/2006-09/DIDLextension.xsd";
    
    /**
     * Create a new DIEXTSerializer instance
     */
    public DIEXTSerializer() {
    }
    
    /**
     * Gets the namespace constant
     */
    public String getNamespace() {
        return DIEXT_NAMESPACE;
    }
    
    /**
     * Gets the schemaLocation constant
     */
    public String getSchema() {
        return DIEXT_SCHEMA;
    }
    
    /**
     * Writes a DIEXT object to specified OutputStream as XML fragment
     */
    public void write(OutputStream stream, Object object) throws DIDLSerializationException {
        DIEXT diext = (DIEXT) object;
        PrintWriter out = new PrintWriter(stream, true);
        
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        
        if (diext.getCreated() != null) {
            String date = sdf.format(diext.getCreated());
            out.print(DIEXT_PREFIX + ":" + DIEXT_CREATED + "=\"" + date + "\" ");
        }
        
        if (diext.getLastModified() != null) {
            String date = sdf.format(diext.getLastModified());
            out.print(DIEXT_PREFIX + ":" + DIEXT_MODIFIED + "=\"" + date + "\" ");
        }
        
        out.print("xmlns:" + DIEXT_PREFIX + "=\"" + DIEXT_NAMESPACE + "\"");
        
        out.close();
    }
    
    /**
     * Reads XML fragment from InputStream to create DIEXT object
     */
    public Object read(InputStream stream) throws DIDLSerializationException {
        DIEXT diext = new DIEXT();
        try {
            ObjectInputStream in = new ObjectInputStream(stream);
            Map nvmap = (Map) in.readObject();
            in.close();
            
            String created = (String) nvmap.get(DIEXTSerializer.DIEXT_CREATED);
            String modified = (String) nvmap.get(DIEXTSerializer.DIEXT_MODIFIED);

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT+0"));
            
            if (created != null) {
                diext.setCreated(formatter.parse(created));
            }
            
            if (modified != null) {
                diext.setLastModified(formatter.parse(modified));
            }
        } 
        catch (ParseException e) {
            throw new DIDLSerializationException(e.getMessage());
        }
        catch (ClassNotFoundException e) {
            throw new DIDLSerializationException(e.getMessage());
        }
        catch (IOException e) {
            throw new DIDLException(DIDLException.UNKNOWN_ERROR, e.getMessage());
        }
        return diext;
    }
    
    
     /**
     * set property
     *
     *@param id property identifier
     *@param value property value
     *@throws If the property value can't be assigned or retrieved.
     */
    public void setProperty(String id, String value) throws DIDLSerializationException {
        throw new DIDLSerializationException("no property is supported");
    }
    
    /**
     *   Look up the value of a property.
     *@throws If the property value can't be assigned or retrieved.
     */
    public String getProperty(String id) throws DIDLSerializationException{
         throw new DIDLSerializationException("no property is supported");
    }
}
