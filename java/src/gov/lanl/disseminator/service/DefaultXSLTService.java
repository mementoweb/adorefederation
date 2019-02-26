package gov.lanl.disseminator.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import gov.lanl.disseminator.matchmaker.MatchMakerCo;
import gov.lanl.disseminator.model.ContextObjectContainer;
import gov.lanl.disseminator.model.Entity;
import gov.lanl.util.resource.Resource;
import gov.lanl.util.xslt.XSLTPool;
import gov.lanl.util.xslt.XSLTTransformer;

import info.openurl.oom.config.ClassConfig;
import info.openurl.oom.config.OpenURLConfig;

import javax.xml.transform.TransformerException;

public class DefaultXSLTService extends AbstractService {
	public DefaultXSLTService(OpenURLConfig openURLConfig,
			ClassConfig classConfig) throws TransformerException {
		super(openURLConfig, classConfig);
		this.xsltpath = (classConfig.getArg("xsltpath"));
		this.mimetype = (classConfig.getArg("mimetype"));
		this.type = (classConfig.getArg("type"));
		// TODO Auto-generated constructor stub
	}

	private String xsltpath = null;
	private String mimetype = null;
	private String type = null;

	/**
	 * @param openURLConfig
	 * @param classConfig
	 * @throws TransformerException
	 */
	/*
	 * public DefaultXSLTService(OpenURLConfig openURLConfig, ClassConfig
	 * classConfig) {
	 * 
	 * this.xsltpath=(classConfig.getArg("xsltpath"));
	 * this.mimetype=(classConfig.getArg("mimetype"));
	 * this.type=(classConfig.getArg("type")); }
	 */

	public String getXSLTPath() {
		return xsltpath;
	}

	public String getMimeType() {
		return mimetype;
	}

	public String getType() {
		return type;
	}

	public String transform(String input) throws Exception {
		XSLTTransformer xtran = XSLTPool.borrowObject(getXSLTPath());
		String output = xtran.transform(input);
		XSLTPool.returnObject(getXSLTPath(), xtran);
		return output;

	}

	// MOD - added 9/30/08 - to handle params for stylesheets - fk
	public String transform(String input, Map params) throws Exception {
		XSLTTransformer xtran = XSLTPool.borrowObject(getXSLTPath());
		Set keySet = params.keySet();
		Iterator it = keySet.iterator();
		while (it.hasNext()) {
			String paramName = (String) it.next();
			String value = (String) params.get(paramName);
			xtran.setParameter(paramName, value);
			// System.out.println("key:" + paramName);
			// System.out.println("value:" + value);
		}

		String output = xtran.transform(input);
		XSLTPool.returnObject(getXSLTPath(), xtran);
		return output;
	}

	public Entity matchEntity(ContextObjectContainer co) throws Exception {
		List l = new ArrayList();
		MatchMakerCo maker = new MatchMakerCo();
		maker.defList(l);
		maker.match(co);
		l = maker.getResource();
		Entity e = (Entity) l.get(0);
		return e;
	}

	@Override
	public Resource serve(ContextObjectContainer co) throws Exception {

		setEntity(co);
		// Optimization for the marcxml
		Entity e;
		if (type.equals("meta")) {
			e = getMarcXML();
		} else {
			co.getServiceType().setProperty("type", type);
			e = matchEntity(co);
		}

		String xml = new String(e.getContent());
		String output = transform(xml);
		Resource result = new Resource();

		result.setContentType(getMimeType());
		result.setBytes(output.getBytes("UTF-8"));
		return result;

	}

	public URI getServiceID() throws URISyntaxException {
		// TODO Auto-generated method stub
		return null;
	}

}