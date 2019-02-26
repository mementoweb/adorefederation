package gov.lanl.adore.solr;

import org.marc4j.MarcReader;
import org.marc4j.MarcStreamReader;
import org.marc4j.MarcXmlReader;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;

import java.io.InputStream;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class MarcXMLRecordParser {

	public static void main(String args[]) throws Exception {
		InputStream in = new FileInputStream("/Users/rchute/Desktop/sample_isi_didl.xml");
		MarcReader reader = new MarcXmlReader(in);
		while (reader.hasNext()) {
			Record record = reader.next();
			System.out.println(record.toString());
		}
	}
	
    public static final Article parse(InputStream input, Article a) throws Exception {
        MarcReader reader = new MarcXmlReader(input);
        while (reader.hasNext()) {
            Record record = reader.next();
            
            // jtitle
            String jtitle = getValue(record,"773",'t');
            if (jtitle != null)
            	a.setJtitle(jtitle);
            
            // atitle
            String atitle = getValue(record,"245",'a');
            if (atitle != null)
            	a.setAtitle(atitle);
            
            // date
            for (Object df : record.getControlFields()) {
            	ControlField cf = (ControlField) df;
            	if (cf.getTag().equals("008")) {
					int d = getInteger(cf.getData().substring(7, 11));
					if (d != -1)
					    a.setDate(d);
            	}
            }
            
            // dataset
            String dataset = getValue(record,"040",'a');
            if (dataset != null)
            	a.setDataset(dataset);
            
            // lang
            String lang = getValue(record,"041",'a');
            if (lang != null)
            	a.setLanguage(lang);
            
            // volume, issue, page
			DataField df = (DataField) record.getVariableField("363");
			if (df != null) {
				List subfields = df.getSubfields();
				Iterator i = subfields.iterator();
				while (i.hasNext()) {
					Subfield subfield = (Subfield) i.next();
					char code = subfield.getCode();
					String data = subfield.getData();
					if (code == 'a' && data != null) {
						int vol = getInteger(data);
						if (vol != -1)
						    a.setVolume(vol);
					} else if (code == 'b' && data != null) {
						String[] d = data.split("-");
						int issue = getInteger(d[0]);
						if (issue != -1)
						    a.setIssue(issue);
					} else if (code == 'p' && data != null) {
						String[] d = data.split("-");
						int spage = getInteger(d[0]);
						if (spage != -1)
							a.setSpage(spage);
						if (d.length > 1) {
							int epage = getInteger(d[1]);
							if (epage != -1)
								a.setEpage(epage);
						}
					}
				}
			}
            
            // issn
            String issn = getValue(record,"773",'x');
            if (issn != null)
            	a.setIssn(issn);
            
            // subjects
            List<String> subjects = getValues(record, "650", 'a');
            if (subjects != null)
            	a.setSubjects(subjects);
            
            // authors
            List<Author> authors = getAuthors(record, a.getContentHash());
            if (authors != null)
            	a.setAuthors(authors);

        }
        return a;
    }

    private static final int getInteger(String data) {
    	int i = -1;
    	try {
    		data = data.replaceAll("[-a-zA-Z ]*", "");
    		i = Integer.parseInt(data);
    	} catch (Exception e) {}
    	return i;
    }
    
    private static final String getValue(Record record, String field, char subfield) {
    	String v = null;
    	try{
    		v = ((DataField) record.getVariableField(field)).getSubfield(subfield).getData();
    	} catch (Exception e) {}
    	return v;
    }
    
    private static final List getValues(Record record, String field, char subfield) {
    	List l = null;
    	try{
    		List subfields = ((DataField) record.getVariableField(field)).getSubfields(subfield);
    		for (Object sf : subfields) {
    			if (l == null)
    				l = new LinkedList<String>();
    			l.add(((Subfield) sf).getData());
    		}
    	} catch (Exception e) {}
    	return l;
    }
    
    private static final List<Author> getAuthors(Record record, String contentHash) {
    	final String AUTHOR = "Author";
    	final String CREATOR = "Creator";
    	List<Author> l = null;
    	Author a = null;
    	String[] tags = { "700", "710", "720" };
    	int index = 0;
    	try{
    		for (Object df : record.getVariableFields(tags)) {
                List subfields =  ((DataField) df).getSubfields();
                Iterator i = subfields.iterator();
                String name = null;
                String email = null;
                String inst = null;
                while (i.hasNext()) {
                    Subfield subfield = (Subfield) i.next();
                    char code = subfield.getCode();
                    String data = subfield.getData();
                    if (code == 'a' && data != null) {
                    	name = data;
                    } else if (code == 'c' && data != null) {
                    	name += ", " + data;
                    } else if (code == 'g' && data != null) {
                    	email = data;
                    } else if (code == 'u' && data != null) {
                    	inst = data;
                    } else if (code == 'e' && (!data.contains(AUTHOR) && !data.contains(CREATOR))) {
                    	name = null;
                    	continue;
                    } 
                }
                if (name != null) {
                	a = new Author(contentHash);
                	a.setName(name);
                	a.setAuthorIndex(index);
                	if (email != null)
                		a.setEmail(email);
                	if (inst != null)
                		a.setAffiliation(inst);
        			if (l == null)
        				l = new LinkedList<Author>();
        			l.add(a);
                	index++;
                }
                
    		}
    	} catch (Exception e) {}
    	return l;
    }
    
}
