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

package gov.lanl.registryclient;

import gov.lanl.registryclient.parser.MetadataParser;
import gov.lanl.registryclient.parser.Metadata;

import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

import ORG.oclc.oai.harvester.verb.ListRecords;
import ORG.oclc.oai.harvester.verb.Record;
import ORG.oclc.oai.harvester.verb.GetRecord;

/**
 * Repository abstracts a typical OAI-PMH usecase in LANL, given an oai
 * repository, the client may ask a list of objects following a specific range,
 * and this set of object (snapshot) might be cached. The concept "snapshot" is
 * import for some application, e.g. federator. Because a client request may
 * last very long, and the service shall ensure integrity of responding this
 * request by using "snapshot".
 * 
 * This pattern can modeled as:
 * 
 * <code>
 * Repository<Format> r=new Repository<Format>(baseurl);
 * Snapshot<String,Format> ss=r.getSnapshot<String,Format>(from,until,set,prefix);
 * </code>
 *
 */

public class OAIRegistry<T extends Metadata> implements RegistryInterface {
    private String baseURL;

    private MetadataParser parser = null;

    /**
     * Creates a new OAIRegistry instance for given baseUrl
     * 
     * @param baseURL
     *            baseURL of reposiotry index
     */
    public OAIRegistry(String baseURL) {
        this(baseURL, null);
    }

    /**
     * Creates a new OAIRegistry instance for given baseUrl
     * 
     * @param baseURL
     *            baseURL of reposiotry index
     * @param parser
     *            metadata parser instance for registry
     * 
     */
    public OAIRegistry(String baseURL, MetadataParser parser) {
        this.baseURL = baseURL;
        this.parser = parser;
    }

    /**
     * Sets the metadata parser instance for registry
     * @param ps
     *            metadataparser implementation
     */
    public void setParser(MetadataParser ps) {
        this.parser = ps;
    }

    /**
     * Gets the metadata parser instance for registry
     * @return an metadataparser implementation
     */
    public MetadataParser getParser() {
        return this.parser;
    }

    /**
     * List Registry Records 
     * 
     * @param from
     *            from date, usually issued by oai request.
     * @param until
     *            until date, usually issued by oai request.
     * @param setSpec
     *            oai setSpec, if available
     * @param prefix
     *            oai metadataPrefix
     * @return a hashmap of id:record pairs
     */
    public Map<String, RegistryRecord<T>> listRecords(String from,
            String until, String setSpec, String prefix)
            throws RegistryException {

        Map<String, RegistryRecord<T>> snapshot = new HashMap<String, RegistryRecord<T>>();
        try {
            ListRecords lr;
            URL url = new URL(baseURL);
            lr = new ListRecords(url, from, until, setSpec, prefix);
            addResponse(snapshot, lr, prefix);

            while (lr.getResumptionToken() != null) {
                lr = new ListRecords(new URL(baseURL), lr.getResumptionToken());
                addResponse(snapshot, lr, prefix);
            }
            return snapshot;
        } catch (Exception ex) {
            throw new RegistryException(ex);
        }
    }

    /**
     * Gets a Registry Record
     * 
     * @param identifier
     *           the OAI identifier for the record
     * @param prefix
     *           the metadataPrefix of the output format
     * @return the requested record as a RegistryRecord instance 
     */
    public RegistryRecord getRecord(String identifier, String prefix)
            throws RegistryException, ItemNotExist {
        GetRecord gr;
        try {
            gr = new GetRecord(new URL(baseURL), identifier, prefix);
        } catch (Exception ex) {
            throw new RegistryException(ex);
        }
        Iterator i = gr.iterator();
        if (i.equals(null) || (!i.hasNext()))
            throw new ItemNotExist(i + " " + prefix);
        try {
            Record r = (Record) i.next();
            RegistryRecord item = new RegistryRecord(r.getIdentifier(), r
                    .getDatestamp(), parser.parse(r.getMetadata(), prefix));
            return item;
        } catch (Exception ex) {
            throw new RegistryException(ex);
        }
    }

    private void addResponse(Map<String, RegistryRecord<T>> snapshot,
            ListRecords lr, String prefix) throws Exception {

        for (Iterator it = lr.iterator(); it.hasNext();) {
            Record r = (Record) it.next();
            RegistryRecord item = new RegistryRecord(r.getIdentifier(), r
                    .getDatestamp(), parser.parse(r.getMetadata(), prefix));
            snapshot.put(r.getIdentifier(), item);
        }
    }
}
