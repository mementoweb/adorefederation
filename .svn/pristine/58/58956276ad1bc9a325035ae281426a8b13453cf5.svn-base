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

package gov.lanl.ockham.client.oai;

import gov.lanl.registryclient.OAIRegistry;
import gov.lanl.registryclient.RegistryException;
import gov.lanl.registryclient.RegistryRecord;
import gov.lanl.registryclient.parser.MetadataParser;
import gov.lanl.registryclient.parser.ParserContext;
import gov.lanl.registryclient.SnapshotView;
import gov.lanl.registryclient.CachedView;

import gov.lanl.ockham.iesrdata.EntityType;
import gov.lanl.ockham.iesrdata.IESRCollection;
import gov.lanl.ockham.iesrdata.IESRService;

import java.util.Map;

public class OckhamOAIRegistry {

	String prefix = "oai_iesr";

	SnapshotView<IESRCollection> collectionSnapshotView = null;

	SnapshotView<IESRService> serviceSnapshotView = null;
	
	CachedView<IESRCollection> collectionCachedView = null;

	CachedView<IESRService> serviceCachedView = null;

	public OckhamOAIRegistry(String url) throws RegistryException {
		collectionSnapshotView = new SnapshotView<IESRCollection>(new OAIRegistry(url,
				new MetadataParser(new ParserContext(prefix,
						gov.lanl.ockham.iesrdata.IESRCollection.class))));
		
		serviceSnapshotView = new SnapshotView<IESRService>(new OAIRegistry(url, new MetadataParser(
				new ParserContext(prefix,
						gov.lanl.ockham.iesrdata.IESRService.class))));
		
		collectionCachedView = new CachedView<IESRCollection>(new OAIRegistry(url,
			new MetadataParser(new ParserContext(prefix,
					gov.lanl.ockham.iesrdata.IESRCollection.class))),"collections",prefix);
	
		serviceCachedView = new CachedView<IESRService>(new OAIRegistry(url, new MetadataParser(
			new ParserContext(prefix,
					gov.lanl.ockham.iesrdata.IESRService.class))),"services",prefix);
	}

	public Map<String, RegistryRecord<IESRCollection>> listCollections(String from, String until)
			throws RegistryException {
		return collectionSnapshotView.getSnapshot(from, until, EntityType.COLLECTION
				.value(), prefix);
	}

	public Map<String, RegistryRecord<IESRService>> listServices(String from, String until)
			throws RegistryException {
		return serviceSnapshotView.getSnapshot(from, until, EntityType.SERVICE.value(),
				prefix);
	}
	
	public CachedView<IESRCollection> getCollectionsCache() throws RegistryException{
	    	return collectionCachedView;
	    
	}
	
	public CachedView<IESRService> getServicesCache() throws RegistryException{
	    	return serviceCachedView;
	    
	}
	

}
