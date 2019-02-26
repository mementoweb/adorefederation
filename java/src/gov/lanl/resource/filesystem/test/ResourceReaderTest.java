package gov.lanl.resource.filesystem.test;

import gov.lanl.resource.ResourceRecord;
import gov.lanl.resource.filesystem.ResourceReader;
import gov.lanl.util.resource.Resource;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import junit.framework.TestCase;

public class ResourceReaderTest extends TestCase {
    protected Properties props;
    protected String recordId;
    protected ResourceReader rr;
    protected String repoId;
    
    protected void setUp() throws Exception {
        props = new Properties();
        props.load(new FileInputStream("/Users/rchute/tmp/resource.properties"));
        recordId= "info:lanl-repo/ds/416e605e-1f83-44f6-90b3-fe0a5179715d";
        repoId = "info:lanl-repo/resourcedb/LOC_f32f0258-0297-47f0-af03-b030e3c0d3bd";
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
