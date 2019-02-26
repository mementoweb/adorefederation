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

package gov.lanl.webdot;

import java.net.URL;
import java.net.*;
import org.xmlpull.v1.XmlPullParser; 
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class GraphServlet extends HttpServlet{
  
    String _outputdir=null;
    String _graphvizURL=null;
    static String  requestURI;   

    public void doGet (HttpServletRequest request,
                       HttpServletResponse res)
        throws ServletException, IOException
    {
	//idenifier
	String id = request.getParameter("rft_id");
         
        //provider
	String  res_id = request.getParameter("res_id");

        String  svc_id = "info:pathways/svc/pwc.rdf";
        String url_ver = "Z39.88-2004";      
	String version = request.getParameter("rft.version");
        String     url = mapBaseUrl(res_id)+"?url_ver="+url_ver + "&rft_id="+id+"&svc_id="+svc_id;
        if (res_id.equals("info:sid/library.lanl.gov:test"))
	    {
		url = mapBaseUrl(res_id)+id+".rdf";
            }
        System.out.println("url:" +url);
        String     fmt = request.getParameter("rft_val_fmt");
 
        requestURI=(HttpUtils.getRequestURL(request)).toString();
        System.out.println("requesturl:" + requestURI);  
        Random rand = new Random(System.currentTimeMillis());
	int ids = rand.nextInt(); 
        PrintWriter writer = new PrintWriter(new FileWriter(new File(_outputdir + "/my"+ids+".dot")));
 
        writer.println("digraph G{");
        writer.println("rankdir=LR");
        //writer.println("fontzise=65");
	// writer.println("fontname=helvetica");
	writer.println("ranksep = 0.25");
        writer.println("nodesep = .05;");
        writer.println("page = \"10.5,8.5\";");                                                                        
        writer.println("size = \"10.0,7.5\";");       
 
        writer.println("edge [style=\"setlinewidth(2)\"];");
        writer.flush(); 
 
 
        CoreToDot tr = new CoreToDot();
        
	       tr.writer = writer;
	
	       try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	        factory.setNamespaceAware(true); 
	        XmlPullParser parser = factory.newPullParser();
	        URL u = new URL(url);
                URLConnection yc = u.openConnection();
	        parser.setInput(yc.getInputStream(), "UTF-8"); 
	        while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
	        tr.processEvent(parser); 
	        parser.next(); 
	        } 
 
	}
	      
	       catch (Exception e) {
 
		System.out.println(e.toString());
		res.sendError(404);
 
	       } 

	   writer.println("}");
	   writer.close(); 
	
	       
	res.setContentType("text/html");
	PrintWriter out = res.getWriter();
	
	out.println("<html><body>");
	       out.println("<a href=\""+_graphvizURL+"my"+ids+".dot.dot.map\">");
	       out.println("<img src=\""+_graphvizURL+"my"+ids+".dot.dot.png\" ismap>");	       
	   out.println("</a>");
	   out.close();
	
    }

    public static  String  mapBaseUrl(String provider)
    {   
	String baseurl = "http://african.lanl.gov/pwc/adore/adore-openurl-resolver-frontend";
	if (provider.equals("info:sid/arxiv.org:pathways"))
          { baseurl = "http://beta.arxiv.org:81/openurl-resolver";}
	      if (provider.equals("info:sid/fedora-cis.org:pathways"))
          { baseurl ="http://memex.cs.cornell.edu:8000/openurl/";}
	      if (provider.equals("info:sid/library.lanl.gov:pathways"))
	      {baseurl="http://african.lanl.gov/pwc/adore/adore-openurl-resolver-frontend";}
              if (provider.equals("info:sid/library.lanl.gov:test"))
		  {baseurl="http://adelie.lanl.gov:8000/webdot/";}
	      return baseurl;
    } 

    public void init (ServletConfig config) throws ServletException
    {
        ServletContext context=config.getServletContext();
	                           
	_outputdir=context.getInitParameter("outputdir");
	_graphvizURL=context.getInitParameter("graphvizURL");
	
	
    }
 
}
