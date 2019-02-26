/*
 * DCTerms.java
 *
 * Created on November 28, 2005, 10:06 AM
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

package org.adore.didl.content;

import info.repo.didl.serialize.DIDLDeserializerType;
import info.repo.didl.serialize.DIDLSerializationException;
import info.repo.didl.serialize.DIDLSerializerType;

import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * <code>DCTerms</code> defines the object container and serializers for
 * all DCTerms content type references. The Key enumeration provides a list
 * of valid Key names; the DCTermsParser iterates through them to ensure 
 * validity.
 * 
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class DCTerms implements  DIDLSerializerType, DIDLDeserializerType {
    
    /**
     * An enumeration of keys defined by DCTerms
     */
    public enum Key { 
        ALTERNATIVE("alternative"), TABLE_OF_CONTENTS("tableOfContents"), ABSTRACT("abstract"),
        CREATED("created"), VALID("valid"), AVAILABLE("available"), ISSUED("issued"), MODIFIED("modified"), DATE_ACCEPTED("dateAccepted"),
        DATE_COPYRIGHTED("dateCopyrighted"),DATE_SUBMITTED("dateSubmitted"), EXTENT("extent"), MEDIUM("medium"), IS_VERSION_OF("isVersionOf"),
        HAS_VERSION("hasVersion"), IS_REPLACED_BY("isReplacedBy"), REPLACES("replaces"), IS_REQUIRED_BY("isRequiredBy"), REQUIRES("requires"),
        IS_PART_OF("isPartOf"),HAS_PART("hasPart"), IS_REFERENCED_BY("isReferencedBy"), REFERENCES("references"), IS_FORMAT_OF("isFormatOf"),
        HAS_FORMAT("hasFormat"),CONFORMS_TO("conformsTo"), SPATIAL("spatial"), TEMPORAL("temporal"), AUDIENCE("audience"), MEDIATOR("mediator"),
        EDUCATION_LEVEL("educationLevel"),ACCESS_RIGHTS("accessRights"), BIBLIOGRAPHIC_CITATION("bibliographicCitation");
        
        private String value=null;
        
        Key(String value) {
            this.value = value;
        }
        
        /**
         * Gets the value of the Key instance
         * @return value
         */
        public String value() {
            return value;
        }
        
    };
    
    /** DCTerms XML Namespace */
    public static final String NAMESPACE = "http://purl.org/dc/terms/";
    /** DCTerms XML SchemaLocation */
    public static final String SCHEMA_LOCATION = "http://purl.lanl.gov/aDORe/schemas/2006-09/dcterms.xsd";
    /** DCTerms XML Schema Prefix */
    public static final String PREFIX = "dcterms";
    
    private Key key;
    private String value;
    
    /** Creates a new DII instance */
    public DCTerms() {
    }
    
    /** Creates a new DII instance with the specified key/value pair */
    public DCTerms(Key key, String value) {
        super();
        this.key = key;
        this.value = value;
    }
    
    /** Sets the Key for the DCTerm instance */
    public void setKey(Key key){
        this.key=key;
    }
    
    /** Sets the Value for the DCTerm instance */
    public void setValue(String value){
        this.value=value;
    }
    
    /** Gets the key name for the DCTerm instance */
    public Key getKey() {
        return key;
    }
    
    /** Gets the key value for the DCTerm instance */
    public String getValue() {
        return value;
    }
    
    /**
     * Writes a DCTerm object to the specifed OutputStream as a DCTerms XML Fragment
     */
    public void write(OutputStream stream, Object obj) throws DIDLSerializationException {
        DCTerms terms=(DCTerms)obj;
        try{
            PrintWriter out = new PrintWriter(new OutputStreamWriter(stream,XMLConstants.DEFAULT_ENCODING), true);
            out.print("<"+PREFIX+":");
            out.print(terms.getKey().value());
            out.print(" "+"xmlns:"+PREFIX+"=\""+ NAMESPACE+"\" " );
            out.print("xmlns:" + XMLConstants.XSI_PREFIX  + "=\"" + XMLConstants.XSI_NAMESPACE  + "\" ");
            out.print("xsi:schemaLocation=\""+NAMESPACE+" "+SCHEMA_LOCATION+"\">");
            out.print(terms.getValue());
            out.print("</"+PREFIX+":"+terms.getKey().value()+">");
            out.close();
        } catch (Exception ex) {
            throw new DIDLSerializationException(ex.getMessage());
        }
    }
    
    /**
     * Reads a DCTerms XML Fragment from specified InputStream 
     * and returns a populated DCTerms instance. 
     */
    public DCTerms read(InputStream stream) throws DIDLSerializationException{
        try {
            DCTerms terms=new DCTerms();
            DCTermsParser parser=new DCTermsParser();
            parser.parse(terms,stream);
            return terms;
        } catch (Exception ex) {
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
