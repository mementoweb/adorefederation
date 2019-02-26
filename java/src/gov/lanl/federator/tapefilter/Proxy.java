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

package gov.lanl.federator.tapefilter;

import java.util.Map;
import java.util.TreeMap;
import java.util.SortedMap;
import java.util.Set;

import org.apache.log4j.Logger;

import gov.lanl.ockham.iesrdata.IESRCollection;
import gov.lanl.ockham.iesrdata.IESRService;
import gov.lanl.registryclient.RegistryRecord;
import gov.lanl.util.oai.oaiharvesterwrapper.SetformatException;
import gov.lanl.util.oai.oaiharvesterwrapper.Sets;
import gov.lanl.util.oai.oaiharvesterwrapper.ListRecords;
import gov.lanl.util.oai.oaiharvesterwrapper.ListIdentifiers;

/**
 * proxy to environment, the effort is to hide details of getting DIDL from the
 * repository
 * 
 * @author liu_x
 * 
 */
public class Proxy {

    public enum Operation {
        LISTRECORDS, LISTIDENTIFIERS
    }

    private MagicFilter filter;

    private TreeMap<String, RegistryRecord<IESRCollection>> collections;

    private Map<String, RegistryRecord<IESRService>> services;

    private Object result;

    private static Logger log = Logger.getLogger(Proxy.class.getName());

    public Proxy(Operation type,
            Map<String, RegistryRecord<IESRCollection>> collections,
            Map<String, RegistryRecord<IESRService>> services, String from,
            String until, String setSpec) throws SetformatException,
            TapeFilterException {
        this(type, collections, services, from, until, setSpec, null, null);
    }

    public Proxy(Operation type,
            Map<String, RegistryRecord<IESRCollection>> colls,
            Map<String, RegistryRecord<IESRService>> services, String from,
            String until, String setSpec, String tape,
            String tapeResumptionToken) throws SetformatException,
            TapeFilterException {

        // use a treemap to guarantee same sequence
        this.collections = new TreeMap<String, RegistryRecord<IESRCollection>>(colls);
        this.services = services;
        Sets set = new Sets(setSpec);
        String tapesetSpec = null;
        // only format or collection based sets are valid for an XMLTape
        if ((set.getType() == Sets.FORMAT)
                || (set.getType() == Sets.COLLECTION)) {
            tapesetSpec = set.getSetSpec();
        }

        filter = new MagicFilter(from, until, set);

        // start from beginning
        if (tape == null)
            result = harvest(type, collections, from, until, tapesetSpec);
        else {
            if (tapeResumptionToken == null) {
                // if there is no resumptionToken, we process next tape
                collections.remove(tape);
                result = harvest(type, collections.tailMap(tape), from, until, tapesetSpec);
            } else {
                // with resumption, we will continue to process current tape
                Object obj = harvest(type, tape, tapeResumptionToken);
                if (obj == null) {
                    collections.remove(tape);
                    result = harvest(type, collections.tailMap(tape), from, until, tapesetSpec);
                } else {
                    result = obj;
                }
            }
        }
    }

    /**
     * get processing result
     * 
     * @return ListRecords or ListIdentifiers. Or NULL if harvest is finished
     */
    public Object getResult() {
        return result;
    }

    private Object harvest(Operation type,
            SortedMap<String, RegistryRecord<IESRCollection>> map, String from,
            String until, String tapesetSpec) throws TapeFilterException {
        try {
            for (Map.Entry<String, RegistryRecord<IESRCollection>> entry : map
                    .entrySet()) {
                if (filter.accept(entry.getValue())) {
                    IESRCollection coll = entry.getValue().getMetaData();

                    if (coll.getTypes().contains("arc"))
                        continue;
                    
                    // contruct from/until
                    String f = from;
                    String u = until;

                    if (Operation.LISTRECORDS.equals(type)) {
                        ListRecords lr = new ListRecords(getOAILocator(coll), f, u, tapesetSpec, "didl");
                        log.debug(getOAILocator(coll) + " " + f + " " + u + " "  + tapesetSpec);
                        if (lr.size() != 0) {
                            lr.setCollectionId(coll.getIdentifier());
                            return lr;
                        }
                    }

                    else if (Operation.LISTIDENTIFIERS.equals(type)) {
                        ListIdentifiers li = new ListIdentifiers(getOAILocator(coll), f, u, tapesetSpec, "didl");
                        if (li.size() != 0) {
                            li.setCollectionId(coll.getIdentifier());
                            return li;
                        }
                    }
                }
            }
            return null;
        } catch (Exception ex) {
            throw new TapeFilterException(ex);
        }
    }

    private Object harvest(Operation type, String collid, String resumptionToken)
            throws TapeFilterException {
        try {
            IESRCollection collection = collections.get(collid).getMetaData();
            String rt = resumptionToken;
            for (;;) {

                if (Operation.LISTRECORDS.equals(type)) {
                    ListRecords lr = new ListRecords(getOAILocator(collection),
                            rt);
                    if (lr.size() != 0) {
                        lr.setCollectionId(collid);
                        return lr;
                    } else if (lr.getResumptionToken() != null) {
                        rt = lr.getResumptionToken();
                    } else
                        break;
                }

                else if (Operation.LISTIDENTIFIERS.equals(type)) {
                    ListIdentifiers li = new ListIdentifiers(
                            getOAILocator(collection), rt);
                    if (li.size() != 0) {
                        li.setCollectionId(collid);
                        return li;
                    } else if (li.getResumptionToken() != null) {
                        rt = li.getResumptionToken();
                    } else
                        break;
                }
            }
            return null;
        } catch (Exception ex) {
            throw new TapeFilterException(ex);
        }
    }

    private String getOAILocator(IESRCollection coll)
            throws TapeFilterException {
        try {

            Set<String> mysrvIds = coll.getServices();
            for (String mysrvId : mysrvIds) {
                IESRService service = ((IESRService) (services.get(mysrvId)
                        .getMetaData()));
                if ("oai-pmh".equals(service.getType())) {
                    return service.getLocator();
                }
            }
            throw new TapeFilterException("no oai-pmh interface for "
                    + coll.getIdentifier());

        } catch (Exception ex) {
            throw new TapeFilterException(
                    "exception in looking oai-pmh interface for "
                            + coll.getIdentifier(), ex);
        }
    }
}
