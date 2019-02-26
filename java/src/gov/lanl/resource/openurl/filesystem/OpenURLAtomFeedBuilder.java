package gov.lanl.resource.openurl.filesystem;

import java.util.Date;

import gov.lanl.util.DateUtil;
import gov.lanl.util.xml.XmlUtil;

public class OpenURLAtomFeedBuilder {
	private String repoBaseUrl;
	private String pidResolver;
	private StringBuffer atom;
	private StringBuffer items;
	private int index = 0;
	private int perPage = 0;
	private String creationDate;
	public static final String XMLHEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	public static final String ATOMHEADER = "<feed xmlns=\"http://www.w3.org/2005/Atom\" xmlns:opensearch=\"http://a9.com/-/spec/opensearchrss/1.0/\" xml:base=\"http://www.lanl.gov\">";
	
	public OpenURLAtomFeedBuilder(String baseUrl, String pidResolver) {
		this.repoBaseUrl = baseUrl;
		this.pidResolver = pidResolver;
		this.creationDate = DateUtil.date2UTC(new Date(System.currentTimeMillis()));
		atom = new StringBuffer();
		items = new StringBuffer();
	}
	
	private void setFeedProperties() {
		String base = repoBaseUrl + "/index";
		String id = base;
		if (index > 0)
			id += "?offset=" + index;
		if (perPage > 0)
			id += "&amp;perPage=" + perPage;
		atom.append(XMLHEADER);
		atom.append(ATOMHEADER);
		atom.append("<id>" + id + "</id>");
		atom.append("<updated>" + creationDate + "</updated>");
		atom.append("<title type=\"text\">aDORe Repository Index</title>");
		atom.append("<generator version=\"1.0\" uri=\"http://african.lanl.gov\">aDORe Repository</generator>");
		atom.append("<author><name>aDORe Repository</name></author>");
		if (index > 0)
		    atom.append("<opensearch:startIndex>" + index + "</opensearch:startIndex>");
		if (perPage > 0)
		    atom.append("<opensearch:itemsPerPage>" + perPage + "</opensearch:itemsPerPage>");
		atom.append("<link rel=\"self\" href=\"" + id + "\" type=\"application/atom+xml\"/>");
	    atom.append("<link rel=\"first\" href=\"" + base + "&amp;index=" + index + "&amp;perPage=" + perPage + "\" type=\"application/atom+xml\"/>");
	    atom.append("<link rel=\"previous\" href=\"" + base + "&amp;index=" + (index - perPage) + "&amp;perPage=" + perPage + "\" type=\"application/atom+xml\"/>");
	    atom.append("<link rel=\"next\" href=\"" + base + "&amp;index=" + (index + perPage) +  "&amp;perPage=" + perPage + "\" type=\"application/atom+xml\"/>");
	}
	
	public void addEntry(String id) {
		String url = XmlUtil.encode(repoBaseUrl + "/openurl-aDORe4?url_ver=Z39.88-2004&amp;rft_id=" + id + "&amp;svc_id=info:lanl-repo/svc/getDatastream");
		items.append("<entry>");
		items.append("  <id>" + pidResolver + id.substring(id.lastIndexOf("/") + 1) + "</id>");
		items.append("  <updated>" + creationDate + "</updated>");
		items.append("  <author>");
		items.append("    <name>aDORe Repository</name>");
		items.append("  </author>");
		items.append("  <title type=\"text\">" + id + "</title>");
		items.append("  <link rel=\"alternate\" type=\"image/jp2\" href=\"" + url + "\"/>");
		items.append("  <rights>Mozilla</rights>");
		items.append("</entry>");
	}
	
	public String getFeed() {
		setFeedProperties();
		atom.append(items.toString());
		atom.append("</feed>");
		return atom.toString();
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}
}
