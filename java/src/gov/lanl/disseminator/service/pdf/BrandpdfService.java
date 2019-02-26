/*
 * Copyright (c) 2008  Los Alamos National Security, LLC.
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

package gov.lanl.disseminator.service.pdf;

import gov.lanl.adore.helper.ResourceManager;
import gov.lanl.disseminator.matchmaker.MatchMakerCo;
import gov.lanl.disseminator.model.ContextObjectContainer;
import gov.lanl.disseminator.model.Entity;
import gov.lanl.disseminator.util.pdf.BrandText;

import info.openurl.oom.config.ClassConfig;
import info.openurl.oom.config.OpenURLConfig;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;

import gov.lanl.util.resource.Resource;
import gov.lanl.disseminator.service.AbstractService;

public class BrandpdfService extends AbstractService {
	private static String text = "LANL Research Library";
	
	/**
	 * Construct a Hello World web service class.
	 * 
	 * @param openURLConfig
	 * @param classConfig
	 * @throws TransformerException
	 */
	public BrandpdfService(OpenURLConfig openURLConfig, ClassConfig classConfig)
			throws TransformerException {

		super(openURLConfig, classConfig);
	}

	@Override
	public Resource serve(ContextObjectContainer co) throws Exception {
		// rewrote it by asumption
		// is pdf by reference? did not have example to test
		ResourceManager rm = getResourceManager();
		// Resource r = new Resource();

		List l = new ArrayList();
		MatchMakerCo maker = new MatchMakerCo();
		maker.defList(l);
		maker.match(co);
		l = maker.getResource();

		Entity e = (Entity) l.get(0);

		String arcid = e.getProperty(Entity.IDENTIFIER_ATT);

		Resource ori = rm.getResource(arcid, "arc");
		BrandText bt = new BrandText();
		byte[] bytes = bt.brand(ori.getBytes(), text);
		Resource result = new Resource();
		result.setContentType("application/pdf");
		result.setBytes(bytes);
		return result;
	}

	public URI getServiceID() throws URISyntaxException {
		// TODO Auto-generated method stub
		return null;
	}

}
