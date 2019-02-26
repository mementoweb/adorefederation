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

package gov.lanl.disseminator.service;

import gov.lanl.adore.helper.IdNotFoundException;
import gov.lanl.adore.helper.ResourceManager;
import gov.lanl.adore.helper.ResourceManagerException;
import gov.lanl.disseminator.adore.didl2model.DIDL2Entity;
import gov.lanl.disseminator.matchmaker.MatchMakerCo;
import gov.lanl.disseminator.model.ContextObjectContainer;
import gov.lanl.disseminator.model.Entity;
import gov.lanl.disseminator.service.toc.MakeURI;
import gov.lanl.disseminator.service.toc.SplashPageX;
import gov.lanl.util.HttpDate;
import gov.lanl.util.properties.GenericPropertyManager;
import gov.lanl.util.properties.PropertiesUtil;
import gov.lanl.util.resource.Resource;
import gov.lanl.util.xpath.marcxml.MarcXmlAbbrevProperties;
import gov.lanl.util.xpath.marcxml.MarcXmlParser;
import gov.lanl.util.xslt.XSLTPool;
import gov.lanl.util.xslt.XSLTTransformer;
import info.openurl.oom.ContextObject;
import info.openurl.oom.OpenURLException;
import info.openurl.oom.OpenURLRequest;
import info.openurl.oom.OpenURLRequestProcessor;
import info.openurl.oom.OpenURLResponse;
import info.openurl.oom.Service;
import info.openurl.oom.config.OpenURLConfig;
import info.openurl.oom.descriptors.ByValueMetadataKev;
import info.openurl.oom.entities.ServiceType;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;

import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.apache.log4j.Logger;
import org.drools.RuleBase;
import org.drools.StatefulSession;


public abstract class AbstractService implements Service {

	// public abstract URI getServiceID() throws URISyntaxException;

	private static ResourceManager rm;
	private HashMap marcmap;
	Entity marcxml;
	RecommendationService recommendationService;
	public Map map;

	public AbstractService(OpenURLConfig openURLConfig,
			info.openurl.oom.config.ClassConfig classConfig)
			throws TransformerException {
		// super(openURLConfig, classConfig);
		this.map = classConfig.getArgs();

		// TODO Auto-generated constructor stub
	}

	private static Logger logger = Logger.getLogger(AbstractService.class
			.getName());
	static {
		try {
			GenericPropertyManager loader = new GenericPropertyManager();
			loader
					.addAll(PropertiesUtil
							.loadConfigByCP("etc/adore.properties"));
			rm = new ResourceManager(loader.getProperties());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.fatal(ex);
		}
	}

	public ResourceManager getResourceManager() {
		return rm;
	}

	/**
	 * process the request
	 * 
	 * @param input
	 * @param rft_id
	 * @param rft_version
	 * @param svc_args
	 * @return
	 * @throws Exception
	 */
	/*
	 * public abstract Resource serve(ServiceInput input, ContextObjectContainer
	 * co) throws Exception;
	 */

	public abstract Resource serve(ContextObjectContainer co) throws Exception;

	public Resource serveDefault(ContextObjectContainer co) throws Exception {
		co.getServiceType().setProperty("rem", "true"); // flag to collect
														// services
		SplashPageX sp = new SplashPageX();
		String res_url = "";
		Entity resolver = co.getResolver();
		if (resolver.hasProperty("res_id")) {
			res_url = resolver.getProperty("res_id");
		}
		String oflag;
		Entity service = co.getServiceType();
		if (service.hasProperty("svc.openurl")) {
			oflag = service.getProperty("svc.openurl");
			// System.out.println("oflag:"+ oflag);
		} else {
			oflag = "false";
		}

		if (oflag.equals("true")) {
			sp.setFlag(true);
		} else {
			sp.setFlag(false);
		}

		String profile = service.getProperty("recommendation");
		//System.out.println("profile:"+ profile);
                
		sp.setProfile(profile);


		String sfxbaseurl = "http://linkseeker.lanl.gov/lanl";
		if (service.hasProperty("svc.sfxbaseurl")) {
			sfxbaseurl = service.getProperty("svc.sfxbaseurl");
		}
		sp.setSFX(sfxbaseurl);
		HashMap marcXML = getMetaData(co);
		sp.setMarcXML(marcXML);
		sp.setBaseURI(res_url);
		String output = sp.getAtomXML(co);
		Resource result = new Resource();


		// String htmlout = transform(output);
		XSLTTransformer xtran = XSLTPool
				.borrowObject("/etc/xslt/disseminator/plink.xsl");
		String htmlout = xtran.transform(output);
		XSLTPool.returnObject("/etc/xslt/disseminator/plink.xsl", xtran);

		result.setBytes(htmlout.getBytes("UTF-8"));
		result.setContentType("text/html");
		// result.setBytes(output.getBytes());
		return result;
	}

	public void setEntity(ContextObjectContainer co) throws Exception {
		Entity didentity = co.getReferent();
		String id = didentity.getProperty("rft_id");
		Entity entity = didentity.searchWithin(Entity.IDENTIFIER_ATT, id);
		co.setReferent(entity);
	}

	public HashMap getMetaData(ContextObjectContainer co) throws Exception {
		// ContextObjectContainer
		List l = new ArrayList();
		MultiMap mhm = new MultiHashMap();
		co.getServiceType().setProperty("type", "meta");
		MatchMakerCo maker = new MatchMakerCo();
		// maker.defList(l);
		maker.defMap(mhm);
		try {
			maker.match(co);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// l = maker.getResource();
		mhm = (MultiHashMap) maker.getMap();
		Collection coll = (Collection) mhm.get("bib");
		// System.out.println("after size:" + l.size());
		List ents = new ArrayList(coll);
		Entity e = (Entity) ents.get(0);
		marcxml = e;
		String a = new String(e.getContent(), "UTF-8");
		//System.out.println(a);
		Resource r = new Resource();
		r.setContentType("application/xml");
		r.setBytes(e.getContent());
		// HashMap marcmap = new HashMap();
		// try {
		marcmap = MarcXmlParser.getMarcPropMap(r.getBytes(), new MarcXmlAbbrevProperties());
                //System.out.println(marcmap);
		// } catch (Exception e1) {
		// TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		co.getServiceType().setProperty("type", "none");
		// return co to initial state
		// try {
		co.setReferent(co.getReferentMeanIt());
		// } catch (Exception e1) {
		// TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		return marcmap;

	}

	public HashMap getMetaMap() {
		return marcmap;
	}

	public Entity getMarcXML() {
		return this.marcxml;
	}

	public void setPermission(ContextObjectContainer co) throws Exception {
		// date of pub and collection
		// HashMap marcmap = new HashMap();
		recommendationService = new RecommendationService();
		// try {
		getMetaData(co);
		// } catch (Exception e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// return;
		// }
		ArrayList dlist = (ArrayList) marcmap.get("date");

		String mdate = "";
		if (dlist.size() > 0) {
			mdate = (String) dlist.get(0);
			// System.out.println("date"+ mdate);
			if (mdate.length() >= 12) {
				mdate = mdate.substring(7, 11);
			}
		}

		ArrayList list = (ArrayList) marcmap.get("collection");
		String title = "";
		if (list.size() > 0) {
			title = (String) list.get(0);
		}
		ArrayList lanlPubTypes = (ArrayList) marcmap.get("lapubtype");
		String lanlPubType = "";
		if (lanlPubTypes.size() > 0) {
			lanlPubType = (String) lanlPubTypes.get(0);
                        if (lanlPubType.indexOf("[Adminmetadata : dc:accessRights]") == 0) {
                            lanlPubType = lanlPubType.substring("[Adminmetadata : dc:accessRights]".length());
                        }
                        //System.out.println("lanlPubType:"+ lanlPubType);

		}

		ArrayList subsetlist = (ArrayList) marcmap.get("subset");
		ArrayList subsetcut = new ArrayList();
		Iterator it = subsetlist.iterator();
		while (it.hasNext()) {
			String subset = (String) it.next();
			// System.out.println("subset:"+ subset);

			if (subset.indexOf("[Adminmetadata : dc:accessRights]") == 0) {
				subset = subset.substring("[Adminmetadata : dc:accessRights]"
						.length());
				// System.out.println("subsetcut:"+ subset);
			}
			subsetcut.add(subset);
		}
		//System.out.println("mdate:" + mdate);
		//System.out.println("collection:" + title);
		SecurityProfile p = new SecurityProfile();
		String secprofile = co.getServiceType().getProperty("svc.access");
		p.setName(secprofile);
		p.setCollection(title);
		p.setSubset(subsetcut);
		if (!lanlPubType.equals("")){
			p.setLanlPubType(lanlPubType);
                }

		Integer year = 0;

		try {
			year = new Integer(mdate);
		} catch (NumberFormatException e) {

			// TODO Auto-generated catch block
			// e1.printStackTrace();
		}
		p.setPubyear(year.intValue());
		MatchMakerCo maker = new MatchMakerCo();
		RuleBase ruleBase = maker.getRuleBase();
		// WorkingMemory workingMemory = (WorkingMemory)
		// ruleBase.newStatelessSession();
		// StatelessSession session = ruleBase.newStatelessSession();
		StatefulSession session = ruleBase.newStatefulSession();

		session.setGlobal("recommendationService", recommendationService);
		// session.execute(p);
		session.insert(p);
		session.insert(co);
		session.fireAllRules();
		// List recommendations = recommendationService.getRecommendations();
		// for (Iterator iterator = recommendations.iterator();
		// iterator.hasNext(); ) {

		// System.out.println("recommendation:"+iterator.next());
		// }
		session.dispose();
		// recommendationService.createRecommendation(new
		// Recommendation("FULL"));
		// recommendations.clear();

		// maker.match(co);
		// co.getServiceType().setProperty("securitytest", "false");
		// co.setReferent(co.getReferentMeanIt());
	}

	public void compose(Entity e, ByValueMetadataKev Data) throws Exception {
		//System.out.println("Go here first");
		if (Data != null && Data.getFieldMap().size() > 0) {
			// System.out.println("Go here then");
			HashMap m = (HashMap) Data.getFieldMap();
			Set entrySet = m.entrySet();
			Iterator it = entrySet.iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Entry) it.next();
				String key = (String) entry.getKey();
				String v = ((String[]) entry.getValue())[0];
				// String v= ((String[]) m.get(key))[0];
				// System.out.println("key:" + entry.getKey());
				// System.out.println("value:" + v);
				e.setProperty(key, v);
			}
		}
	}

	public void printMap(Map m) {
		Set entrySet = m.entrySet();
		Iterator it = entrySet.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Entry) it.next();
			String key = (String) entry.getKey();
			String v = ((String[]) entry.getValue())[0];
			// System.out.println("key:" + entry.getKey());
			// System.out.println("value:" + v);
		}
	}

	public Entity populate(Object[] svcs, String id_property, URI svc_id) {
		Entity srvtypes = new Entity();
		ByValueMetadataKev svcData = null;

		for (int i = 0; i < svcs.length; i++) {
			Object s = svcs[i];

			// System.out.println("s" + i);
			if (s instanceof URI) {
				svc_id = (URI) svcs[i];
			}

			if (s instanceof ByValueMetadataKev) {

				svcData = (ByValueMetadataKev) s;
				try {
					compose(srvtypes, svcData);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException();
				}
			}
		}
		if (svc_id != null) {
			srvtypes.setProperty(id_property, svc_id.toString());
		}
		return srvtypes;
	}

	public OpenURLResponse resolve(ServiceType servicetype,
			ContextObject contextObject, OpenURLRequest request,
			OpenURLRequestProcessor processor) throws OpenURLException {
		URI rft_id = null;
		// default service
		URI svc_id = URI.create("info:lanl-repo/svc/ore.atom");

		String rft_version = null;
		// String svc_args = null;

		Object[] data = contextObject.getReferent().getDescriptors();
		Entity referentbyref = populate(data, "rft_id", rft_id);

		rft_id = URI.create(referentbyref.getProperty("rft_id"));
		if (referentbyref.hasProperty("rft.version")) {
			rft_version = referentbyref.getProperty("rft.version");
		}

		Object[] svcs = contextObject.getServiceTypes()[0].getDescriptors();
		Entity srvtypes = populate(svcs, "svc_id", svc_id);

		Object[] referrers = (Object[]) contextObject.getReferrers()[0]
				.getDescriptors();
		URI rfr_id = null;

		Entity referrer = populate(referrers, "rfr_id", rfr_id);

		Object[] requesters = (Object[]) contextObject.getRequesters()[0]
				.getDescriptors();

		URI req_id = null;

		Entity requester = populate(requesters, "req_id", req_id);

		Object[] resolvers = (Object[]) contextObject.getResolvers()[0]
				.getDescriptors();

		URI res_id = null;
		Entity resolver = populate(resolvers, "res_id", res_id);

		try {
			// only processing of referent is different
			// we need to dereference and populate entity

			// ObtainInterface obtainer = ObtainFactory.getObtainInterface();
			// ServiceInput input = obtainer.obtain(rft_id.toString(), svc_id
			// .toString(), rft_version, null);
			String reqId = null;

			if (rft_version != null) {
				reqId = rft_version;
			} else {
				reqId = rft_id.toString();
			}

			if (reqId.contains("/ds/")) {
				try {
				Resource res = rm.getResource(reqId, "arc");
					HashMap<String, String> header_map = new HashMap<String, String>();
					header_map.put("Content-Length", res.getBytes().length + "");
					header_map.put("Date", HttpDate.getHttpDate());
					return new OpenURLResponse(HttpServletResponse.SC_OK, res
							.getContentType(), res.getBytes(), null, header_map);
				} catch (ResourceManagerException e) {
					// Datastream does not exist in an ARCfile repository
				}
			}
				
			Resource didl = rm.getResource(reqId, "xmltape");

			DIDL2Entity transformer = new DIDL2Entity(new String(didl
					.getBytes(), "UTF-8"));
			Entity referent = transformer.getEntity();
			referent.setContent(didl.getBytes());
			// System.out.println("didll"+new String(didl.getBytes(),"UTF-8"));

			ContextObjectContainer co = new ContextObjectContainer();

			referent.setProperty("rft_id", rft_id.toString());

			co.setReferrer(referrer);
			co.setReferent(referent);
			co.setServiceType(srvtypes);
			co.setRequester(requester);
			co.setResolver(resolver);

			setPermission(co);
			// just quick take -- need to be rewrite
			Entity srvt = co.getServiceType();
			if (recommendationService.getRecommendations().size() > 0) {
				List recommendations = recommendationService
						.getRecommendations();
				for (Iterator iterator = recommendations.iterator(); iterator
						.hasNext();) {
					Recommendation rec = (Recommendation) iterator.next();
					srvt.setProperty("recommendation", rec.getRecommendation());
					// System.out.println(rec.getRecommendation());
				}
			} else {
				srvt.setProperty("recommendation", "no");
			}

			//System.out.println("rec:" +srvt.getProperty("recommendation"));
			// co.setServiceType(srvt);
			Resource result;
			String expose = "INTERNAL";
			if (map.containsKey("expose")) {
				expose = (String) map.get("expose");
			}
			//System.out.println("expose:" +expose);

			if (srvt.getProperty("recommendation").equals("no")) {
				result = serveDefault(co);
			} else {
				if (srvt.getProperty("recommendation").equals("INTERNAL")) {
					result = serve(co);
				} else if ((srvt.getProperty("recommendation").equals("PUBLIC") || srvt.getProperty("recommendation").equals("OSTI"))
						&& expose.equals("public")) {
					{
						result = serve(co);
					}
				} else {
					result = serveDefault(co);
				}
			}

                //MOD - fk - 121408
                if (srvt.getProperty("recommendation").equals("NOACCESS")) {
                    return new OpenURLResponse(HttpServletResponse.SC_OK, "text/plain", "not resolvable identifier".getBytes());
                  //  return new OpenURLResponse(HttpServletResponse.SC_OK, new String(result.getBytes(), "UTF-8"));
                }

			HashMap header_map = new HashMap();
			MakeURI mu = new MakeURI();

			// String location = "?url_ver=Z39.88-2004&rft_id="
			// + referent.getProperty(Entity.IDENTIFIER_ATT) + "&svc_id="
			// + "info:lanl-repo/svc/ore.atom>; type=\"application/atom+xml\";
			// rel=\"resourcemap\"";
			// AdoreObtain o = new AdoreObtain();
			// ResourceManager rm = o.getResourceManager();
			String res_url = rm.getServiceResolverUrl();

			if (resolver.hasProperty("res_id")) {
				res_url = resolver.getProperty("res_id");
				// System.out.println("res_url:" + res_url);
			}

			// default permalink style rem
			String urlr = mu.makeURA(false, referent.getProperty("rft_id"),
					res_url);
			String remheader = "<" + urlr + ">;rel=\"aggregation\"";
			header_map.put("Link", remheader);
			if (co.getServiceType().getProperty("svc_id").equals(
					"info:lanl-repo/svc/aggregation")) {
				String urlrem = mu.makeUR(false,
						referent.getProperty("rft_id"), res_url);

				// String accept =
				// co.getServiceType().getProperty("svc.accept");
				// if
				// (co.getServiceType().getProperty("svc.accept").indexOf("application/atom+xml")>0)
				// {}
				// { urlrem = mu.makeUR(false,
				// referent.getProperty("rft_id"),res_url); }
				// if
				// (co.getServiceType().getProperty("svc.accept").indexOf("application/rdf+xml")>0)
				// {}
				// { urlrem = mu.makeAR(false,
				// referent.getProperty("rft_id"),"info:lanl-repo/svc/rem.rdf",
				// res_url);}

				return new OpenURLResponse(HttpServletResponse.SC_SEE_OTHER,
						new String(urlrem.getBytes(), "UTF-8"));

			} else {
				if (result.getContentType().equals("redirectURL")) {
					// System.out.println("try to redirect");
					// System.out.println(new String(result.getBytes()));
					return new OpenURLResponse(
							HttpServletResponse.SC_MOVED_TEMPORARILY,
							new String(result.getBytes(), "UTF-8"));
				} else {
					return new OpenURLResponse(HttpServletResponse.SC_OK,
							result.getContentType(), result.getBytes(), null,
							header_map);
				}
			}
		} catch (ResourceManagerException ex) {
			if (ex.getCause() instanceof gov.lanl.locator.IdLocatorException)
				return new OpenURLResponse(HttpServletResponse.SC_NOT_FOUND,
						"text/plain", "not resolvable identifier".getBytes());
			else
				throw new OpenURLException("openurl exception", ex);
           
		} catch (IdNotFoundException ex) {
			return new OpenURLResponse(HttpServletResponse.SC_NOT_FOUND,
					"text/plain", "not resolvable identifier".getBytes());

		} catch (Exception ex) {
			throw new OpenURLException("openurl exception", ex);
		}
	}
}