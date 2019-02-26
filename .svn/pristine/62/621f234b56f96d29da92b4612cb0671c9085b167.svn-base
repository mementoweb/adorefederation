package gov.lanl.disseminator.service.didl;

import gov.lanl.disseminator.DmtConstants;
import gov.lanl.disseminator.matchmaker.MatchMakerCo;
import gov.lanl.disseminator.model.ContextObjectContainer;
import gov.lanl.disseminator.model.Entity;
import gov.lanl.disseminator.service.DefaultXSLTService;
import gov.lanl.util.resource.Resource;
import info.openurl.oom.config.ClassConfig;
import info.openurl.oom.config.OpenURLConfig;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;

public class GetDIDLVparam extends DefaultXSLTService {

    public GetDIDLVparam (OpenURLConfig openURLConfig, ClassConfig classConfig) throws TransformerException {
	super(openURLConfig, classConfig);
	// TODO Auto-generated constructor stub
    }

    public Resource serve(ContextObjectContainer co)
            throws Exception {
        String res_url="";
        Entity referent = co.getReferent();
        Entity resolver = co.getResolver();
        if (resolver.hasProperty("res_id"))
           { res_url = resolver.getProperty("res_id");
        }
        //System.out.println("res_url"+res_url);
        String xslt = getXSLTPath();
        //System.out.println("xslt"+xslt);
        HashMap param_map = new HashMap();
        param_map.put("baseurl", res_url);
        Resource result =  new Resource();
        String xml = new String (referent.getContent());
        if (xslt.equals("none")) {
       
           result.setContentType(getMimeType());
           result.setBytes(xml.getBytes("UTF-8"));
           //result.setContentType("application/xml");
        }
        
        else {
             String output = transform(xml, param_map);
	     result.setContentType(getMimeType());
	     result.setBytes(output.getBytes("UTF-8"));   
            
        }
            return result;
        
    }
}