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

import gov.lanl.disseminator.matchmaker.MatchMakerCo;
import gov.lanl.disseminator.model.ContextObjectContainer;
import gov.lanl.disseminator.model.Entity;
import gov.lanl.util.resource.Resource;
import gov.lanl.util.xml.XmlUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.apache.log4j.Logger;


public class SplashPageX {
	private static Logger logger = Logger
			.getLogger(SplashPageX.class.getName());
	static Resource r;
	static String res_url = null;
	static String curdate; //union didl created and service added
	static String diddate; //didle created
	static String resmapurl;
	static String frm = "http://purl.lanl.gov/aDORe/schemas/2007-01/entry.xhtml"; // urlencode it
	static HashMap marcmap;
	static MakeURI mu;
	static List relatedIds;
	String baseurl;
	boolean isOpenURL;

	String jtitle;
	String publisher;
	String volume;
	String issue;
	String laur;
	String abstract_;
	String publisher_addr;
	String title;
	String issn1;
	String mdate;
	String id;
	String sfxbaseurl;
	String profile;

	/*
	
	 public static String getAtomXML(ContextObjectContainer co) throws Exception{
	
	 List l = new ArrayList();
	 MatchMakerCo maker = new MatchMakerCo();
	 maker.defList(l);
	 maker.match(co);
	 l = maker.getResource();
	
	 // frm = java.net.URLEncoder.encode(frm, "UTF8");
	 Entity e=(Entity) l.get(0);
	 r = new Resource();
	 r.setContentType("application/xml");
	 r.setBytes(e.getContent());
	
	 Entity referent = co.getReferentMeanIt();
	 //res_url = co.getResolver().getProperty("res_id");
	 //System.out.println("res_url:"+res_url);
	 String xml="";
	 AdoreObtain o = new AdoreObtain();
	 ResourceManager rm = o.getResourceManager();
	 res_url = rm.getServiceResolverUrl();
	
	 Resource didl = rm.getResource(referent.getProperty(Entity.IDENTIFIER_ATT), "xmltape");
	
	 HashMap map = DidlXmlParser.processContent(didl.getBytes());
	
	 xml = toSplash(referent,co, map);
	 return xml;
	 }
	
	 */

	public void setFlag(boolean b) {
		this.isOpenURL = b;
	}

	public void setSFX(String sfxbaseurl) {
		this.sfxbaseurl = sfxbaseurl;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public void setBaseURI(String base) {
		baseurl = base;
		res_url = base;
	}

	public void setMarcXML(HashMap map) {
		marcmap = map;
	}

	public void setReferentbyId(ContextObjectContainer co) throws Exception {
		// main referent didl 
		Entity didentity = co.getReferentMeanIt();
		diddate = didentity.getProperty("didCreated");
		System.out.println("diddate:" + diddate);

		String didId = didentity.getProperty(Entity.IDENTIFIER_ATT);
		String id = didentity.getProperty("rft_id");
		Entity entity = didentity.searchWithin(Entity.IDENTIFIER_ATT, id);
		co.setReferent(entity);
	}

	public void findItems(ContextObjectContainer co) throws Exception {
		// do not know yet
		MultiMap mhm = new MultiHashMap();
		MatchMakerCo maker = new MatchMakerCo();
		//maker.defList(l);
		maker.defMap(mhm);
		maker.match(co);
		mhm = (MultiHashMap) maker.getMap();
		//l = maker.getResource();
		Collection coll = (Collection) mhm.get("item");

		List ents = new ArrayList(coll);
		relatedIds = new ArrayList();
		if (ents.size() > 0) {
			for (int i = 0; i < ents.size(); i++) {
				Entity item = (Entity) ents.get(i);
				String itemid = item.getProperty("identifier");
				relatedIds.add(itemid);
			}
		}

		//didnot make co back
	}

	public String getAtomXML(ContextObjectContainer co) throws Exception {
		// Entity marcXML = getMetaData(co);

		setReferentbyId(co);
		//  findItems(co);

		//Entity e=(Entity) l.get(0);
		//r = new Resource();
		//r.setContentType("application/xml");
		//r.setBytes(e.getContent());
		//marcmap = MarcXmlParser.processContent(r.getBytes());	

		String xml = toSplash(co);
		return xml;
	}

	public static String SetValue(String name) {
		ArrayList list = (ArrayList) marcmap.get(name);
		String title = "";
		if (list.size() > 0) {
			title = (String) list.get(0);

		}
		return title;
	}

	public static String SetISSN() {
		ArrayList issn = (ArrayList) marcmap.get("issn");
		String issn0 = "";
		String issn1 = "";
		if (issn.size() > 0) {
			issn0 = (String) issn.get(0);
			issn1 = issn0;
			int b = issn0.indexOf("(");
			if (b > 0) {
				issn1 = issn0.substring(0, b);
			}

		}
		return issn1;
	}

	public static String SetDate() {
		ArrayList dlist = (ArrayList) marcmap.get("date");

		String mdate = "";
		if (dlist.size() > 0) {
			mdate = (String) dlist.get(0);
			//System.out.println("date"+ mdate);
			if (mdate.length() >= 12) {
				mdate = mdate.substring(7, 11);
			}
		}
		return mdate;
	}

	public String makeSFX(String fpage, String pages, String aulast) {
		StringBuffer s = new StringBuffer();
		// s.append("http://linkseeker.lanl.gov/lanl?");
		s.append(sfxbaseurl + "?");
		s.append("url_ver=Z39.88-2004");
		s.append("&rft_val_fmt=info%3Aofi%2Ffmt%3Akev%3Amtx%3Ajournal");
		s.append("&rft_id=" + java.net.URLEncoder.encode(id)); // this is not correct in general need to change after demo
		if (issn1.length() > 0) {
			s.append("&rft.issn=" + java.net.URLEncoder.encode(issn1));
		}
		s.append("&rft.aulast=" + java.net.URLEncoder.encode(aulast));
		if (fpage.length() > 0) {
			s.append("&rft.spage=" + java.net.URLEncoder.encode(fpage));
		}
		s.append("&rft.pages=" + java.net.URLEncoder.encode(pages));
		s.append("&rft.year=" + java.net.URLEncoder.encode(mdate));
		s.append("&rft.atitle=" + java.net.URLEncoder.encode(jtitle));
		s.append("&rft.title=" + java.net.URLEncoder.encode(title));
		if (volume.length() > 0) {
			s.append("&rft.volume=" + java.net.URLEncoder.encode(volume));
		}
		if (issue.length() > 0) {
			s.append("&rft.issue=" + java.net.URLEncoder.encode(issue));
		}
		s.append("&svc_val_fmt="
				+ java.net.URLEncoder.encode("info:ofi/fmt:xml:xsd:sch_svc"));

		return s.toString();
	}

	public String toLinkSeekerPage(ContextObjectContainer co) throws Exception {
		//setReferentbyId(co);
		id = co.getReferent().getProperty(Entity.IDENTIFIER_ATT);
		ArrayList creator = (ArrayList) marcmap.get("creator");

		//ArrayList subjects =  (ArrayList) marcmap.get("subject");
		//ArrayList affs =  (ArrayList) marcmap.get("affiliation_name");
		jtitle = SetValue("group_title");
		publisher = SetValue("publisher");
		volume = SetValue("journal_volume");
		issue = SetValue("journal_issue");
		issn1 = SetISSN();
		mdate = SetDate();
		title = SetValue("title");
		ArrayList glist = (ArrayList) marcmap.get("pages");
		String pages = "";
		String fpage = "";
		if (glist.size() > 0) {
			pages = (String) glist.get(0);
			int a = pages.indexOf("-");
			fpage = pages;
			if (a > 0) {
				fpage = pages.substring(0, a);
			}

		}

		int n = 0;
		String aulast = "";
		for (Iterator k = creator.iterator(); k.hasNext();) {
			String value = (String) k.next();
			n = n + 1;

			String aufirst = aulast;
			//random logic
			if (n == 1) {
				int jj = value.indexOf(",");
				if (jj > 0) {
					aulast = value.substring(0, jj);
					aufirst = value.substring(jj + 1);
				}
			}
		}

		String sfxurl = makeSFX(fpage, pages, aulast);
		return sfxurl;
	}

	/**
	 * 
	 * @param entity
	 * @return
	 * @throws RepositoryException 
	 */
	public String toSplash(ContextObjectContainer co) throws Exception {
		//   System.out.println("here ++");
		Entity referent = co.getReferentMeanIt();
		referent.setProperty("currid", referent
				.getProperty(Entity.IDENTIFIER_ATT));
		//curdate =  diddate;

		//marcmap = MarcXmlParser.processContent(r.getBytes());

		ArrayList creator = (ArrayList) marcmap.get("creator");

		ArrayList subjects = (ArrayList) marcmap.get("subject");
		ArrayList affs = (ArrayList) marcmap.get("affiliation_name");
		jtitle = XmlUtil.encode(SetValue("group_title"));
		publisher = SetValue("publisher");
		volume = SetValue("journal_volume");
		issue = SetValue("journal_issue");
		laur = SetValue("Laur");
		abstract_ = SetValue("abstract");
		// abstract_= abstract_.replaceAll("&times;","&#215;");

		System.out.println("before" + abstract_);
		abstract_ = XmlUtil.encode(abstract_);
		abstract_ = abstract_.replaceAll("&amp;times;", "&#215;");
		abstract_ = abstract_.replaceAll("&amp;deg;", "&#176;");
		publisher_addr = SetValue("publisher_addr");
		title = XmlUtil.encode(SetValue("title"));
		issn1 = SetISSN();
		mdate = SetDate();
		// not correct in general top item needed
		id = referent.getProperty(Entity.IDENTIFIER_ATT);

		ArrayList glist = (ArrayList) marcmap.get("pages");
		String pages = "";
		String fpage = "";
		if (glist.size() > 0) {
			pages = (String) glist.get(0);
			int a = pages.indexOf("-");
			fpage = pages;
			if (a > 0) {
				fpage = pages.substring(0, a);
			}

		}
		System.out.println("pages" + pages);

		StringBuilder builderbody = new StringBuilder();
		List<String> srvList = new ArrayList<String>();

		mu = new MakeURI();

		String rem = mu.makeUR(isOpenURL, referent
				.getProperty(Entity.IDENTIFIER_ATT), res_url);
		String permalink = mu.makeAR(isOpenURL, referent
				.getProperty(Entity.IDENTIFIER_ATT), "info:lanl-repo/svc/view",
				res_url);

		StringBuilder hb = new StringBuilder();
		hb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		hb.append("<info>\n");
		hb.append("<title>");
		hb.append(title);
		hb.append("</title>\n");
		hb.append("<dlink>" + XmlUtil.encode(rem) + "</dlink>\n");
		hb.append("<plink>" + XmlUtil.encode(permalink) + "</plink>\n");
		hb.append("<pdisplay>"
				+ XmlUtil.encode(mu.makeAR_forBrowser(isOpenURL, referent
						.getProperty(Entity.IDENTIFIER_ATT),
						"info:lanl-repo/svc/view", res_url)) + "</pdisplay>");

		hb.append("<authors>");

		int n = 0;
		String aulast = "";
		for (Iterator k = creator.iterator(); k.hasNext();) {
			String value = (String) k.next();
			n = n + 1;
			if (k != null) {
				hb.append("<author>\n");
				hb.append(value);
				hb.append("</author>\n");
			}
			String aufirst = aulast;
			//random logic
			if (n == 1) {
				int jj = value.indexOf(",");
				if (jj > 0) {
					aulast = value.substring(0, jj);
					aufirst = value.substring(jj + 1);
				}
			}
		}

		hb.append("</authors>\n");
		hb.append("<citation>\n");

		for (Iterator k = creator.iterator(); k.hasNext();) {
			String value = (String) k.next();
			n = n + 1;
			if (k != null) {
				hb.append(value + " ");
			}

		}
		hb.append("(" + mdate + ");");

		if (jtitle.length() > 0) {
			hb.append("In " + jtitle + ";");
		}
		if (volume.length() > 0) {
			hb.append("Vol." + volume + ";");
		}
		if (issue.length() > 0) {
			hb.append("iss." + issue + ";");
		}
		if (pages.length() > 0) {
			hb.append("p." + pages + ";");
		}
		hb.append("</citation>\n");
		if (profile.equals("INTERNAL") || profile.equals("PUBLIC")) {
			if (abstract_.length() > 0) {
				hb.append("<abstract>" + abstract_ + "</abstract>");
			}
		}

		hb.append("<resources>\n");

		if (profile.equals("INTERNAL") || profile.equals("PUBLIC")) {
			String sfxurl = makeSFX(fpage, pages, aulast);
			hb.append("<resource>\n");
			hb.append("<id>sfx</id>\n");
			hb.append("<name>LinkSeeker</name>\n");
			hb.append("<link>" + XmlUtil.encode(sfxurl) + "</link>\n");
			hb.append("<display>"
					+ XmlUtil.encode(mu.makeAR_forBrowser(isOpenURL, referent
							.getProperty(Entity.IDENTIFIER_ATT),
							"info:lanl-repo/svc/linkseeker", res_url))
					+ "</display>");
			hb.append("</resource>\n");
		}
		append(builderbody, referent, referent, srvList);
		//curdate already modified

		hb.append(builderbody.toString());
		hb.append("</resources>\n");
		hb.append("</info>\n");

		System.out.println(hb.toString());
		return hb.toString();
	}

	private void append(StringBuilder builder, Entity referent, Entity e,
			List<String> srvList) {
		for (Entity subent : e.getEntities()) {

			appendEntry(builder, referent, subent, srvList);
			append(builder, referent, subent, srvList);
		}
	}

	private void appendEntry(StringBuilder builder, Entity referent,
			Entity entity, List<String> srvList) {
		for (String service : entity.getServices()) {

			System.out.println("service" + service);
			if (!srvList.contains(service)) {
				srvList.add(service);
				HashMap params = entity.getParams(service);
				String cdate = (String) params.get("dateCreated");
				String title = (String) params.get("title");
				String sem = (String) params.get("semantic");

				//System.out.println("semantic"+sem);
				//Date servdate = gov.lanl.util.DateUtil.utc2Date(cdate);
				//if (servdate.after(gov.lanl.util.DateUtil.utc2Date(curdate))) {
				//  curdate = cdate;
				//}
				// nuzno ewe proverit' chto eto za id
				String id = referent.getProperty("currid");
				String locurl = mu.makeAR(isOpenURL, id, service, res_url);

				String locid = XmlUtil.encode(id + "#" + service.substring(19));

				builder.append("<resource>\n");
				builder.append("<id>" + locid + "</id>\n");
				builder.append("<name>" + title + "</name>\n");
				builder.append("<link>" + XmlUtil.encode(locurl) + "</link>\n");
				builder.append("<display>"
						+ XmlUtil.encode(mu.makeAR_forBrowser(isOpenURL, id,
								service, res_url)) + "</display>");
				builder.append("</resource>\n");
			}
		}
	}

}
