package gov.lanl.resource.filesystem.test;

import gov.lanl.resource.ResourceRecord;
import gov.lanl.resource.filesystem.ResourceReader;
import gov.lanl.util.resource.Resource;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import junit.framework.TestCase;

public class BDBReaderTest extends TestCase {
    protected Properties props;
    protected String recordId;
    protected ResourceReader rr;
    protected String repoId;
    
    protected void setUp() throws Exception {
        props = new Properties();
        props.load(new FileInputStream("/Users/rchute/tmp/resource.properties"));
        //recordId= "info:lanl-repo/ds/2RTVVFHAAAVRYNOQEHUGP7TYSYHXKMHH";
        recordId="info:lanl-repo/ds/2XRYVAHS7UONLQ4RIZYNXZ5TBND2HGGK";
        repoId = "info:lanl-repo/resourcedb/repo_test1234";
        rr = new ResourceReader(props);
    }
    
    public void testGetResource() throws Exception {
        Resource r = rr.getResource(repoId, recordId);
        InputStream is = r.getInputStream();
        System.out.println(r.getContentType());
        assertTrue(is != null);
    }
    
    public void testGetMetadata() throws Exception {
        ResourceRecord r = rr.getMetadata(repoId, recordId);
        assertTrue(r != null);
    }
    
    public void testListIdentifiers_All() throws Exception {
        InputStream is = rr.listIdentifiers(repoId, null, null);
        assertTrue(is != null);
    }
}
