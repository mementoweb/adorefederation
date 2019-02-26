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

package gov.lanl.xmltape.identifier.openurl;

import gov.lanl.identifier.Identifier;
import gov.lanl.identifier.IndexException;
import gov.lanl.identifier.sru.SRUDC;
import gov.lanl.identifier.sru.SRUSearchRetrieveResponse;
import gov.lanl.xmltape.identifier.index.IdentifierIndexInterface;
import gov.lanl.xmltape.identifier.index.IdentifierIndexRegistry;
import gov.lanl.xmltape.identifier.index.access.RecordAccessor;
import gov.lanl.xmltape.identifier.index.access.RecordAccessorConfig;
import gov.lanl.xmltape.identifier.index.access.RecordAccessorRegistry;
import gov.lanl.xmltape.registry.OAIRegistry;
import gov.lanl.xmltape.registry.RegistryException;
import gov.lanl.xmltape.registry.TapeConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

public class IdentifierResolver implements IdentifierResolverIface {
	protected static OAIRegistry registry;
    
	protected static String idIdxPlugin;
    
    protected static String tapeIdxPlugin;
    
    private static HashMap cachedIdentifierLists = new HashMap();

    protected static IdentifierIndexRegistry idIndexRegistry;
    
    protected static RecordAccessorRegistry raRegistry;
    
    private static Logger log = Logger.getLogger(IdentifierResolver.class.getName());

    private static Properties props;

    public IdentifierResolver() {}
    
    public IdentifierResolver(Properties prop) {
    	setProperties(prop);
    }

    public void setProperties(Properties prop) {
    	this.props = prop;
    	init();
    }
    
    private static void init() {
        try {
            // load registry
            if (props.getProperty("adore-xmltape-registry.OAIURL") != null) {
                registry = new OAIRegistry(props.getProperty("adore-xmltape-registry.OAIURL"));
                log.info(props.getProperty("adore-xmltape-registry.OAIURL"));
            } else {
                throw new Exception("cannot find adore-xmltape-registry.OAIURL");
            }
            if (props.getProperty("adore-xmltape-index.Plugin") != null) {
                tapeIdxPlugin = props.getProperty("adore-xmltape-index.Plugin");
                log.info(props.getProperty("adore-xmltape-index.Plugin"));
            } else {
                throw new Exception("cannot find adore-xmltape-index.Plugin");
            }
            if (props.getProperty("adore-xmltape-index.IdIdxPlugin") != null) {
                idIdxPlugin = props.getProperty("adore-xmltape-index.IdIdxPlugin");
                log.info(props.getProperty("adore-xmltape-index.IdIdxPlugin"));
            } else {
                throw new Exception("cannot find adore-xmltape-index.IdIdxPlugin");
            }
            
            // initialize id index registry
            idIndexRegistry = new IdentifierIndexRegistry();
            raRegistry = new RecordAccessorRegistry();
        } catch (Exception ex) {
            log.error(" IdentifierResolver init failed:" + ex.getMessage());
        }
    }
    
    public byte[] getRecord(String referent, String resolver) throws ResolverException {
        String id = referent;
        String name = resolver;

        try {
            // Get Tape information from Tape Registy
            TapeConfig config = registry.getTapeConfig(name);

            // Get identifier index path from uri
            String idxDir = cleanPath(config.getTapeIndexFile());
            log.debug("Identifier Index: " + idxDir);

            // Get tape path from file uri
            String tape = cleanPath(config.getXMLTapeFile());
            log.debug("Tape: " + tape);

            // Get index path from uri
            String tapeIdxfile = cleanPath(config.getTapeIndexFile());
            log.debug("Index: " + tapeIdxfile);

            long s = System.currentTimeMillis();
            IdentifierIndexInterface idx = null;

            // Next, determine the id type
            String recId = null;
            try {
                idx = idIndexRegistry.getIdentifierIndexImpl(idIdxPlugin, idxDir);
                idx.open(true);
                // check if non doc identifier
                try {
                    recId = idx.getIdentifier(id).getRecordId();
                } catch (Exception e) {
                    log.debug("ID not found, checking for document id");
                }
                // check if doc identifier
                if (recId == null) {
                    try {
                        if (idx.isDocId(referent) != null)
                            recId = id;
                    } catch (IndexException e) {
                        log.debug("Exception attempting to resolve identifier: " + id);
                        e.printStackTrace();
                    }
                }
                idx.close();
            } catch (IndexException e) {
            	log.debug("IndexException: An Error Opening Index");
            } 
            log.debug("Identifier Index Init: " + (System.currentTimeMillis() - s));
            s = System.currentTimeMillis();
            
            if (recId == null)
                throw new IdentifierNotFoundException("Identifier Not Found" + id);

            Properties props = new Properties();
            props.put(RecordAccessorConfig.TAG_TAPE_FILE_ID, config.getXMLTapeId());
            props.put(RecordAccessorConfig.TAG_TAPE_FILE_NAME, tape);
            props.put(RecordAccessorConfig.TAG_TAPE_IDX_FILE, tapeIdxfile);
            props.put(RecordAccessorConfig.TAG_TAPE_IDX_PLUGIN, tapeIdxPlugin);

            RecordAccessor ra = (raRegistry.getRecordAccessor(tape) == null) ? raRegistry.getRecordAccessor(props) : raRegistry.getRecordAccessor(tape);
            log.debug("Record Accessor Init: " + (System.currentTimeMillis() - s));
            s = System.currentTimeMillis();
            byte[] bytes = ra.getMetadata(recId).getBytes();
            log.debug("Record Lookup: " + (System.currentTimeMillis() - s));
            if (bytes == null)
                throw new ResolverException("Unable to resolve identifier: " + id);

            return bytes;

        } catch (RegistryException e) {
            throw new ResolverException("Error attempting to obtain tape configuration: " + e.getMessage());
        } catch (URISyntaxException e) {
            throw new ResolverException("Error processing URI: " + e.getMessage());
        }
    }

    public byte[] getRecordsList(String referent, String resolver) throws ResolverException {
        try {
            // Get Tape information from Tape Registy
            TapeConfig config = registry.getTapeConfig(resolver);
            
            // Get identifier index path from uri
            String idIndexfile = cleanPath(config.getTapeIndexFile());
            log.debug("Identifier Index: " + idIndexfile);
            
            IdentifierIndexInterface idx = idIndexRegistry.getIdentifierIndexImpl(idIdxPlugin, idIndexfile);
            
            ArrayList<Identifier> ids = new ArrayList<Identifier>();
            try {
                idx.open(true);
                ids = idx.getIdentifiers(referent);
                // identity processing for doc identifier
                if (ids.size() == 0) {
                    try {
                        String date = null;
                        if ((date = idx.isDocId(referent)) != null) {
                            ids.add(new Identifier(referent, referent, date));
                        }
                    } catch (IndexException e) {
                        throw new ResolverException("Error occured attempting to obtain record list", e);
                    }
                }
                idx.close();
            } catch (IndexException e) {
                log.debug("ID not found, checking if document id");
            }
            
            SRUSearchRetrieveResponse srr = new SRUSearchRetrieveResponse();
            srr.setSearchRequest("dc.identifier", referent);
            for (Identifier id : ids) {
                SRUDC dc = new SRUDC();
                dc.addKey(SRUDC.Key.IDENTIFIER, id.getRecordId());
                dc.addKey(SRUDC.Key.SOURCE, config.getXMLTapeUri());
                if (id.getDatestamp() != null)
                    dc.addKey(SRUDC.Key.DATE, id.getDatestamp());
                srr.addRecord(dc);
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            SRUSearchRetrieveResponse.write(baos, srr);
            byte[] bytes = baos.toByteArray();

            if (bytes == null)
                throw new ResolverException("Unable to resolve identifier: " + referent);
            
            return bytes; 
            
        } catch (Exception e) {
            throw new ResolverException("Error occured during resolution", e);
        }
    }
    
    public byte[] listIdentifiers(String resolver) throws ResolverException {
        try {
            // Strip off info uri content
            if (resolver.contains("/"))
                resolver = resolver.substring(resolver.lastIndexOf("/") + 1);
            
            // Get Tape information from Tape Registy
            TapeConfig config = registry.getTapeConfig(resolver);
            
            // Get identifier index path from uri
            String idIndexfile = cleanPath(config.getTapeIndexFile());
            log.debug("Identifier Index: " + idIndexfile);
            
            byte[] bytes = null;
            if (cachedIdentifierLists.get(idIndexfile) != null 
                    && ((File) cachedIdentifierLists.get(idIndexfile)).exists()
                       && ((File) cachedIdentifierLists.get(idIndexfile)).length() > 0) {
                File tmp = (File) cachedIdentifierLists.get(idIndexfile);
                bytes = getBytesFromFile(tmp);
            } else {
                IdentifierIndexInterface idx = idIndexRegistry.getIdentifierIndexImpl(idIdxPlugin, idIndexfile);
                idx.open(true);
                bytes = idx.listIdentifiers();
                idx.close();
                File tmp = File.createTempFile("idIdxCache", null);
                tmp.deleteOnExit();
                FileOutputStream fos = new FileOutputStream(tmp);
                fos.write(bytes);
                cachedIdentifierLists.put(idIndexfile, tmp);
            }
            
           return bytes;
            
        } catch (Exception e) {
        	log.error(e.getMessage());
            throw new ResolverException("Error occured during resolution", e);
        }
    }
    
    protected static String cleanPath(String uri) throws URISyntaxException {
        String path = new URI(uri).getPath();
        if (path.charAt(2) == ':')
            path = path.substring(1).replace("/", "\\");
        return path;
    }
    
    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
    
        // Get the size of the file
        long length = file.length();
    
        byte[] bytes = new byte[(int)length];
    
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
        
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
    
        is.close();
        return bytes;
    }
}
