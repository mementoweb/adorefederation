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

package gov.lanl.disseminator.service.toc;

import gov.lanl.disseminator.model.ContextObjectContainer;
import gov.lanl.disseminator.model.Entity;
import gov.lanl.disseminator.service.DefaultXSLTService;
import gov.lanl.util.resource.Resource;
import info.openurl.oom.config.ClassConfig;
import info.openurl.oom.config.OpenURLConfig;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;

public class REMRDFService extends DefaultXSLTService {
	private static Logger logger = Logger.getLogger(REMRDFService.class
			.getName());
	// private String generator = null;
	// private String generatoruri = null;
	// if no permalink service
	private String baseuri = null;
	private String openurl = null;

	/**
	 * Construct a Hello World web service class.
	 * 
	 * @param openURLConfig
	 * @param classConfig
	 * @throws TransformerException
	 */
	public REMRDFService(OpenURLConfig openURLConfig, ClassConfig classConfig)
			throws TransformerException {
		super(openURLConfig, classConfig);
		// this.generator=classConfig.getArg("generator");
		// this.generatoruri=classConfig.getArg("generatoruri");
		this.baseuri = classConfig.getArg("baseuri");
		this.openurl = classConfig.getArg("openurl");

	}

	@Override
	public Resource serve(ContextObjectContainer co) throws Exception {
		// if (input == null) throw new NullPointerException("no resource passed
		// in");
		// Entity referent = co.getReferentMeanIt();
		// String srvname = co.getServiceType().getProperty("svc_id");
		co.getServiceType().setProperty("rem", "true");
		// co.getServiceType().setProperty("bib", "true");

		// HashMap args = referent.getParams(srvname);
		REMCreator rm = new REMCreator();
		Entity resolver = co.getResolver();
		String res_url;
		if (resolver.hasProperty("res_id")) {
			res_url = resolver.getProperty("res_id");
		} else {
			res_url = baseuri;
		}
		HashMap marcXML = getMetaData(co);
		rm.setMarcXML(marcXML);
		// co.getReferentMeanIt().addParamToService(srvname, "rem", "true");
		String output;

		String oflag;
		Entity service = co.getServiceType();
		if (service.hasProperty("svc.openurl")) {
			oflag = service.getProperty("svc.openurl");
			System.out.println("oflag:" + oflag);
		} else {
			oflag = openurl;
		}

		if (oflag.equals("true")) {
			rm.setFlag(true);
		} else {
			rm.setFlag(false);
		}

		// rm.setGenerator(generatoruri, generator);
		rm.setBaseURI(res_url);
		output = rm.getAtomXML(co);

		Resource result = new Resource();

		String rdfxml = transform(output);

		result.setBytes(rdfxml.getBytes("UTF-8"));
		// result.setContentType("text/html");
		result.setContentType("application/xml");
		// result.setBytes(output.getBytes());

		return result;
	}

	public URI getServiceID() throws URISyntaxException {
		// TODO Auto-generated method stub
		return null;
	}

}
