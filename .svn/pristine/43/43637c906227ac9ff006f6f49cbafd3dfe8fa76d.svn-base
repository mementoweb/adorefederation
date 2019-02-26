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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class CoreToDot {

    public static String nscore="info:pathways/core#";
    public static String nsrdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    private StringBuffer id;
    private StringBuffer identifier;
    private StringBuffer version;
    private StringBuffer provider;
    private StringBuffer location;
    private boolean _isid = false;
    private boolean _isversion = false;
    private boolean _isprovider = false;
    private boolean _isidentifier=false;

    static public  PrintWriter writer;
    private int eindex=0;
    private Map aentity = new HashMap();
    private Vector pentity = new Vector(); 
    private boolean islineage = false;
    private boolean _islocation = false;
  

    public static void main  (String []args)throws Exception
    {
	String outputdir="/tmp";
        writer = new PrintWriter(new FileWriter(new File(outputdir+"/test.dot")));
     
	writer.println("digraph G{");
        writer.println("rankdir=LR");
        writer.println("fontzise=80");
        writer.println("fontname=helvetica");
        writer.println("ranksep = 0.25");
        writer.println("nodesep = .05;");
	//writer.println("page = \"10.5,8.5\";");
	//writer.println("size = \"10.0,7.5\";"); 

        writer.println("edge [style=\"setlinewidth(2)\"];"); 
        writer.flush();

	CoreToDot tr = new CoreToDot();

	XmlPullParserFactory factory = XmlPullParserFactory.newInstance(); 
        
        factory.setNamespaceAware(true); 
 
         XmlPullParser parser = factory.newPullParser(); 
         parser.setInput(new FileInputStream (new File(args[0])), "UTF-8");
	 System.out.println(args[0]);
         while (parser.getEventType() != XmlPullParser.END_DOCUMENT) { 
            tr.processEvent(parser); 
            parser.next(); 
        } 

        writer.println("}");
        writer.close();
    }


     public  void processEvent(XmlPullParser parser) throws Exception { 
       String fullName;      
            switch (parser.getEventType()) { 
                         case XmlPullParser.START_TAG : 
                         fullName = processName(parser); 
                if (fullName.equals(fullName(nscore,"entity"))){
                             String aboutref = processAttribute(parser,"about");
                             if (aboutref.length()>0)
				 { int i = aboutref.length()/4;
                                   aboutref=aboutref.substring(0,i);}
                             
                             eindex = eindex+1;
                             writer.println("a" + eindex+ "[label=\"    \\n\\n\\n" + aboutref + "...\\n\\n\\n    \"," +
                             "shape=ellipse,fontsize=45,style=filled,fontname=helvetica,fillcolor=lightskyblue]");			    
                             aentity.put("e","a"+eindex);
                             pentity.add("a"+ eindex);    
	                    } 
		if (fullName.equals(fullName(nscore,"hasIdentifier"))){
		       eindex = eindex+1; 
		       writer.println("a" + eindex + "[label=\"hasIdentifier\",fontsize=45,shape=plaintext]"); 
		       int j = pentity.size()-1; 
		       String e = (String)pentity.get(j); 
		       writer.println(e + "->"+ "a" + eindex + " [arrowhead=none]"); 
                       aentity.put("hi","a"+eindex);
		       eindex = eindex+1; 
		      String res = processAttribute(parser,"resource");
		      writer.println("a" + eindex  +  "  [label =\"" + res 
                                    + "\",shape=box,style=filled,color=orange,fontsize=45]");
		      writer.println( (String)aentity.get("hi") + "->" + "a" + eindex  + "[arrowhead=none]"); 
 
		 }    

		if (fullName.equals(fullName(nscore,"hasProviderPersistence"))){ 
		    eindex = eindex+1; 
		    writer.println("a" + eindex + "[label=\"hasProviderPersistence\",fontsize=45,shape=plaintext]");
		    int j = pentity.size()-1; 
		    String e = (String)pentity.get(j);
		    writer.println(e + "->"+ "a" + eindex + " [arrowhead=none]");
                    aentity.put("pp","a"+eindex);
                    eindex=eindex+1; 
		    String res = processAttribute(parser,"resource"); 
		    writer.println("a" + eindex  +  "  [label =\"" + res 
				   + "\",shape=box,style=filled,color=white,fontsize=45]");
		    writer.println( (String)aentity.get("pp") + "->" + "a" + eindex  + "[arrowhead=none]");
 
		} 
 
                	       
             if (fullName.equals(fullName(nscore,"hasProviderInfo"))){
		eindex = eindex+1;   
		writer.println("a" + eindex + "[label=\"hasProviderInfo\",shape=plaintext,fontsize=45]");
		int j = pentity.size()-1; 
		String e = (String)pentity.get(j);
		writer.println(e + "->"+ "a" + eindex + " [arrowhead=none]");
                aentity.put("pi","a" + eindex);  
             }

	     if (fullName.equals(fullName(nscore,"hasSemantic"))){ 
		 eindex = eindex+1;
		 String res = processAttribute(parser,"resource");
		 writer.println("a"+ eindex + " [label=\"hasSemantic\",shape=plaintext,fontsize=45]");
		 int j = pentity.size()-1;
                 String e = (String)pentity.get(j);
		 writer.println(e + "->"+ "a" + eindex + " [arrowhead=none]");
                 aentity.put("s","a" +eindex);
                 eindex = eindex+1;
                 writer.println("a" + eindex + 
                                   "  [label =\"" + res  + "\",shape=box,style=filled,color=palegreen,fontsize=45]");
		 writer.println((String)aentity.get("s")  + "-> a" + eindex + " [arrowhead=none]");
                 
             }

	     if (fullName.equals(fullName(nscore,"hasDatastream"))){
                 eindex = eindex+1; 
		 writer.println("a" + eindex + " [label=\"hasDatastream\",shape=plaintext,fontsize=45]");
				aentity.put("r","a"+eindex);
				int j = pentity.size()-1; 
				String e = (String)pentity.get(j); 
				writer.println(e + "->"+ "a" + eindex + " [arrowhead=none]");
	     }
		 if (fullName.equals(fullName(nscore,"hasLocation"))){		     
                     String loc =processAttribute(parser,"resource");
		     aentity.put("loc",loc);
                 }

                 if (fullName.equals(fullName(nscore,"hasFormat"))){ 
		     String fmt = processAttribute(parser,"resource");
                     aentity.put("f",fmt);
                     
                 } 

		 if (fullName.equals(fullName(nscore,"hasLineage"))){
		     eindex = eindex+1; 
		     writer.println("a"+ eindex +"[label=\"hasLineage\",color=blue,shape=plaintext,fontsize=45]");
                     aentity.put("l","a"+eindex);
		     int j = pentity.size()-1;
		     String e = (String)pentity.get(j); 
		     writer.println(e + "->"+ "a" + eindex + " [arrowhead=none]"); 
                     islineage=true;

                  }
             if (fullName.equals(fullName(nscore,"provider"))){
                 _isprovider=true; 
                eindex = eindex+1; 
                writer.println("a" + eindex + "[label=provider,shape=plaintext,group=x,fontsize=45]");
                aentity.put("p","a" + eindex);
                writer.println((String)aentity.get("pi") + "->"+ "a" + eindex+ " [arrowhead=none]");
                provider = new StringBuffer();  
                }
              if (fullName.equals(fullName(nscore,"versionKey"))){
                _isversion=true;                                         
                eindex = eindex+1;                                                                      
                writer.println("a" + eindex + "[label=versionKey,shape=plaintext,group=x,fontsize=45]");
                 aentity.put("v","a" + eindex);                      
                writer.println((String)aentity.get("pi") + "->"+ "a" + eindex+" [arrowhead=none]");
                version=new StringBuffer();                        
                }   
               if (fullName.equals(fullName(nscore,"identifier"))){
                 _isid=true;                                    
                eindex = eindex+1;                                                                                      writer.println("a" + eindex + "[label=identifier,shape=plaintext,group=x,fontsize=45]");                                                aentity.put("i","a" + eindex); 
                writer.println((String)aentity.get("pi") + "->"+ "a" + eindex+ " [arrowhead=none]");
                id= new StringBuffer();                             
                }     
              break;
             case XmlPullParser.END_TAG :
                  fullName = processName(parser);
                   

		  if (fullName.equals(fullName(nscore,"entity"))){
                      int j = pentity.size()-1;
                      if (j!=0)
			  {
			   if (islineage) {
			   writer.println((String)aentity.get("l") + "->" +
                              (String)pentity.get(j)+" [arrowhead=none]");
                                  islineage = false;  
			      }
                             else {
		                  writer.println((String)pentity.get(j-1) + "->" + (String)pentity.get(j));
				  }
                          }
		      pentity.removeElementAt(j); 
                   }
                     
		  
		  
                  if (fullName.equals(fullName(nscore,"hasDatastream"))){
                      eindex=eindex+1;
		     
		      writer.println("a" + eindex + " [label=\"hasFormat\",shape=plaintext,group=y,fontsize=45]"); 
		      writer.println((String)aentity.get("r") + "-> a" +eindex + "[arrowhead=none]");
                      aentity.put("flabel","a"+eindex);
		      eindex = eindex+1;
                      String pronomurl = "http://african.lanl.gov/pronom-format-registry/OAIHandler"+ 
			   "?verb=GetRecord&identifier=" + aentity.get("f") + "&metadataPrefix=pronom"; 
                    writer.println("a" + eindex +
                      " [label=\"" + aentity.get("f") + 
                           "\",style=filled,color=khaki,shape=house,fontsize=45,URL=\"" + pronomurl + "\"]");

		    writer.println((String)aentity.get("flabel") + "-> a" +eindex + "[arrowhead=none]");

		    aentity.remove("f");
                    eindex = eindex+1;
		    writer.println("a" + eindex + " [label=\"hasLocation\",shape=plaintext,group=x,fontsize=45]"); 
		    writer.println((String)aentity.get("r") + "-> a" +eindex + "[arrowhead=none]"); 
                    aentity.put("llabel","a"+eindex);
		    eindex=eindex+1;
		    String loc =(String) aentity.get("loc");
                    if (loc.length()>0) 
			{ int i = loc.length()/4;
			loc = loc.substring(0,i);} 

                    writer.println("a" + eindex + " [label=\"" + loc + "...\",style=filled,fontsize=45,color=khaki,shape=house,URL=\"" + aentity.get("loc")+ "\"]");
                    aentity.remove("loc");
		    writer.println((String)aentity.get("llabel") + "-> a" +eindex + "[arrowhead=none]");
                   
                  }

                  if (fullName.equals(fullName(nscore,"provider"))){                               
                               _isprovider=false;                                                                                           eindex = eindex+1;  
                     writer.println("a" + eindex + 
                     " [label=\""+provider.toString() + "\",color=lightgrey,shape=box,style=filled,fontsize=45]");
                     writer.println((String)aentity.get("p") + "->"+ "a" + eindex+ " [arrowhead=none]");
		    
                    
                                                                                                        
                }     
                   if (fullName.equals(fullName(nscore,"versionKey"))){                                      
                     _isversion=false;                                                                      
                     eindex = eindex+1;                                                                     
                     writer.println("a" + eindex + 
                      " [label=\""+version.toString() + "\",color=lightgrey,shape=box,style=filled,fontsize=45]");
                      writer.println((String)aentity.get("v") + "->"+ "a" + eindex+ " [arrowhead=none]");         
                                                                                                             
                }
                    if (fullName.equals(fullName(nscore,"identifier"))){            
                     _isid=false;                                                                       
                     eindex = eindex+1;                                                                      
                     writer.println("a" + eindex +                                                           
                      " [label=\""+id.toString() + "\",color=lightgrey,shape=box,style=filled,fontsize=45]");
                     writer.println((String)aentity.get("i") + "->"+ "a" + eindex+ " [arrowhead=none]");                                                                                         
                }       
		    if (fullName.equals(fullName(nscore,"hasProviderInfo"))){
                        if (islineage)
			{
			String baseurl = GraphServlet.requestURI;                       
			     String url = baseurl +"?rft_id="+id.toString() +
                                   "&res_id=" + provider.toString() + 
                                   "&url_ver=Z39.88-2004" +
                                   "&svc_id=info:pathways/svc/pwc.rdf";
			
                        
			int j = pentity.size()-1;
			String e = (String)pentity.get(j);
			writer.println(e + "[URL=\""+ url+"\"]"); 
			}
                         
                    }
		    
              break;
             case XmlPullParser.TEXT :
                     
               String text = new String(parser.getText());
              
                  if (_isid) {
                  id = id.append(text); 
                 } 
                  if (_isversion){
                  version=version.append(text);
                }
                  if (_isprovider){
                  provider=provider.append(text);
                 } 
		  if (_islocation){
		      location=location.append(text);
                  }
		  
            break; 
 
        }
 
   } 


    public  String processName(XmlPullParser xmlr) { 
 
                                   String prefix = xmlr.getPrefix();
                                   String uri = xmlr.getNamespace();
                                   String localName = xmlr.getName();
                                   return  fullName(uri, localName);
 
                           } 

   public  String  processAttribute(XmlPullParser xmlr,String attrName) {
          String value=""; 
          for (int i = 0; i < xmlr.getAttributeCount(); i++)
              { 
 
                  if (xmlr.getAttributeName(i).equals(attrName))
                      { value = xmlr.getAttributeValue(i);
                        return value;
                      } 
              }
          return value; 
      } 


  public  static String fullName(String namespaceURI, String localName) {
        StringBuffer sb = new StringBuffer();
        sb.append(namespaceURI);
        sb.append("#");
        sb.append(localName); 
        return sb.toString();
    } 

}
