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

package gov.lanl.xmltape.identifier.index.bdbImpl;

import gov.lanl.identifier.IndexException;
import gov.lanl.xmltape.identifier.index.IdentifierIndexInterface;
import gov.lanl.xmltape.identifier.index.access.RecordAccessor;
import gov.lanl.xmltape.identifier.index.access.RecordAccessorConfig;
import gov.lanl.xmltape.identifier.openurl.IdentifierNotFoundException;
import gov.lanl.xmltape.identifier.openurl.IdentifierResolver;
import gov.lanl.xmltape.identifier.openurl.ResolverException;
import gov.lanl.xmltape.index.TapeIndexInterface;
import gov.lanl.xmltape.registry.RegistryException;
import gov.lanl.xmltape.registry.TapeConfig;

import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class BDBIdentifierResolver extends IdentifierResolver {
    
	private static Logger log = Logger.getLogger(BDBIdentifierResolver.class.getName());
	
	public BDBIdentifierResolver() {
		super();
	}
	
    public byte[] getRecord(String referent, String resolver) throws ResolverException {
        String id = referent;
        String name = resolver;

        try {
            // Get Tape information from Tape Registry
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
            	idx = idIndexRegistry.getIdentifierIndex(idIdxPlugin, idxDir);
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

            RecordAccessor ra = (raRegistry.getRecordAccessor(tape) == null) ? raRegistry.getRecordAccessor(props, (TapeIndexInterface) idx, idx) : raRegistry.getRecordAccessor(tape);
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
    
}
