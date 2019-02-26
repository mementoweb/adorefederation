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

package gov.lanl.repo.oaidb.srv;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class PutResponseHandler {
    
    static javax.xml.parsers.DocumentBuilderFactory dbf = null;
    static {
        dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setAttribute("http://xml.org/sax/features/namespaces", Boolean.TRUE);
    }
    
    org.w3c.dom.Document doc;
    boolean success = false;
    ArrayList<PMPError> errors;
    
    public PutResponseHandler(String response) {
        try {
            javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource IS = new InputSource(new StringReader(response));
            doc = db.parse(IS);
            checkErrors();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public String getError() {
        return errors.get(0).getErrorMessage();
    }
    
    private void checkErrors() {
        NodeList errorNodes = doc.getElementsByTagName("error");
        int nlength = errorNodes.getLength();
        errors = new ArrayList<PMPError>();
        for (int i = 0; i < nlength; i++) {
            Node node = errorNodes.item(i);
            PMPError error = new PMPError();
            error.setErrorCode(((Element) node).getAttribute("code"));
            error.setErrorMessage(((Element) node).getFirstChild().getNodeValue());
            errors.add(error);
        }
        if (errors.size() == 0)
            success = true;
    }
    
    public class PMPError {
        private String errorCode;
        private String errorMessage;
        
        public String getErrorCode() {
            return errorCode;
        }
        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }
        public String getErrorMessage() {
            return errorMessage;
        }
        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }

    
}
