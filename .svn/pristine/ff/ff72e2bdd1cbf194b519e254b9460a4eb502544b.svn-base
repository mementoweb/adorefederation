/*
 * DiadmSerializer.java
 *
 * Created on October 13, 2005, 10:19 AM
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

import info.repo.didl.serialize.DIDLSerializationException;
import info.repo.didl.serialize.DIDLSerializerType;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.adore.didl.content.DC;
import org.adore.didl.content.DCTerms;
import org.adore.didl.content.Diadm;
import org.adore.didl.content.XMLConstants;

/**
 * <code>DiadmSerializer</code>  defines the XML serializer for
 * all Diadm content type references.
 *
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class DiadmSerializer implements DIDLSerializerType{
    /** Diadm XML Namespace */
    public static final String DIADM_NAMESPACE = "http://library.lanl.gov/2005-08/aDORe/DIADM/";
    /** Diadm XML SchemaLocation */             
    public static final String DIADM_SCHEMA_LOCATION = "http://purl.lanl.gov/aDORe/schemas/2006-09/DIADM.xsd";
    /** Diadm XML Prefix */
    public static final String DIADM_PREFIX = "diadm";
    /** Diadm XML Local Name */
    public static final String DIADM_LOCALNAME="DIADM";
    
    /**
     * Writes a Diadm object to the specifed OutputStream as a Diadm XML Fragment
     */
    public void write(OutputStream stream, Object object) throws  DIDLSerializationException{
        try{
            Diadm diadm = (Diadm) object;
            PrintWriter out = new PrintWriter(stream, true);
            out.println();
            out.print("<"+DIADM_PREFIX+":");
            out.print(DIADM_LOCALNAME);
            out.print(" "+"xmlns:"+DIADM_PREFIX+"=\""+ DIADM_NAMESPACE+"\" " );
            out.print("xmlns:" + XMLConstants.XSI_PREFIX  + "=\"" + XMLConstants.XSI_NAMESPACE  + "\" ");
            out.print("xsi:schemaLocation=\""+DIADM_NAMESPACE+" "+DIADM_SCHEMA_LOCATION+"\">");
            out.println();
            //serialize DC
            for (DC dc: diadm.getDC()){
                ByteArrayOutputStream dcStream = new ByteArrayOutputStream();
                dc.write(dcStream,dc);
                out.println(dcStream.toString(XMLConstants.DEFAULT_ENCODING));
                dcStream.close();
            }
            
            //serialize DCTerms
            for (DCTerms dcterm: diadm.getDCTerms()){
                ByteArrayOutputStream termStream = new ByteArrayOutputStream();
                dcterm.write(termStream,dcterm);
                out.println(termStream.toString(XMLConstants.DEFAULT_ENCODING));
                termStream.close();
            }
            
            out.print("</"+DIADM_PREFIX+":"+DIADM_LOCALNAME+">");
            out.println();
            out.close();
        } catch (Exception ex){
            throw new DIDLSerializationException(ex.getMessage());
        }
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
