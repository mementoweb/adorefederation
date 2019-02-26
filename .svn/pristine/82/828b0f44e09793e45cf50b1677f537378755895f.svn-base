/*
 * Copyright (c) 2008  Los Alamos National Security, LLC.
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

package gov.lanl.disseminator.service.toc;

import gov.lanl.disseminator.model.ContextObjectContainer;
import gov.lanl.disseminator.model.Entity;
import gov.lanl.disseminator.service.DefaultXSLTService;
import gov.lanl.util.resource.Resource;
import gov.lanl.util.xslt.XSLTPool;
import gov.lanl.util.xslt.XSLTTransformer;
import info.openurl.oom.config.ClassConfig;
import info.openurl.oom.config.OpenURLConfig;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import javax.xml.transform.TransformerException;

public class SplashService extends DefaultXSLTService {
    private String baseuri = null;
    private String openurl = null;
	 
    public SplashService(OpenURLConfig openURLConfig, ClassConfig classConfig) throws TransformerException {
	super(openURLConfig, classConfig);
	this.baseuri=classConfig.getArg("baseurl");
	this.openurl=classConfig.getArg("openurl");
	// TODO Auto-generated constructor stub
    }


    /**
     * Construct a Hello World web service class.
     * 
     * @param openURLConfig
     * @param classConfig
     * @throws TransformerException
     */
 
    
   
   

    public String transform (String input,String param) throws Exception{
	XSLTTransformer xtran = XSLTPool.borrowObject("/etc/xslt/disseminator/entry.xsl");
        if (xtran!=null) {
	xtran.setParameter("entryid", param);
        }
	String output = xtran.transform(input);
	//System.out.println(output);
	XSLTPool.returnObject("/etc/xslt/disseminator/entry.xsl", xtran);
	return output;
	
}
    
    
    @Override
    public Resource serve(ContextObjectContainer co) throws Exception {
       // if (input == null)
         //   throw new NullPointerException("no resource passed in");
	co.getServiceType().setProperty("rem", "true"); //flag to collect services
	SplashPageX sp = new SplashPageX();
	String res_url;
	 Entity resolver = co.getResolver(); 
	 if (resolver.hasProperty("res_id"))
	    { res_url = resolver.getProperty("res_id");
	    }
	    else {
	      res_url = baseuri;
	    }
	String oflag;
	Entity service =  co.getServiceType();
	 if (service.hasProperty("svc.openurl")) {
	     oflag = service.getProperty("svc.openurl");
	     System.out.println("oflag:"+ oflag);
	 }
	 else {
	     oflag=openurl;
	 }
	 
	 if (oflag.equals("true")){
	     sp.setFlag(true);
	 }
	 else {
	     sp.setFlag(false);
	 }
	 
	 String profile = service.getProperty("recommendation");
	 sp.setProfile(profile);
	 String sfxbaseurl = "http://linkseeker.lanl.gov/lanl";
	 if (service.hasProperty("svc.sfxbaseurl")) {
	   sfxbaseurl = service.getProperty("svc.sfxbaseurl");
	 }
	 sp.setSFX(sfxbaseurl);
	HashMap marcXML = getMetaData(co);
	sp.setMarcXML(marcXML);
	sp.setBaseURI(res_url);
        String output = sp.getAtomXML(co);
        Resource result = new Resource();
        String htmlout = transform(output);
	
	result.setBytes(htmlout.getBytes("UTF-8"));
        result.setContentType("text/html");
        //result.setBytes(output.getBytes());
        return result;
    }



   



    public URI getServiceID() throws URISyntaxException {
	// TODO Auto-generated method stub
	return null;
    }
}
