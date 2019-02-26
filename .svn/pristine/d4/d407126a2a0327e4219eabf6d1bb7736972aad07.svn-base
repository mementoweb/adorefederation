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

package gov.lanl.adore.helper;

import gov.lanl.locator.IdLocation;
import gov.lanl.locator.IdLocatorProxy;
import gov.lanl.ockham.client.oai.OckhamOAIRegistry;
import gov.lanl.ockham.iesrdata.IESRCollection;
import gov.lanl.ockham.iesrdata.IESRService;
import gov.lanl.util.resource.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Properties;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to obtain resource from XMLtape / ARCfile Resolver
 * @author rchute
 */
public class ResourceManager {
    /** ID Locator Resolver URL Property */
	static final String ADORE_ID_LOCATOR_RESOLVERURL = "adore-id-locator.ResolverURL";

    /** Service Registry OAI-PMH URL Property */
	static final String ADORE_SERVICE_REGISTRY_OAIURL = "adore-service-registry.OAIURL";

    /** OpenURL Service Property */
    public static final String ADORE_SERVICE_RESOLVERURL = "adore-disseminator.ServiceResolverURL";
    
    /**  XMLtape Type Indicator */
	public static final String TYPE_XMLTAPE = "xmltape";

    /**  ARCfile Type Indicator */
	public static final String TYPE_ARCFILE = "arc";
    
	private Properties props;

	private IdLocatorProxy locatorProxy;

	private OckhamOAIRegistry svcRegistry;

	private XMLTapeResolverProxy tapeResolver;

	private ArcResolverProxy arcResolver;
    
    private String svcResolverUrl;

    /**
     * Constructor used to initialize required properties
     * @param props
     *        Properties object containing svcReg & IdLoc URLs
     * @throws Exception
     */
	public ResourceManager(Properties props) throws Exception {
		this.props = props;
		this.init();
	}

	private void init() throws Exception {
		if (!props.containsKey(ADORE_ID_LOCATOR_RESOLVERURL))
			throw new Exception("Missing configuration: "
					+ ADORE_ID_LOCATOR_RESOLVERURL);
		if (!props.containsKey(ADORE_SERVICE_REGISTRY_OAIURL))
			throw new Exception("Missing configuration: "
					+ ADORE_SERVICE_REGISTRY_OAIURL);
        if (props.containsKey(ADORE_SERVICE_RESOLVERURL)) {
            svcResolverUrl=props.getProperty(ADORE_SERVICE_RESOLVERURL);
        }
        
		// init id locator proxy
		URL locatorUrl = new URL((String) props
				.get(ADORE_ID_LOCATOR_RESOLVERURL));
		locatorProxy = new IdLocatorProxy(locatorUrl);

		// init service registry
		String svcRegUrl = (String) props.get(ADORE_SERVICE_REGISTRY_OAIURL);
		svcRegistry = new OckhamOAIRegistry(svcRegUrl);

		// init xmltape resolver proxy
		tapeResolver = new XMLTapeResolverProxy();

		// init arcfile resolver proxy
		arcResolver = new ArcResolverProxy();
	}

	/**
	 * Gets the repository type of the resource: ARCfile or XMLtape
	 * 
	 * @param identifier
     *        a resource identifier
	 * @return
     *        xmltape or arc to indicate repository type
	 * @throws IdNotFoundException
	 * @throws ResourceManagerException
	 */
	public String getResourceType(String identifier)
			throws IdNotFoundException, ResourceManagerException {
		try {
			ArrayList<String> al = new ArrayList<String>();
			for (IdLocation i : locatorProxy.get(identifier)) {
				IESRCollection collection = svcRegistry.getCollectionsCache()
						.getRecord(i.getRepo()).getMetaData();
				al.addAll(collection.getTypes());
			}
			if (al.contains(TYPE_ARCFILE))
				return TYPE_ARCFILE;
			else if (al.contains(TYPE_XMLTAPE))
				return TYPE_XMLTAPE;
			else
				throw new IdNotFoundException(identifier + "  not found.");
		} catch (Exception ex) {
			throw new ResourceManagerException(ex);
		}
	}

    /**
     * Gets datastream / content-type for a given identifier and type
     * @param identifier
     *        a resource identifier
     * @param type
     *        xmltape or arc to indicate repository type
     * @return
     *        A Resource object containing bitstream and content-type 
     * @throws ResourceManagerException
     */
	public Resource getResource(String identifier, String type)
			throws ResourceManagerException {
		Resource resource = new Resource();
		String collectionId = null;
		URL serviceUrl = null;
		IESRCollection c = null;
		IESRCollection collection = null;

		try {
			for (IdLocation i : locatorProxy.get(identifier)) {
				collection = svcRegistry.getCollectionsCache().getRecord(
						i.getRepo()).getMetaData();
				if (collection.getTypes().contains(type)) {
					c = collection;
					collectionId = i.getRepo();
					break;
				}
			}

			if (collection == null)
				throw new IdNotFoundException(identifier + "  not found.");
			if (c == null)
				throw new Exception("Requested collection type not found");

		} catch (Exception e) {
			throw new ResourceManagerException(e);
		}

		// get the openurl service information for the resource
        try {
            // get the service interfaces
            Set<String> services = (Set<String>) c.getServices();
            for (String service : services) {
                IESRService serviceProfile = svcRegistry.getServicesCache()
                        .getRecord(service).getMetaData();
                if (serviceProfile.getType().equals("openurl") 
                        && (serviceProfile.getSupportsStandard().equals("openurl-aDORe1") 
                                || serviceProfile.getSupportsStandard().equals("openurl-aDORe4"))) {
                    serviceUrl = new URL(serviceProfile.getLocator());
                    break;
                }
            }
        } catch (Exception e) {
            throw new ResourceManagerException(e);
        }

		if (serviceUrl == null)
			throw new ResourceManagerException(
					"Unable to determine openurl:openurl-1_0 serviceUrl");

		// get the resource
		try {
			if (type == TYPE_XMLTAPE) {
				resource = tapeResolver.get(serviceUrl, identifier);
			} else if (type == TYPE_ARCFILE) {
				resource = arcResolver.get(serviceUrl, identifier);
			}
		} catch (Exception e) {
			throw new ResourceManagerException(e);
		}

		if (resource == null)
			throw new ResourceManagerException(
					"Error occurred attempting to obtain resource");

		return resource;
	}

    /**
     * Gets OAI-PMH Service Information for a repository
     * @param identifier
     *        a resource identifier
     * @return
     *        an object containing service type and location information
     */
	public IESRService getOAIServiceInfo(String identifier) {
		IdLocation id = null;
		String collectionId = null;
		IESRService serviceProfile = null;
		try {
			id = locatorProxy.get(identifier).get(0);
			collectionId = id.getRepo();
			if (id == null)
				throw new IdNotFoundException(identifier + "  not found.");

			IESRCollection c = svcRegistry.getCollectionsCache().getRecord(
					collectionId).getMetaData();

			// get the service interfaces
			Set<String> services = (Set<String>) c.getServices();
			for (String service : services) {
				serviceProfile = svcRegistry.getServicesCache().getRecord(
						service).getMetaData();
				if (serviceProfile.getType().equals("oai-pmh")
						&& serviceProfile.getSupportsStandard().equals(
								"oai-pmh-2_0")) {
					return serviceProfile;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serviceProfile;
	}

    /**
     * Gets a list of the repositories an identifier resides in
     * @param identifier
     *        a resource identifier
     * @return
     *        List of identifier location objects
     * @throws ResourceManagerException
     */
	public List<IdLocation> getLocations(String identifier) throws ResourceManagerException{
		ArrayList<IdLocation> locations=new ArrayList<IdLocation>();
		
		try {
			for (IdLocation i : locatorProxy.get(identifier)) {
				IESRCollection collection = svcRegistry.getCollectionsCache().getRecord(
						i.getRepo()).getMetaData();
				if (collection.getTypes().contains(TYPE_ARCFILE)) { //copy arc location to result set
					locations.add(i);
				}
				else if (collection.getTypes().contains(TYPE_XMLTAPE)){ //get response from xmltape
					Set<String> services = (Set<String>) collection.getServices();
					for (String service : services) {
						IESRService serviceProfile = svcRegistry.getServicesCache()
								.getRecord(service).getMetaData();
                         if (serviceProfile.getType().equals("openurl") 
                                && serviceProfile.getSupportsStandard().equals("openurl-aDORe2")) {
							URL serviceUrl = new URL(serviceProfile.getLocator());
							locations.addAll(tapeResolver.getLocations(serviceUrl, identifier));
						}
					}	
				}
			}
			return locations;

		} catch (Exception e) {
			throw new ResourceManagerException(e);
		}
	}

    /**
     * Main Method - Parses command line args<br>
     * 
     * Expects the following args:<br>
     *   [props]<br>
     *        path to properties file<br>
     *   [resourceId]<br>
     *        the unique identifier of the datastream<br>
     *   [repoType]
     *        xmltape / arc
     *   [outputFile]<br>
     *        path to output file
     * @param args
     *        String Array containing processing configurations
     */
	public static void main(String[] args) {
        if (args.length != 4) {
            System.out
                    .println("Usage: gov.lanl.adore.helper.ResourceManager [props] [resourceId] [repoType] [outputFile]");
            System.exit(0);
        }
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(args[0]));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            ResourceManager rm = new ResourceManager(props);
            long s = System.currentTimeMillis();
            Resource resource = rm.getResource(args[1], args[2]);
            System.out.println("Time to get resource: "
                    + (System.currentTimeMillis() - s));
            System.out.println("Content-type: " + resource.getContentType());
            System.out.println("Writing resource to: " + args[3]);
            FileOutputStream fos = new FileOutputStream(new File(args[3]));
            fos.write(resource.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public String getServiceResolverUrl() {
        return svcResolverUrl;
    }
}
