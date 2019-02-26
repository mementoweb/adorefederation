package gov.lanl.adore.solr;

import java.util.List;

public class Article {
	private String contentId;
	private String contentHash;
	private String dataset;
	private String jtitle;
	private String atitle;
	private int date;
	private String language;
	private String artContext;
	private int volume;
	private int issue;
	private int spage;
	private int epage;
	private String issn;
	private List<Author> authors; 
	private List<String> abstracts; 
	private List<String> subjects;
	
	public final String getContentId() {
		return contentId;
	}
	public final void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public final String getContentHash() {
		return contentHash;
	}
	public final void setContentHash(String contentHash) {
		this.contentHash = contentHash;
	}
	public final String getJtitle() {
		return jtitle;
	}
	public final void setJtitle(String jtitle) {
		this.jtitle = jtitle;
	}
	public final String getAtitle() {
		return atitle;
	}
	public final void setAtitle(String atitle) {
		this.atitle = atitle;
	}
	public int getDate() {
		return date;
	}
	public final void setDate(int date) {
		this.date = date;
	}
	public final String getLanguage() {
		return language;
	}
	public final void setLanguage(String language) {
		this.language = language;
	}
	public final String getArtContext() {
		return artContext;
	}
	public final void setArtContext(String artContext) {
		this.artContext = artContext;
	}
	public final int getVolume() {
		return volume;
	}
	public final void setVolume(int volume) {
		this.volume = volume;
	}
	public final int getIssue() {
		return issue;
	}
	public final void setIssue(int issue) {
		this.issue = issue;
	}
	public final int getSpage() {
		return spage;
	}
	public final void setSpage(int spage) {
		this.spage = spage;
	}
	public final int getEpage() {
		return epage;
	}
	public final void setEpage(int epage) {
		this.epage = epage;
	}
	public final String getIssn() {
		return issn;
	}
	public final void setIssn(String issn) {
		this.issn = issn;
	}
	public List<Author> getAuthors() {
		return authors;
	}
	public final void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	public final List<String> getAbstracts() {
		return abstracts;
	}
	public final void setAbstracts(List<String> abstracts) {
		this.abstracts = abstracts;
	}
	public final List<String> getSubjects() {
		return subjects;
	}
	public final void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}
	public final String getDataset() {
		return dataset;
	}
	public final void setDataset(String dataset) {
		this.dataset = dataset;
	}
}
