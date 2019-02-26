/*
 * CodecTest.java
 *
 * Created on January 29, 2006, 2:16 AM
 *
 * Copyright (c) 2006  The Regents of the University of California and Ghent University
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
 */

package org.adore.didl.json;

import java.io.FileReader;

import junit.framework.TestCase;

/**
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public class CodecTest extends TestCase{
    private static int BUFFER_SIZE=64000;
    private String testdir=null;

    /** Creates a new instance of CodecTest */
    public CodecTest() {
    }

    public void setUp() {
        try {
            testdir = System.getProperty("Test.Temp");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }
    }

    
     /**
     * test base64 encode of "didl00", which correspods to ZGlkbDAw
     **/
    
    public void testJSON2DIDL00() throws Exception{
        String didlxml=createDIDLXML("ex/didl00.js");
	//        System.err.println(didlxml);
        assertTrue(didlxml.indexOf(">ZGlkbDAw<")!=-1);
    }
    
     /**
     * test ref
     **/
    public void testJSON2DIDL01() throws Exception{
        String didlxml=createDIDLXML("ex/didl01.js");
        //System.err.println(didlxml);
        assertTrue(didlxml.indexOf("http://didl01.org")!=-1);
        
    }
    
     /**
     * test xml inline
     **/
    
    public void testJSON2DIDL02() throws Exception{
        String didlxml=createDIDLXML("ex/didl02.js");
        //System.err.println(didlxml);
        assertTrue(didlxml.indexOf("<example><![CDATA[&&&<<<<>>>>>>>>>didl02]]></example>")!=-1);
        
    }
    
     /**
     * test html base64 inline
     **/
    
    public void testJSON2DIDL03() throws Exception{
        String didlxml=createDIDLXML("ex/didl03.js");
        //System.err.println(didlxml);
        assertTrue(didlxml.indexOf("base64")!=-1);
        
    }
 
    
    /**
     * test a didl such as:
     * item:
     *     component:
     *         resource 2
     *         resource 3
     */
    public void testJSON2DIDL04() throws Exception{
        String didlxml=createDIDLXML("ex/didl04.js");
        //System.err.println(didlxml);
        assertTrue(didlxml.indexOf("http://www.google.com")!=-1);
    }
    
    /**
     * test a json file with xml attributes
     */
    
    public void testJSON2DIDL05() throws Exception{
        String didlxml=createDIDLXML("ex/didl05.js");
        //System.err.println(didlxml);
        assertTrue(didlxml.indexOf("id=\"5\"")!=-1);
    }
    
    /**
     * test base64 inline a URL
     */
     public void testJSON2DIDL06() throws Exception{
        String didlxml=createDIDLXML("ex/didl06.js");
	//        System.err.println(didlxml);
        assertTrue(didlxml.indexOf("base64")!=-1);
    }
     
     
    
    
    public void testRoundtrip() throws Exception{
        Codec codec=new Codec();
	codec.registerBytestreamHandler(new FileBytestreamHandler(testdir));
        for (int i=0;i<=6;i++){
            codec.toJSON(createDIDLXML("ex/didl0"+i+".js"));
        }
    }
   
    
    private String createDIDLXML(String filename) throws Exception{
        FileReader fr=new FileReader(filename);
        char[] buffer=new char[BUFFER_SIZE];
        fr.read(buffer,0,BUFFER_SIZE);
        fr.close();
        Codec codec=new Codec();
	codec.registerBytestreamHandler(new FileBytestreamHandler(testdir));
        String didlxml=codec.toDIDLXML(new String(buffer));
        return didlxml;
    }
    
   
    
}
