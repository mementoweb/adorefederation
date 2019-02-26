/*
 * Copyright (c) 2007  Los Alamos National Security, LLC.
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

package gov.lanl.util.xslt;

import gov.lanl.util.FileUtil;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.FileNotFoundException;
import org.apache.log4j.Logger;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.URIResolver;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Use the TraX interface to perform a transformation in the simplest manner
 * possible (3 statements).
 */
public class XSLTTransformer {
	private static Logger logger = Logger.getLogger(XSLTTransformer.class.getName());

	javax.xml.transform.Transformer transformer;

	public XSLTTransformer(String stylefile) {
		//	try {
		//	    Class.forName("net.sf.saxon.TransformerFactoryImpl");
		//	    System.setProperty("javax.xml.transform.TransformerFactory",
		//		    "net.sf.saxon.TransformerFactoryImpl");
		//    } catch (Exception ex) {
		//        logger.fatal("ClassNotFoundException for net.sf.saxon.TransformerFactoryImpl");
		//        logger.fatal(ex);
		//    }

		try {
			// Use the TransformerFactory to instantiate a Transformer that will
			// work with the stylesheet you specify. This method call also processes
			// the stylesheet into a compiled Templates object.

			MyURLResolver resolver = null;
			String xsltname = null;

			if (stylefile.lastIndexOf('/') == -1) {
				resolver = new MyURLResolver(null);
				xsltname = stylefile;
			} else {
				resolver = new MyURLResolver(stylefile.substring(0, stylefile
						.lastIndexOf('/')));
				xsltname = stylefile.substring(stylefile.lastIndexOf('/') + 1);
			}

			TransformerFactory tFactory = TransformerFactory.newInstance();
			tFactory.setURIResolver(resolver);

			transformer = tFactory.newTransformer(resolver.resolve(xsltname,
					null));

			// Use the Transformer to apply the associated Templates object
			// to
			// an XML document
			// (foo.xml) and write the output to a file (foo.out).
		} catch (Exception ex) {
			logger.fatal(stylefile + " throws exception");
			logger.fatal(ex);
		}
	}

	public void setParameter(String paraName, String paraValue) {
		transformer.setParameter(paraName, paraValue);
	}

	public synchronized String transform(String input)
			throws TransformerException, TransformerConfigurationException,
			IOException {
		StringWriter sw = new StringWriter();
		transformer.transform(new StreamSource(new StringReader(input)),new StreamResult(sw));
		return sw.toString();
	}
	
	public static void main(String[] args) {
		String input = args[0];
		String xslFile = args[1];
		try {
			XSLTTransformer xtran = XSLTPool.borrowObject(xslFile);
			String output = xtran.transform(FileUtil.getContents(new File(input)));
			XSLTPool.returnObject(xslFile, xtran);
			System.out.println(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class MyURLResolver implements URIResolver {
	String prefix = null;

	public MyURLResolver(String prefix) {
		this.prefix = prefix;
	}

	public Source resolve(String href, String base) throws TransformerException {
		try {
			InputStream stream = null;
			if (new File(prefix, href).exists()) {
				stream = new FileInputStream(new File(prefix, href));
			} else
				stream = this.getClass().getResourceAsStream(
						prefix + "/" + href);

			if (stream == null)
				throw new TransformerException("no " + href + " found");
			return new StreamSource(stream);
		} catch (FileNotFoundException ex) {
			throw new TransformerException("no " + href + "found");
		}
	}
}
