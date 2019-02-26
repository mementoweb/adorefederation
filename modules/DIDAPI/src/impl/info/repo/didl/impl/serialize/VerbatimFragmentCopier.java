/*
 * VerbatimFragmentCopier.java
 *
 * Created on October 12, 2006, 10:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package info.repo.didl.impl.serialize;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Writer;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author liu_x
 */
public class VerbatimFragmentCopier extends DefaultHandler2   {
    
    private Map<String,String> prefixMap;   // Namespace declarations in scope...
    private boolean verbatim = false;       // False if characters need to be XML escaped...
    private Writer buffer;             // Writer to write inline data to...
    /** Creates a new instance of VerbatimFragmentCopier */
    public VerbatimFragmentCopier(Writer writer) {
        // From this moment we have to store the inline data into memory...
        verbatim = true; // First we copy characters literary
        buffer=writer;
    }
    
    
    /**
     * Implements SAX Handler
     */
    public void startElement(
            String uri,
            String localName,
            String qName,
            Attributes attributes)
            throws SAXException {
        try {
            
            
            verbatim = false; // Only when we find XML input we use proper XML escaping
            
            buffer.write("<" + qName);
            for (int i = 0; i < attributes.getLength() ; i++) {
                buffer.write(" " + attributes.getQName(i) + "=\"" + escape(attributes.getValue(i)) + "\"");
            }
            
            if (prefixMap != null) {
                for (Iterator it = prefixMap.keySet().iterator(); it.hasNext() ;) {
                    String prefix = (String) it.next();
                    buffer.write(" xmlns");
                    buffer.write(prefix.equals("")?prefix:":"+prefix);
                    buffer.write("=\"" + escape(prefixMap.get(prefix)) + "\"");
                }
            }
            buffer.write(">");
            
            // P@HACK: we only use the prefixMapping to correctly copy all xmlns declarations
            // after we finised the opening tag we can therefore delete the mapping
            prefixMap = null;
        } catch (Exception e) {
            throw new SAXException(e);
        }
    }
    
    
    /**
     * Implements SAX Handler
     */
    public void endElement(
            String uri,
            String localName,
            String qName)
            throws SAXException {
        try {
            buffer.write("</" + qName + ">");
            
        } catch (Exception e) {
            throw new SAXException("SAXException in endElement", e);
        }
    }
    
    /**
     * Implements SAX Handler
     */
    public void processingInstruction(String target, String data) throws SAXException {
        try{
            buffer.write("<?" + target + " " + data + "?>");
        } catch (Exception e) {
            throw new SAXException("SAXException in processingInstruction", e);
        }
    }
    
    /**
     * Implements SAX Handler
     */
    public void comment(char[] ch, int start, int length) throws SAXException {
        String out = "<!-- " + new String(ch, start, length) + "-->";
        try{
            buffer.write(out.toCharArray(), 0, out.length());
        } catch (Exception e) {
            throw new SAXException("SAXException in processingInstruction", e);
        }
    }
    
    /**
     * Implements SAX Handler
     */
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        if (prefixMap == null) {
            prefixMap = new HashMap<String,String>();
        }
        prefixMap.put(prefix, uri);
    }
    
    /**
     * Implements SAX Handler
     */
    public void endPrefixMapping(String prefix) throws SAXException {
        prefixMap = null;
        
    }
    
    
    
    /**
     * Implements SAX Handler
     */
    public void startCDATA() throws SAXException {
        try{
            verbatim = true;
            buffer.write("<![CDATA[");
        } catch (Exception e) {
            throw new SAXException("SAXException in starting CDATA", e);
        }
    }
    
    /**
     * Implements SAX Handler
     */
    public void endCDATA() throws SAXException {
        try{
            
            buffer.write("]]>");
            verbatim = false;
        } catch (Exception e) {
            throw new SAXException("SAXException in ending CDATA", e);
        }
    }
    
    /**
     * Implements SAX Handler
     */
    public void characters(char[] ch, int start, int length)
    throws SAXException {
        
        try{
            String out = escape(new String(ch, start, length));
            buffer.write(out.toCharArray(), 0, out.length());
        } catch (Exception e) {
            throw new SAXException("SAXException in processing characters", e);
        }
    }
    
    private String escape(String str) {
        if (verbatim) {
            return str;
        } else {
            return str.replaceAll("&", "&amp;")
            .replaceAll("<", "&lt;")
            .replaceAll(">", "&gt;")
            .replaceAll("\"", "&quot;")
            .replaceAll("'", "&apos;");
        }
    }
    
}
