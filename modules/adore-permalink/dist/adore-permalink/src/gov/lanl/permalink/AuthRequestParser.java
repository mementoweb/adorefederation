package gov.lanl.permalink;

import gov.lanl.util.xpath.DOM4JImpl;
import gov.lanl.util.xpath.DocumentField;
import gov.lanl.util.xpath.DocumentProfile;
import gov.lanl.util.xpath.DocumentProfileFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

public class AuthRequestParser {

	public static HashMap processContent(String xml) throws Exception {
		HashMap map = new HashMap<String, String>();
		try {

			Properties props = new Properties();

			props.put("profile.namespace.1", "http://library.lanl.gov/oppie/auth/getinst");
			props.put("profile.namespace.prefix.1", "op");
			props.put("profile.field.name.1", "inst");
			props.put("profile.field.xpath.1", "//op:OPPIEAccess/op:INST");
			props.put("profile.field.name.2", "sfxurl");
			props.put("profile.field.xpath.2", "//op:OPPIEAccess/op:SFXBASEURL");

			DocumentProfile docProfile = DocumentProfileFactory.generateDocProfile(props);

			DOM4JImpl doc = new DOM4JImpl();
			try {
				doc.setDocument(xml);
				doc.setNamespaces(docProfile.getNamespaceProfiles());
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Populate Metadata Object
			for (Iterator i = docProfile.getDocFields(); i.hasNext();) {
				DocumentField df = (DocumentField) i.next();
				String field = df.getFieldName();
				String xpath = df.getXpath();

				ArrayList values = doc.xpath(xpath);
				for (Iterator k = values.iterator(); k.hasNext();) {
					String value = (String) k.next();
					if (k != null) {
						map.put(field, value);
					}
				}
			}
		} catch (Exception e) { // run time exception
			throw new Exception("General exception in AuthRequestParser: "
					+ e.getMessage());
		}
		return map;
	}
}
