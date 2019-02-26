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

package gov.lanl.ingest.oaitape;

import java.io.InputStream;
import java.io.StringReader;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

import org.apache.log4j.Logger;
import org.apache.xml.security.Init;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.signature.SignedInfo;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.signature.XMLSignatureException;
import org.apache.xml.security.utils.Constants;
import org.apache.xml.security.utils.XMLUtils;
import org.apache.xml.security.utils.resolver.ResourceResolverSpi;
import org.apache.xml.security.utils.resolver.implementations.ResolverAnonymous;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import sun.misc.BASE64Encoder;

/**
 * SigUtils adds xmlsignature functions
 */

public class SigUtils {

    static {
        Init.init();
    }

    private static String OAIPMHNS = "http://www.openarchives.org/OAI/2.0/";

    private static String SIGNS = Constants.SignatureSpecNS;

    private String message = null;

    static Logger log = Logger.getLogger(SigUtils.class.getName());

    public String getMessage() {
        return this.message;
    }

    /**
     * this method is for verifying signatures
     */

    public boolean verify(XMLSignature signature) {
        boolean isVerified = false;
        KeyInfo ki = signature.getKeyInfo();

        try {

            if (ki != null) {
                X509Certificate cert = signature.getKeyInfo()
                        .getX509Certificate();

                if (cert != null) {
                    try {
                         isVerified = signature.checkSignatureValue(cert);
                    } catch (NullPointerException e) {
                        this.message = "NPE occurred calling: ";
                        return false;
                    }
                    this.message = "verification fails";
                } else {
                    PublicKey pk = signature.getKeyInfo().getPublicKey();
                    if (pk != null) {
                        isVerified = signature.checkSignatureValue(cert);
                    } else {
                        isVerified = false;
                        this.message = "Did not find a public key, so I can't check the signature";
                    }
                }
            } else {
                isVerified = false;
                this.message = "Did not find a KeyInfo";
            }

        } catch (Exception e) {
            isVerified = false;
            this.message = e.getMessage();
        }

        return isVerified;
    }

    /**
     * this method verifyes oai record using signature from abouts container
     */

    public boolean verify_record(String record) {
        try {

            javax.xml.parsers.DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory
                    .newInstance();

            javax.xml.parsers.DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            dbf.setAttribute("http://xml.org/sax/features/namespaces",
                    Boolean.TRUE);
            javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource IS = new InputSource(new StringReader(record));
            org.w3c.dom.Document doc = db.parse(IS);
            Constants.setSignatureSpecNSprefix("dsig");
            NodeList aboutnodes = doc.getElementsByTagNameNS(OAIPMHNS, "about");

            for (int i = 0; i < aboutnodes.getLength(); i++) {
                Element about = (Element) aboutnodes.item(i);

                NodeList nodes = about.getElementsByTagNameNS(
                        Constants.SignatureSpecNS, "Signature");

                if (nodes.getLength() != 0) {
                    // Element sigElement = (Element)
                    // nodes.item(nodes.getLength()-1);
                    Element sigElement = (Element) nodes.item(0);
                    XMLSignature signature = new XMLSignature(sigElement, "");
                    return this.verify(signature);
                }
            }

            this.message = "signature not found in about container";
            return false;
        } catch (Exception e) {
            log.info("from verify_record:" + e.getMessage());
            this.message = e.getMessage();
            return false;
        }

    }

    /**
     * this method is for no URI in xmlsig in abouts container
     */

    public boolean verify_anonimos(String metadata, String about) {
        try {

            InputStream anonimosRef = new java.io.ByteArrayInputStream(metadata
                    .getBytes());
            ResourceResolverSpi resolver = new ResolverAnonymous(anonimosRef);
            boolean followManifests = false;
            javax.xml.parsers.DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory
                    .newInstance();
            dbf.setNamespaceAware(true);
            dbf.setAttribute("http://xml.org/sax/features/namespaces",
                    Boolean.TRUE);

            javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource IS = new InputSource(new StringReader(about));
            org.w3c.dom.Document doc = db.parse(IS);
            Element nscontext = XMLUtils.createDSctx(doc, "dsig",
                    Constants.SignatureSpecNS);
            Element sigElement = (Element) XPathAPI.selectSingleNode(doc,
                    "//dsig:Signature[1]", nscontext);
            XMLSignature signature = new XMLSignature(sigElement, "");

            if (resolver != null) {
                signature.addResourceResolver(resolver);
            }
            signature.setFollowNestedManifests(followManifests);
            return this.verify(signature);
        } catch (Exception e) {
            // e.printStackTrace();
            this.message = e.getMessage();
            return false;
        }
    }

    /**
     * this utility method to get digest value from Signature SignedInfo element
     */

    public String getDigest(SignedInfo info) throws Exception {
        byte[] digest = info.item(0).getDigestValue();
        BASE64Encoder encoder = new BASE64Encoder();
        String coded = new String(encoder.encodeBuffer(digest));
        return coded.trim();
    }

}