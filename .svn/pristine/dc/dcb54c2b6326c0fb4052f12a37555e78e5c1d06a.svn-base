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

package gov.lanl.xmlsig;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

public class XMLSigTest extends TestCase {

    static String testdir = null;

    String keystoreType = "JKS";

    String keystoreFile = testdir + File.separator + "keystore.jks";

    static String keystorePass = "xmlsecurity";

    String privateKeyAlias = "aDORe";

    String privateKeyPass = "xmlsecurity";

    String certificateAlias = "aDORe";

    String certificateURL = testdir + File.separator + "aDORe.crt";
    
    static Logger log = Logger.getLogger(XMLSigTest.class.getName());
    
    public XMLSigTest(String name) {
        super(name);
    }
    
    public XMLSigTest() {
    }
    
    public static void main(String args[]) {
        
        if (args.length > 0) {
            testdir = args[0];
            keystorePass = args[1];
        } else if (testdir == null || keystorePass == null) {
            testdir = System.getProperty("XMLSigTest.TestDir");
            keystorePass = System.getProperty("XMLSigTest.KeyStorePass");
        }
        
        TestSuite suite = new TestSuite("XMLSigTest");
        suite.addTest(new XMLSigTest() {
            protected void runTest() {
                try {
                    testSignRef();
                    testSignRefwithDigest();
                    testSignDidl();
                    testSignInline();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        junit.textui.TestRunner.run(suite);

    }

    public void testSignRefwithDigest() {
        try {
            CreateXMLSig c = new CreateXMLSig(keystoreType, keystoreFile,
                    keystorePass, privateKeyAlias, privateKeyPass,
                    certificateURL);

            String sig = c.signRef("http://gws.lanl.gov",
                    "o+c5rvs4+vieRz1y0dee0ycqk9k=");
            log.debug("testSignRefwithDigest");
            log.debug(sig);

        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }

    public void testSignRef() {
        try {
            CreateXMLSig c = new CreateXMLSig(keystoreType, keystoreFile,
                    keystorePass, privateKeyAlias, privateKeyPass,
                    certificateURL);

            String sig = c.signRef("http://gws.lanl.gov");
            log.debug("testSignRef:");
            log.debug(sig);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }

    public void testSignDidl() {
        try {
            CreateXMLSig c = new CreateXMLSig(keystoreType, keystoreFile,
                    keystorePass, privateKeyAlias, privateKeyPass,
                    certificateURL);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            File didl = new File(testdir + File.separator + "signed.xml");
            doc = db.parse(didl);
            VerifyExampleTest ve = new VerifyExampleTest();
            ve.verify(doc);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }

    public void testSignInline() {
        try {
            CreateXMLSig c = new CreateXMLSig(keystoreType, keystoreFile,
                    keystorePass, privateKeyAlias, privateKeyPass,
                    certificateURL);

            String input = "<book>\n\t<Author>Joe</Author>"
                    + "\n\t<ISBN value=\"12300093456\"/>\n</book>";
            String sig = c.signXMLinline(
                    "#uuid-fb5fbfed-26ff-4d26-8aad-1fe94f54d9ef", input);
            log.debug("testSignXMLInline");
            log.debug(sig);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }

    }

    public static Test suite() {
        return new TestSuite(XMLSigTest.class);
    }
}
