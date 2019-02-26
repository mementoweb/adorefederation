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

package gov.lanl.federator;

import java.io.FileReader;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;

import ORG.oclc.oai.server.catalog.AbstractCatalog;
import ORG.oclc.oai.server.verb.BadResumptionTokenException;
import ORG.oclc.oai.server.verb.CannotDisseminateFormatException;
import ORG.oclc.oai.server.verb.IdDoesNotExistException;
import ORG.oclc.oai.server.verb.NoMetadataFormatsException;
import ORG.oclc.oai.server.verb.NoSetHierarchyException;
import ORG.oclc.oai.server.verb.OAIInternalServerError;

import gov.lanl.util.oai.TokenModem;
import gov.lanl.util.oai.oaiharvesterwrapper.ListIdentifiers;
import gov.lanl.util.oai.oaiharvesterwrapper.ListRecords;
import gov.lanl.util.oai.oaiharvesterwrapper.GetRecord;
import gov.lanl.util.oai.oaiharvesterwrapper.SetformatException;
import gov.lanl.util.oai.oaiharvesterwrapper.Sets;
import gov.lanl.util.oai.oaiharvesterwrapper.Header;
import gov.lanl.util.oai.oaiharvesterwrapper.Record;
import gov.lanl.util.properties.GenericPropertyManager;

import gov.lanl.locator.IdLocation;
import gov.lanl.ockham.client.oai.OckhamOAIRegistry;
import gov.lanl.ockham.iesrdata.IESRCollection;
import gov.lanl.ockham.iesrdata.IESRService;
import gov.lanl.registryclient.RegistryRecord;
import gov.lanl.registryclient.RegistryException;
import gov.lanl.adore.helper.IdNotFoundException;
import gov.lanl.adore.helper.ResourceManager;
import gov.lanl.federator.tapefilter.TapeFilterException;
import gov.lanl.federator.processor.DIDLProcessor;
import gov.lanl.federator.schematable.MapTable;
import gov.lanl.federator.tapefilter.Proxy;
import gov.lanl.format.registry.FormatIndex;
import gov.lanl.format.registry.FormatItem;

public class DIDLOAICatalog extends AbstractCatalog {

    private GenericPropertyManager loader;

    private static Logger log = Logger.getLogger(DIDLOAICatalog.class.getName());

    private OckhamOAIRegistry serviceRegistry = null;

    private ResourceManager resourceManager;

    /**
     * Construct a DIDLOAICatalog object
     * 
     * @param properties
     *            a properties object containing initialization parameters
     * @exception IOException
     *                an I/O error occurred during database initialization.
     */
    public DIDLOAICatalog(Properties properties) throws Exception {
        try {
            loader = new GenericPropertyManager();
            loader.addAll(properties);

            String[] mandatoryList = { FedConstants.ADORE_FEDERATOR_CONFIGDIR,
                    FedConstants.ADORE_SERVICE_REGISTRY_OAIBAEURL,
                    FedConstants.ADORE_ID_LOCATOR_RESOLVERURL,
                    FedConstants.ADORE_FORMAT_REGISTRY_OAIBASEURL };
            for (String s : mandatoryList) {
                if (loader.getProperty(s) == null)
                    throw new NullPointerException("configuration " + s
                            + " not found");
            }

            if (loader.getProperty(FedConstants.ADORE_FEDERATOR_NUMBERTHREADS) == null)
                loader.setProperty(FedConstants.ADORE_FEDERATOR_NUMBERTHREADS, "1");

            if (loader.getProperty(FedConstants.ADORE_FEDERATOR_EMPTYSET) == null)
                loader.setProperty(FedConstants.ADORE_FEDERATOR_EMPTYSET, "false");

            if (loader.getProperty(FedConstants.TIME_GRANULARITY) == null)
                loader.setProperty(FedConstants.TIME_GRANULARITY, "YYYY-MM-DDThh:mm:ssZ");

            if (loader.getProperty(FedConstants.ADORE_FEDERATOR_RECORD_PREFIX) == null)
                loader.setProperty(FedConstants.ADORE_FEDERATOR_RECORD_PREFIX, "didl");
            
            MapTable.parse(new InputSource(new FileReader(loader
                    .getProperty(FedConstants.ADORE_FEDERATOR_CONFIGDIR)
                    + FedConstants.FEDERATOR_XML_PATH)));
            serviceRegistry = new OckhamOAIRegistry(loader
                    .getProperty(FedConstants.ADORE_SERVICE_REGISTRY_OAIBAEURL));

            resourceManager = new ResourceManager(loader.getProperties());

            log.info(properties);

        } catch (Exception ex) {
            log.fatal(ex);
            throw ex;
        }

    }

    /**
     * Retrieve the aDORe Resource Type associated with the specified
     * identifier.
     * 
     * @param identifier
     *            the OAI identifier
     * @return the aDORe Resource Type (i.e. arcfile, xmltape, etc)
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

        try {
            resourceManager.getResourceType(identifier);
        } catch (IdNotFoundException ex) {
            throw new IdDoesNotExistException(ex.toString());

        } catch (Exception ex) {
            log.warn(ex.toString());
            throw new OAIInternalServerError(ex.toString());
        }
        try {
            return MapTable.getSchemaLocations();
        } catch (Exception ex) {
            throw new OAIInternalServerError(ex.toString());
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
            NoSetHierarchyException, CannotDisseminateFormatException {
        
        // until is mandatory in order to keep consistent among tapes
        // if until is not used, we will use current time as until.
        if ((until == null) || (until.indexOf("9999") != -1)) {
            until = getTime();
        }
        log.debug("until=" + until);

        try {
            return processListIdentifiers(from, until, set, metadataPrefix, null, null);
        } catch (Exception ex) {
            log.warn(ex.toString());
            System.out.println(ex);
            throw new OAIInternalServerError(ex.toString());
        }
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

        FedToken token;
        try {
            resumptionToken = URLDecoder.decode(resumptionToken, "UTF-8");
            token = new FedToken(TokenModem.decode(resumptionToken));
            log.debug("token=" + token);
        } catch (Exception ex) {
            log.warn(ex.toString());
            throw new BadResumptionTokenException();
        }

        try {
            return processListIdentifiers(token.from, token.until, token.set,
                    token.prefix, token.tape, token.tapetoken);
        }

        catch (Exception ex) {
            ex.printStackTrace();
            throw new OAIInternalServerError(ex.toString());
        }

    }

    private Map processListIdentifiers(String from, String until, String set,
            String metadataPrefix, String tape, String tapetoken)
            throws Exception {

        Map listIdentifiersMap = new HashMap();
        ArrayList<Header> headers = new ArrayList<Header>();
        ArrayList<String> identifiers = new ArrayList<String>();
        try {
            Proxy proxy = new Proxy(Proxy.Operation.LISTIDENTIFIERS,
                    serviceRegistry.listCollections(null, until),
                    serviceRegistry.listServices(null, until), from, until,
                    set, tape, tapetoken);

            if (proxy.getResult() != null) {
                ListIdentifiers li = (ListIdentifiers) (proxy.getResult());
                log.debug("get " + li.size() + " records");
                headers = li.getXMLHeaders();
                identifiers = li.getIdentifiers();

                FedToken token = new FedToken(from, until, set, metadataPrefix,
                        li.getCollectionId(), li.getResumptionToken());
                log.debug("token=" + token);
                listIdentifiersMap.put("resumptionMap",
                        getResumptionMap(TokenModem.encode(token)));

            }
        } catch (SetformatException ex) {
            log.warn("wrong set in request");
        }

        /***********************************************************************
         * END OF CUSTOM CODE SECTION
         **********************************************************************/
        listIdentifiersMap.put("headers", headers.iterator());
        listIdentifiersMap.put("identifiers", identifiers.iterator());
        return listIdentifiersMap;
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

        try {
            IESRService service = resourceManager.getOAIServiceInfo(identifier);
            GetRecord gr = null;
            try {
                gr = new GetRecord(service.getLocator(), identifier, loader.getProperty(FedConstants.ADORE_FEDERATOR_RECORD_PREFIX));
            } catch (Exception e) {
                // do nothing, diff oaiharvester impl handle this issue diff
            }

            if (gr == null || gr.size() == 0) {
                List<IdLocation> ids = resourceManager.getLocations(identifier);
                gr = new GetRecord(service.getLocator(), ids.get(0).getId(),
                        "didl");
            }

            gr.setCollectionId(service.getServes());
            ArrayList<String> records = processRecords(gr.getRecords(),
                    metadataPrefix);
            return (String) (records.get(0));
        } catch (IdNotFoundException ex) {
            throw new IdDoesNotExistException(ex.toString());
        } catch (Exception ex) {
            log.warn(ex.toString());
            throw new OAIInternalServerError(ex.toString());
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
     *         (containing XML <record/>Strings) and an optional "resumptionMap"
     *         Map.
     * @exception OAIInternalServerError
     *                signals an http status code 500 problem
     * @exception NoSetHierarchyException
     *                The repository doesn't support sets.
     * @exception CannotDisseminateFormatException
     *                the metadataPrefix isn't supported by the item.
     */
    public Map listRecords(String from, String until, String set,
            String metadataPrefix) throws OAIInternalServerError,
            NoSetHierarchyException, CannotDisseminateFormatException {
        // until is mandatory in order to keep consistent among tapes
        // if until is not used, we will use current time as until.
        if ((until == null) || (until.indexOf("9999") != -1)) {
            until = getTime();
        }

        try {
            return processListRecords(from, until, set, metadataPrefix, null, null);
        } catch (Exception ex) {
            log.warn(ex.toString());
            System.out.println(ex);
            throw new OAIInternalServerError(ex.toString());
        }

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
        FedToken token;
        try {
            resumptionToken = URLDecoder.decode(resumptionToken, "UTF-8");
            token = new FedToken(TokenModem.decode(resumptionToken));
            log.debug("token=" + token);
        } catch (Exception ex) {
            log.warn(ex.toString());
            throw new BadResumptionTokenException();
        }

        try {
            return processListRecords(token.from, token.until, token.set,
                    token.prefix, token.tape, token.tapetoken);
        } catch (Exception ex) {
            log.warn(ex.toString());
            System.out.println(ex);
            throw new OAIInternalServerError(ex.toString());
        }

    }

    private Map processListRecords(String from, String until, String set,
            String prefix, String tape, String rt) throws SetformatException,
            TapeFilterException, RegistryException, Exception {
        Map listRecordsMap = new HashMap();
        ArrayList<String> records = new ArrayList<String>();
        try {
            Proxy proxy = new Proxy(Proxy.Operation.LISTRECORDS,
                    serviceRegistry.listCollections(null, until),
                    serviceRegistry.listServices(null, until), from, until,
                    set, tape, rt);

            if (proxy.getResult() != null) {
                ListRecords lr = (ListRecords) (proxy.getResult());
                log.debug("get " + lr.size() + " records");
                records = processRecords(lr.getRecords(), prefix);

                FedToken newtoken = new FedToken(from, until, set, prefix, lr
                        .getCollectionId(), lr.getResumptionToken());
                log.debug("newtoken=" + newtoken);
                listRecordsMap.put("resumptionMap", getResumptionMap(TokenModem
                        .encode(newtoken)));

            }
        } catch (SetformatException ex) {
            log.warn("wrong format exception " + ex);
        }
        listRecordsMap.put("records", records.iterator());
        return listRecordsMap;
    }

    private ArrayList<String> processRecords(ArrayList<Record> records,
            String prefix) throws Exception {
        ArrayList<String> results = new ArrayList<String>();
        if (MapTable.getMapTable().get(prefix).getService() != null) {
            String xslfile = loader
                    .getProperty(FedConstants.ADORE_FEDERATOR_CONFIGDIR)
                    + "/xslt/"
                    + MapTable.getMapTable().get(prefix).getService();
            DIDLProcessor processor = new DIDLProcessor(
                    records,
                    Integer.parseInt(loader.getProperty(FedConstants.ADORE_FEDERATOR_NUMBERTHREADS)),
                    xslfile);
            HashMap<String, String> result = processor.process();
            for (Record r : records) {
                StringBuilder builder = new StringBuilder("<record>").append(
                        r.getHeader().getHeaderXML()).append("<metadata>")
                        .append(result.get(r.getHeader().getIdentifier()))
                        .append("</metadata></record>");
                results.add(builder.toString());
            }
        } else {
            for (Record r : records) {
                StringBuilder builder = new StringBuilder("<record>").append(
                        r.getHeader().getHeaderXML()).append("<metadata>")
                        .append(r.getMetadata()).append("</metadata></record>");
                results.add(builder.toString());
            }
        }
        return results;

    }

    /**
     * Retrieve a list of sets that satisfy the specified criteria; will return 
     * set information for all collections, services, and formats. Associated
     * OAI records will not exist for most formats.
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
        ArrayList allsets = new ArrayList();
        ArrayList colls = new ArrayList();
        try {
            String time = getTime();
            log.debug("Until Time: " + getTime());
            Map<String, RegistryRecord<IESRCollection>> collections = serviceRegistry
                    .listCollections(null, time);

            for (RegistryRecord<IESRCollection> r : collections.values()) {
                Sets set = new Sets(Sets.BASE_URL, r.getIdentifier());

                ByteArrayOutputStream output = new ByteArrayOutputStream();
                r.getMetaData().write(output);

                allsets.add("<set><setSpec>" + set.getSetSpec()
                        + "</setSpec><setDescription>"
                        + new String(output.toByteArray())
                        + "</setDescription></set>");

                if (r.getMetaData().getIsPartOf() != null) {
                    for (String s : r.getMetaData().getIsPartOf()) {
                        set = new Sets(Sets.COLLECTION, s);
                        String setspec = new String("<set><setSpec>"
                                + set.getSetSpec() + "</setSpec></set>");
                        log.debug(setspec);
                        if (!colls.contains(setspec)) {
                            colls.add(setspec);
                        }
                    }
                }
            }

            // add all collections
            for (Iterator it = colls.iterator(); it.hasNext();) {
                String coll = (String) (it.next());
                allsets.add(coll);
            }

            // add all formats
            FormatIndex formatIndex = new FormatIndex(loader.getProperty(FedConstants.ADORE_FORMAT_REGISTRY_OAIBASEURL),false);
            for (Iterator it = formatIndex.getList().iterator(); it.hasNext();) {
                FormatItem ti = (FormatItem) (it.next());
                Sets set = new Sets(Sets.FORMAT, Sets.encodeSetSpec(ti.getOAIIdentifier()));
                allsets.add(new String("<set><setSpec>" + set.getSetSpec()
                        + "</setSpec><setDescription>" + ti.getRecord()
                        + "</setDescription></set>"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new OAIInternalServerError(ex.toString());
        }
        listSetsMap.put("sets", allsets.iterator());
        return listSetsMap;
    }

    /**
     * Retrieve the next set of sets associated with the resumptionToken; 
     * the initial listSets returns all set records, only an empty Map 
     * will be returned.
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
        return listSetsMap;
    }

    /**
     * Use the current date as the basis for the resumptiontoken
     * 
     * @return a String version of the current time
     */
    private synchronized static String getResumptionId() {
        Date now = new Date();
        return Long.toString(now.getTime());
    }

    private String getTime() {
        Date date = new Date();
        SimpleDateFormat formatter;
        if (loader.getProperty(FedConstants.TIME_GRANULARITY).equals(
                "YYYY-MM-DD"))
            formatter = new SimpleDateFormat("yyyy-MM-dd");
        else
            formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        TimeZone tz = TimeZone.getTimeZone("UTC");
        formatter.setTimeZone(tz);
        return formatter.format(date);
    }

    /**
     * close the repository
     */
    public void close() {
    }

}
