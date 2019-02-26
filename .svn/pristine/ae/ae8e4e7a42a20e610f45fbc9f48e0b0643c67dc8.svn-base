/*
 * Los Alamos National Laboratory
 * Research Library
 * Digital Library Research & Prototyping Team
 * 
 * Copyright (c) 1998-2005 The Regents of the University of California.
 * 
 * Unless otherwise indicated, this information has been authored by an employee 
 * or employees of the University of California, operator of the Los Alamos National
 * Laboratory under Contract No. W-7405-ENG-36 with the U.S. Department of Energy. 
 * The U.S. Government has rights to use, reproduce, and distribute this information. 
 * The public may copy and use this information without charge, provided that this 
 * Notice and any statement of authorship are reproduced on all copies. Neither the 
 * Government nor the University makes any warranty, express or implied, or assumes 
 * any liability or responsibility for the use of this information.
 * 
 */

package info.repo.didl.impl.tools;

/**
 * XmlUtil library for encoding/decoding xml fragment
 */

public class XmlUtil {

	/**
	 * XML encode a string.
	 * @param s any String
	 * @return the String with &amp;, &lt;, and &gt; encoded for use in XML.
	 */
	public static String encode(String s) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < s.length(); ++i) {
			char c = s.charAt(i);
			switch (c) {
			case '&':
				sb.append("&amp;");
				break;
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '"':
				sb.append("&quot;");
				break;
			case '\'':
				sb.append("&apos;");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * XML decode a string.
	 * @param s any String
	 * @return the String with &amp;, &lt;, and &gt; decoded back to their original character
	 */
	public static String decode(String s) {
		String str = s;
		str = str.replaceAll("&amp;", "&");
		str = str.replaceAll("&lt;", "<");
		str = str.replaceAll("&gt;", ">");
		str = str.replaceAll("&quot;", "\"");
		str = str.replaceAll("&apos;", "\'");
		return str;
	}

}
