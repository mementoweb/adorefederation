package gov.lanl.resource.openurl.filesystem;

import java.sql.Date;

import gov.lanl.util.DateUtil;
import gov.lanl.util.xml.XmlUtil;

public class OpenURLJSONFeedBuilder {
	private String baseUrl;
	private StringBuffer json;
	private StringBuffer items;
	private String creationDate;
	public static final String HEADER = "jsonDjatokaFeed({";		
	
	public OpenURLJSONFeedBuilder(String baseUrl) {
		this.baseUrl = baseUrl;
		this.creationDate = DateUtil.date2UTC(new Date(System.currentTimeMillis()));
		json = new StringBuffer();
		json.append(HEADER);
		addHeaderProperty("link", baseUrl);
		addHeaderProperty("created", creationDate);
		items = new StringBuffer();
		items.append("items\": [");
		
	}
	
	private void addHeaderProperty(String key, String value) {
		json.append("\" + key + \": " + "\"" + value + "\",\n");
	}
	
	private void addItem(String id) {
		String url = XmlUtil.encode(baseUrl + "/openurl-aDORe4?url_ver=Z39.88-2004&rft_id=" + id + "&svc_id=info:lanl-repo/svc/getDatastream");
		String desc = "\n<rdf:Description rdf:about=\"" + url + "\">" +
		    "    <dc:identifier>" + id + "</dc:identifier>" +
		    "</rdf:Description>";
		items.append(desc);
	}
	
	
}
