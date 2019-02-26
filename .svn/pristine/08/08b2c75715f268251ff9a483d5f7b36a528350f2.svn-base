package gov.lanl.disseminator.service.toc;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.apache.log4j.Logger;

import gov.lanl.adore.helper.ResourceManager;
import gov.lanl.disseminator.matchmaker.MatchMakerCo;
import gov.lanl.disseminator.model.ContextObjectContainer;
import gov.lanl.disseminator.model.Entity;
import gov.lanl.util.xml.XmlUtil;
import gov.lanl.util.xpath.marcxml.MarcXmlParser;
import gov.lanl.util.resource.Resource;

public class REMCreator {
	private static Logger logger = Logger.getLogger(REMCreator.class.getName());
	static Resource r;
	static String res_url = null;
	static String curdate; //union didl created and service added
	static String diddate; //didle created
	static String resmapurl;
	static String generator;
	static String generatorURI;
	static String baseurl;
	static boolean isOpenURL;
	//static List ents;
	static List relatedIds;
	boolean toplevel = true;

	static String AGGR_CATEGORY = "<category scheme=\"http://www.openarchives.org/ore/terms/\"  "
			+ "term =\"http://www.openarchives.org/ore/terms/Aggregation\"  "
			+ "label=\"Aggregation\" />\n";
	static String HEADER = "<entry xmlns=\"http://www.w3.org/2005/Atom\"  "
			+ "  xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"  "
			+ "  xmlns:xs=\"http://www.w3.org/2001/XMLSchema\""
			+ "  xmlns:dc=\"http://purl.org/dc/\"  "
			+ "  xmlns:dcterms=\"http://purl.org/dc/terms/\""
			+ "  xmlns:ore=\"http://www.openarchives.org/ore/terms/\" "
			+ "  xmlns:oreatom=\"http://www.openarchives.org/ore/atom/\" "
			+ "  xmlns:grddl=\"http://www.w3.org/2003/g/data-view#\" "
			+ "  grddl:transformation=\"http://www.openarchives.org/ore/atom/atom-grddl.xsl\" >";
	static MakeURI mu = new MakeURI();
	static HashMap marcmap;
	ContextObjectContainer co;
	// REM only applicable to  the item level, so it is different from TOC service 
	StringBuilder proxybuilder = new StringBuilder();
	String urA;

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

		setReferentbyId(co);
		findItems(co);

		String xml = toOreAtom(co);
		return xml;
	}

	public void setGenerator(String generatorURI, String generator) {
		this.generatorURI = generatorURI;
		this.generator = generator;
	}

	public void setFlag(boolean b) {
		this.isOpenURL = b;
	}

	public void setBaseURI(String base) {
		baseurl = base;
		res_url = base;
	}

	public void setMarcXML(HashMap map) {
		marcmap = map;
	}

	/**
	 * 
	 * @param entity
	 * @return
	 * @throws RepositoryException 
	 */
	public String toOreAtom(ContextObjectContainer co) throws Exception {

		ArrayList dlist = (ArrayList) marcmap.get("date");

		String mdate = "";
		if (dlist.size() > 0) {
			mdate = (String) dlist.get(0);
			//System.out.println("date"+ mdate);
			if (mdate.length() >= 12) {
				mdate = mdate.substring(7, 11);
			}
		}

		// System.out.println ("res_url:" +res_url);
		//  MakeURI mu =new MakeURI();
		Entity referent = co.getReferentMeanIt();
		referent.setProperty("currid", referent
				.getProperty(Entity.IDENTIFIER_ATT));
		// String date = referent.getProperty("didCreated");

		curdate = diddate;
		//diddate = date;
		proxybuilder.append("<oreatom:triples>");
		StringBuilder builderbody = new StringBuilder();
		List<String> srvList = new ArrayList<String>();

		String location = mu.makeUR(isOpenURL, referent
				.getProperty(Entity.IDENTIFIER_ATT), res_url);
		urA = mu.makeURA(isOpenURL,
				referent.getProperty(Entity.IDENTIFIER_ATT), res_url);
		String urdf = mu.makeAR(isOpenURL, referent
				.getProperty(Entity.IDENTIFIER_ATT),
				"info:lanl-repo/svc/rem.rdf", res_url);
		String permalink = mu.makeAR(isOpenURL, referent
				.getProperty(Entity.IDENTIFIER_ATT), "info:lanl-repo/svc/view",
				res_url);
		append(builderbody, referent, referent, srvList, "");
		//location = XmlUtil.encode(location);
		resmapurl = location;
		proxybuilder.append("</oreatom:triples>");
		StringBuilder builder = new StringBuilder();
		builder.append(HEADER);

		StringBuilder builder0 = new StringBuilder();

		ArrayList tlist = (ArrayList) marcmap.get("title");
		String title = (String) tlist.get(0);
		//		authors
		ArrayList creator = (ArrayList) marcmap.get("creator");

		builder0.append("<title type=\"text\">" + title + "</title>\n");
		for (Iterator k = creator.iterator(); k.hasNext();) {
			String value = (String) k.next();
			if (k != null) {
				builder0
						.append("<author><name>" + value + "</name></author>\n");
			}
		}

		//builder0.append("<generator uri=\"" + generatorURI +"\">"+generator +"</generator>\n");
		builder0.append("<created>" + diddate + "</created>\n");
		if (!diddate.equals(curdate)) {
			builder0.append("<updated>" + curdate + "</updated>\n");
		}
		builder0.append(AGGR_CATEGORY);
		builder0
				.append("<category term=\""
						+ mdate
						+ "\"  scheme=\"http://www.openarchives.org/ore/atom/created\"/>");
		builder0.append("<id>").append(
				XmlUtil.encode("tag:library.lanl.gov," + mdate + ":"
						+ referent.getProperty(Entity.IDENTIFIER_ATT)));
		builder0.append("</id>\n");

		builder0
				.append("<link rel=\"self\" type=\"application/atom+xml\" href =\""
						+ XmlUtil.encode(resmapurl) + "\"/>\n");
		//builder0.append("<link rel=\"ore:isDesribedBy\" type=\"application/rdf+xml\" href =\"" + resmapurl.replace("atom", "rdfxml") + "\"/>\n" );
		builder0
				.append("<link rel=\"http://www.openarchives.org/ore/terms/describes\" href=\""
						+ XmlUtil.encode(urA) + "\"/>\n");
		//builder0.append("<link rel=\"related\" href=\""+ urdf +"\"/>\n");
		builder0.append("<link href=\"" + XmlUtil.encode(permalink)
				+ "\" rel=\"alternate\"/> ");
		builder0
				.append("<link rel=\"http://www.openarchives.org/ore/terms/isDescribedBy\" href=\""
						+ XmlUtil.encode(urdf) + "\"/>");

		if (relatedIds.size() > 0) {
			for (int i = 0; i < relatedIds.size(); i++) {
				String itemid = (String) relatedIds.get(i);
				builder0
						.append(" <link rel=\"http://www.openarchives.org/ore/terms/similarTo\"      href=\""
								+ itemid + "\" />\n");
			}
		}

		builder.append(builder0.toString());
		//builder.append("<link rel=\"self\" type=\"application/atom+xml\" href =\"" + resmapurl + "\"/>" );
		builder.append(builderbody.toString());
		builder.append(proxybuilder.toString());
		builder.append("</entry>");
		//System.out.println(builder.toString());
		return builder.toString();
	}

	private void append(StringBuilder builder, Entity referent, Entity e,
			List<String> srvList, String source) {
		for (Entity subent : e.getEntities()) {

			appendEntry(builder, referent, subent, srvList, source);
			append(builder, referent, subent, srvList, source);
		}
	}

	private void appendEntry(StringBuilder builder, Entity referent,
			Entity entity, List<String> srvList, String source) {
		for (String service : entity.getServices()) {

			//System.out.println("service"+ service);
			if (!srvList.contains(service)) {
				srvList.add(service);
				HashMap params = entity.getParams(service);
				String cdate = (String) params.get("dateCreated");
				String title = (String) params.get("title");
				String sem = (String) params.get("semantic");
				String mimetype = null;
				mimetype = (String) params.get("mimetype");

				//System.out.println("semantic"+sem);
				Date servdate = gov.lanl.util.DateUtil.utc2Date(cdate);

				if (curdate != null
						&& servdate.after(gov.lanl.util.DateUtil
								.utc2Date(curdate))) {
					curdate = cdate;
				} else {
					curdate = gov.lanl.util.DateUtil.date2UTC(servdate);
				}

				if (diddate != null
						&& servdate.before(gov.lanl.util.DateUtil
								.utc2Date(diddate))) {
					cdate = diddate;
				} else {
					diddate = gov.lanl.util.DateUtil.date2UTC(servdate);
				}

				//builder.append("<entry><title>" + title + "</title>\n"); 

				String id = referent.getProperty("currid");

				//String locurl = res_url+ "?url_ver=Z39.88-2004&rft_id="+ id + "&svc_id=" + service;
				String locurl = mu.makeAR(isOpenURL, id, service, res_url);
				System.out.println("locurl" + locurl);
				String proxyurl = mu.makeProxy(isOpenURL, locurl, res_url);

				proxyurl = XmlUtil.encode(proxyurl);

				//builder.append("<id>").append(proxyurl).append("</id>\n");
				//builder.append("<link rel=\"alternate\"  href=\"").append(locurl).append("\" />\n");

				builder
						.append("<link rel=\"http://www.openarchives.org/ore/terms/aggregates\"");
				builder.append("  href=\"" + locurl + "\"");
				builder.append(" title=\"" + title + "\"");
				if (mimetype != null) {
					builder.append(" type=\"" + mimetype + "\"");
				}
				builder.append("/>\n");

				proxybuilder.append("<rdf:Description  ");
				proxybuilder.append(" rdf:about=\"" + proxyurl + "\">");

				proxybuilder.append("<ore:proxyFor rdf:resource=\""
						+ XmlUtil.encode(locurl) + "\"/>");
				proxybuilder.append("<ore:proxyIn rdf:resource=\""
						+ XmlUtil.encode(urA) + "\"/>");
				proxybuilder.append("</rdf:Description>");
				//builder.append("<updated>"+cdate+"</updated>\n");
				//builder.append("</entry>");
			}
		}
	}

}
