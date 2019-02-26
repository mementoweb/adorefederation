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

package gov.lanl.xmltape.oai;

import gov.lanl.util.oai.Token;
import gov.lanl.util.oai.TokenModem;
import gov.lanl.xmltape.SingleTapeReader;
import gov.lanl.xmltape.TapeRecord;
import gov.lanl.xmltape.index.TapeIndexInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import ORG.oclc.oai.server.catalog.AbstractCatalog;
import ORG.oclc.oai.server.verb.BadResumptionTokenException;
import ORG.oclc.oai.server.verb.CannotDisseminateFormatException;
import ORG.oclc.oai.server.verb.IdDoesNotExistException;
import ORG.oclc.oai.server.verb.NoItemsMatchException;
import ORG.oclc.oai.server.verb.NoMetadataFormatsException;
import ORG.oclc.oai.server.verb.NoSetHierarchyException;
import ORG.oclc.oai.server.verb.OAIInternalServerError;

/**
 * TapeOAICatalog.java 
 * @author liu_x
 */

public class TapeOAICatalog extends AbstractCatalog {
   
    /**
     * Maximum number of entries to return for ListRecords and ListIdentifiers
     */
    Config tapeConfiguration;

    private SingleTapeReader singletapeReader;

    static Logger log = Logger.getLogger(TapeOAICatalog.class.getName());
    
    /**
     * Pending resumption tokens
     */
    private HashMap resumptionResults = new HashMap();

    /**
     * Construct a TapeOAICatalog object
     * 
     * @param properties
     *            a properties object containing initialization parameters
     * @exception IOException
     *                an I/O error occurred during database initialization.
     */
    public TapeOAICatalog(Properties properties) throws IOException {
        tapeConfiguration = new Config(properties);
        
        try {
            log.debug(tapeConfiguration.getIndexDBDir());
            log.debug(tapeConfiguration.getdbname());
            
            String plugin = tapeConfiguration.getIndexPlugin();
            Class impl = Class.forName(plugin);
            log.debug("Plugin: " + plugin);
            TapeIndexInterface indexdb = (TapeIndexInterface) impl.newInstance();
            indexdb.setTapeName(tapeConfiguration.getdbname());
            indexdb.setIndexDir(tapeConfiguration.getIndexDBDir());
            
            //BDBIndex indexdb = new BDBIndex(tapeConfiguration
            //        .getIndexDBDir(), tapeConfiguration.getdbname());
            
            singletapeReader = new SingleTapeReader(indexdb, tapeConfiguration
                    .getXmltapeFile());
            singletapeReader.open();
        } catch (gov.lanl.xmltape.TapeException ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException(
                    "cannot open tape, indexDBDir="
                            + tapeConfiguration.getIndexDBDir()
                            + ",xmltapeFile="
                            + tapeConfiguration.getXmltapeFile());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve a list of schemaLocation values associated with the specified
     * identifier.
     * 
     * @param identifier
     *            the OAI identifier
     * @return a Vector containing schemaLocation Strings
     * @exception OAIInternalServerError
     *                signals an http status code 500 problem
     * @exception IdDoesNotExistException
     *                the specified identifier can't be found
     * @exception NoMetadataFormatsException
     *                the specified identifier was found but the item is flagged
     *                as deleted and thus no schemaLocations (i.e.
     *                metadataFormats) can be produced.
     */
    public Vector getSchemaLocations(String identifier)
            throws OAIInternalServerError, IdDoesNotExistException,
            NoMetadataFormatsException {

        Object nativeItem = null;
        try {
            nativeItem = singletapeReader.getRecord(identifier);
        } catch (gov.lanl.xmltape.TapeException ex) {
            throw new OAIInternalServerError(identifier);
        }

        if (nativeItem == null) {
            throw new IdDoesNotExistException(identifier);
        } else {
            return getRecordFactory().getSchemaLocations(nativeItem);
        }

    }

    /**
     * Retrieve a list of identifiers that satisfy the specified criteria
     * 
     * @param from
     *            beginning date using the proper granularity
     * @param until
     *            ending date using the proper granularity
     * @param set
     *            the set name or null if no such limit is requested
     * @param metadataPrefix
     *            the OAI metadataPrefix or null if no such limit is requested
     * @return a Map object containing entries for "headers" and "identifiers"
     *         Iterators (both containing Strings) as well as an optional
     *         "resumptionMap" Map. It may seem strange for the map to include
     *         both "headers" and "identifiers" since the identifiers can be
     *         obtained from the headers. This may be true, but
     *         AbstractCatalog.listRecords() can operate quicker if it doesn't
     *         need to parse identifiers from the XML headers itself. Better
     *         still, do like I do below and override
     *         AbstractCatalog.listRecords(). AbstractCatalog.listRecords() is
     *         relatively inefficient because given the list of identifiers, it
     *         must call getRecord() individually for each as it constructs its
     *         response. It's much more efficient to construct the entire
     *         response in one fell swoop by overriding listRecords() as I've
     *         done here.
     * @exception OAIInternalServerError
     *                signals an http status code 500 problem
     * @exception NoSetHierarchyException
     *                the repository doesn't support sets.
     * @exception CannotDisseminateFormatException
     *                the metadata format specified is not supported by your
     *                repository.
     */
    public Map listIdentifiers(String from, String until, String set,
            String metadataPrefix) throws OAIInternalServerError,
            NoSetHierarchyException, CannotDisseminateFormatException,
            NoItemsMatchException {
        OAI2TapeAdapter adapter = new OAI2TapeAdapter(tapeConfiguration, from,
                until, set);
        return buildListIdentifiers(null, adapter.getFrom(),
                adapter.getUntil(), adapter.getSet(), metadataPrefix);

    }

    /**
     * Retrieve the next set of identifiers associated with the resumptionToken
     * 
     * @param resumptionToken
     *            implementation-dependent format taken from the previous
     *            listIdentifiers() Map result.
     * @return a Map object containing entries for "headers" and "identifiers"
     *         Iterators (both containing Strings) as well as an optional
     *         "resumptionMap" Map.
     * @exception BadResumptionTokenException
     *                the value of the resumptionToken is invalid or expired.
     * @exception OAIInternalServerError
     *                signals an http status code 500 problem
     */
    public Map listIdentifiers(String resumptionToken)
            throws BadResumptionTokenException, OAIInternalServerError {

        Token token;
        try {
            token = TokenModem.decode(resumptionToken);
            return buildListIdentifiers(token.userinfo, token.from,
                    token.until, token.set, token.prefix);
        } catch (NoSetHierarchyException ex) {
            log.warn(ex.getMessage());
            throw new BadResumptionTokenException();
        } catch (Exception ex) {
            log.warn(ex.getMessage());
            throw new BadResumptionTokenException();
        }
    }

    /**
     * Retrieve the specified metadata for the specified identifier
     * 
     * @param identifier
     *            the OAI identifier
     * @param metadataPrefix
     *            the OAI metadataPrefix
     * @return the <record/>portion of the XML response.
     * @exception OAIInternalServerError
     *                signals an http status code 500 problem
     * @exception CannotDisseminateFormatException
     *                the metadataPrefix is not supported by the item.
     * @exception IdDoesNotExistException
     *                the identifier wasn't found
     */
    public String getRecord(String identifier, String metadataPrefix)
            throws OAIInternalServerError, CannotDisseminateFormatException,
            IdDoesNotExistException {
        Object nativeItem = null;
        try {
            nativeItem = singletapeReader.getRecord(identifier);
        } catch (gov.lanl.xmltape.TapeException ex) {
            throw new OAIInternalServerError("cannot open tape");
        }

        if (nativeItem == null) {
            throw new IdDoesNotExistException(identifier);
        } else {
            return constructRecord(nativeItem, metadataPrefix);
        }

    }

    /**
     * Retrieve a list of records that satisfy the specified criteria. Note,
     * though, that unlike the other OAI verb type methods implemented here,
     * both of the listRecords methods are already implemented in
     * AbstractCatalog rather than abstracted. This is because it is possible to
     * implement ListRecords as a combination of ListIdentifiers and GetRecord
     * combinations. Nevertheless, I suggest that you override both the
     * AbstractCatalog.listRecords methods here since it will probably improve
     * the performance if you create the response in one fell swoop rather than
     * construct it one GetRecord at a time.
     * 
     * @param from
     *            beginning date using the proper granularity
     * @param until
     *            ending date using the proper granularity
     * @param set
     *            the set name or null if no such limit is requested
     * @param metadataPrefix
     *            the OAI metadataPrefix or null if no such limit is requested
     * @return a Map object containing entries for a "records" Iterator object
     *         (containing XML <record/>Strings) and an optional
     *         "resumptionMap" Map.
     * @exception OAIInternalServerError
     *                signals an http status code 500 problem
     * @exception NoSetHierarchyException
     *                The repository doesn't support sets.
     * @exception CannotDisseminateFormatException
     *                the metadataPrefix isn't supported by the item.
     */
    public Map listRecords(String from, String until, String set,
            String metadataPrefix) throws OAIInternalServerError,
            NoSetHierarchyException, CannotDisseminateFormatException,
            NoItemsMatchException {

        OAI2TapeAdapter adapter = new OAI2TapeAdapter(tapeConfiguration, from,
                until, set);
        return buildListRecords(null, adapter.getFrom(), adapter.getUntil(),
                adapter.getSet(), metadataPrefix);
    }

    /**
     * Retrieve the next set of records associated with the resumptionToken
     * 
     * @param resumptionToken
     *            implementation-dependent format taken from the previous
     *            listRecords() Map result.
     * @return a Map object containing entries for "headers" and "identifiers"
     *         Iterators (both containing Strings) as well as an optional
     *         "resumptionMap" Map.
     * @exception OAIInternalServerError
     *                signals an http status code 500 problem
     * @exception BadResumptionTokenException
     *                the value of the resumptionToken argument is invalid or
     *                expired.
     */
    public Map listRecords(String resumptionToken)
            throws BadResumptionTokenException, OAIInternalServerError {
        Token token;
        try {
            token = TokenModem.decode(resumptionToken);
            return buildListRecords(token.userinfo, token.from, token.until,
                    token.set, token.prefix);
        } catch (NoSetHierarchyException ex) {
            log.warn(ex.getMessage());
            throw new BadResumptionTokenException();
        } catch (Exception ex) {
            log.warn(ex.getMessage());
            throw new BadResumptionTokenException();
        }
    }

    /**
     * Utility method to construct a Record object for a specified
     * metadataFormat from a native record
     * 
     * @param nativeItem
     *            native item from the dataase
     * @param metadataPrefix
     *            the desired metadataPrefix for performing the crosswalk
     * @return the <record/>String
     * @exception CannotDisseminateFormatException
     *                the record is not available for the specified
     *                metadataPrefix.
     */
    private String constructRecord(Object nativeItem, String metadataPrefix)
            throws CannotDisseminateFormatException {
        String schemaURL = null;

        if (metadataPrefix != null) {
            if ((schemaURL = getCrosswalks().getSchemaURL(metadataPrefix)) == null)
                throw new CannotDisseminateFormatException(metadataPrefix);
        }
        return getRecordFactory().create(nativeItem, schemaURL, metadataPrefix);
    }

    /**
     * Retrieve a list of sets that satisfy the specified criteria
     * 
     * @return a Map object containing "sets" Iterator object (contains
     *         <setSpec/>XML Strings) as well as an optional resumptionMap Map.
     * @exception OAIBadRequestException
     *                signals an http status code 400 problem
     * @exception OAIInternalServerError
     *                signals an http status code 500 problem
     */
    public Map listSets() throws NoSetHierarchyException,
            OAIInternalServerError {
        Map listSetsMap = new HashMap();
        List sets = new ArrayList();
        try {
            if (!tapeConfiguration.useForcedSets()) {
                for (Iterator it = singletapeReader.getOAISetSpecs().iterator(); it
                        .hasNext();) {
                    SetSpec spec = new SetSpec((String) (it.next()));
                    sets.add(spec.getxml());
                }
            } else {
                sets = tapeConfiguration.getXMLSets();
            }

            listSetsMap.put("sets", sets.iterator());
            return listSetsMap;
        } catch (Exception ex) {
            log.warn(ex.getMessage());
            throw new OAIInternalServerError(ex.getMessage());
        }
    }

    /**
     * Retrieve the next set of sets associated with the resumptionToken
     * 
     * @param resumptionToken
     *            implementation-dependent format taken from the previous
     *            listSets() Map result.
     * @return a Map object containing "sets" Iterator object (contains
     *         <setSpec/>XML Strings) as well as an optional resumptionMap Map.
     * @exception BadResumptionTokenException
     *                the value of the resumptionToken is invalid or expired.
     * @exception OAIInternalServerError
     *                signals an http status code 500 problem
     */
    public Map listSets(String resumptionToken)
            throws BadResumptionTokenException, OAIInternalServerError {
        Map listSetsMap = new HashMap();
        ArrayList sets = new ArrayList();
        listSetsMap.put("sets", sets.iterator());
        return listSetsMap;
    }

    /**
     * close the repository
     */
    public void close() {
        try {
            singletapeReader.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * create resumptionToken
     * 
     * @param v
     *            records of this response
     * @param map
     *            HashMap to store resumptionToken
     * @param from
     * @param until
     * @param set
     * @param prefix
     * @param userinfo
     */
    private void createResumptionToken(Vector v, Map map, String from,
            String until, String set, String prefix) {
        TapeRecord record = (TapeRecord) (v.lastElement());
        StringBuffer resumptionTokenSb = new StringBuffer();
        Token token = new Token(record.getDatestamp(), until, set, prefix,
                record.getIdentifier());
        map.put("resumptionMap", getResumptionMap(TokenModem.encode(token)));
    }

    private Map buildListIdentifiers(String userinfo, String from,
            String until, String set, String metadataPrefix)
            throws OAIInternalServerError, NoSetHierarchyException,
            CannotDisseminateFormatException, NoItemsMatchException {

        Map listIdentifiersMap = new HashMap();
        ArrayList headers = new ArrayList();
        ArrayList identifiers = new ArrayList();

        Vector v = null;

        try {
            v = singletapeReader.getRecords(userinfo, tapeConfiguration
                    .getMaxListSize(), set, from, until);
        } catch (gov.lanl.xmltape.TapeException ex) {
            throw new OAIInternalServerError("cannot open tape");
        }

        if (v.size() == 0)
            throw new NoItemsMatchException();

        /* load the headers and identifiers ArrayLists. */
        for (Iterator it = v.iterator(); it.hasNext();) {
            String[] header = getRecordFactory().createHeader(
                    (TapeRecord) (it.next()));
            headers.add(header[0]);
            identifiers.add(header[1]);
        }

        /* decide if you're done */
        if (v.size() == tapeConfiguration.getMaxListSize()) {
            createResumptionToken(v, listIdentifiersMap, from, until, set,
                    metadataPrefix);

        }
        /***********************************************************************
         * END OF CUSTOM CODE SECTION
         **********************************************************************/
        listIdentifiersMap.put("headers", headers.iterator());
        listIdentifiersMap.put("identifiers", identifiers.iterator());
        return listIdentifiersMap;
    }

    private Map buildListRecords(String userinfo, String from, String until,
            String set, String metadataPrefix) throws OAIInternalServerError,
            NoSetHierarchyException, CannotDisseminateFormatException,
            NoItemsMatchException {

        Map listRecordsMap = new HashMap();
        ArrayList records = new ArrayList();
        Vector v = null;
        try {
            v = singletapeReader.getRecords(userinfo, tapeConfiguration
                    .getMaxListSize(), set, from, until);
        } catch (gov.lanl.xmltape.TapeException ex) {
            throw new OAIInternalServerError("cannot open tape");
        }

        if (v.size() == 0)
            throw new NoItemsMatchException();

        for (Iterator it = v.iterator(); it.hasNext();) {
            String record = constructRecord(it.next(), metadataPrefix);
            records.add(record);
        }

        if (records.size() == tapeConfiguration.getMaxListSize()) {
            createResumptionToken(v, listRecordsMap, from, until, set,
                    metadataPrefix);
        }
        /***********************************************************************
         * END OF CUSTOM CODE SECTION
         **********************************************************************/
        listRecordsMap.put("records", records.iterator());
        return listRecordsMap;
    }
}
