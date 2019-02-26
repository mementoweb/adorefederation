package org.adore.didl.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.adore.didl.content.DC;
import org.adore.didl.content.DII;

import junit.framework.TestCase;

public class DIITest extends TestCase {
	private String xml="  \n   <dii:Identifier xsi:schemaLocation=\"urn:mpeg:mpeg21:2002:01-DII-NS http://standards.iso.org/ittf/PubliclyAvailableStandards/MPEG-21_schema_files/dii/dii.xsd\" xmlns:dii=\"urn:mpeg:mpeg21:2002:01-DII-NS\">info:doi/10.1016/j.dyepig.2004.06.023</dii:Identifier>\n";
	private String identifier="info:doi/10.1016/j.dyepig.2004.06.023";
	  public  void testCreate() throws Exception{
	       DII dii=new DII(0,identifier);
	        
	        ByteArrayOutputStream stream = new ByteArrayOutputStream();
	        dii.write(stream, dii);
	        String output=stream.toString();
	        System.err.println(output);
	        assertTrue("DII creating correct", output.indexOf(identifier)!=-1);
	    }
	    
	    public void testParse() throws Exception{
	        DII dii=new DII();
	        dii=(DII)dii.read(new ByteArrayInputStream(xml.getBytes()));
	        assertTrue("dii value matched in deserialization",dii.getValue().equals(identifier));
	    }
	    
}
