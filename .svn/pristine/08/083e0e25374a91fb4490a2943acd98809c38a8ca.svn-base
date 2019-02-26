package gov.lanl.resource.filesystem.test;

import gov.lanl.arc.heritrixImpl.ARCFileReader;
import gov.lanl.arc.heritrixImpl.CDXRecord;
import gov.lanl.resource.filesystem.ResourceWriter;
import gov.lanl.util.DigestUtil;
import gov.lanl.util.FileUtil;
import gov.lanl.util.HttpDate;
import gov.lanl.util.StreamUtil;
import gov.lanl.util.uuid.UUIDFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import junit.framework.TestCase;

public class ResourceWriterTest extends TestCase {
    protected Properties props;
    protected String arcFile;
    protected ResourceWriter rw;
    protected String repoId;
    protected String sourceUri;
    protected String append;
    
    protected void setUp() throws Exception {
        props = new Properties();
        props.load(new FileInputStream("/Users/rchute/tmp/resource.properties"));
        arcFile= "/Users/rchute/tmp/LOC/LOC_f32f0258-0297-47f0-af03-b030e3c0d3bd.arc";
        repoId = "info:lanl-repo/resourcedb/LOC_f32f0258-0297-47f0-af03-b030e3c0d3bd";
        append = "/Users/rchute/Desktop/imagetest/00001.jp2";
        sourceUri = "http://test/";
        rw = new ResourceWriter(props);
    }
    
//    public void testWrite() throws Exception {
//        String id = null, mimeType = null, utcDate = null, recordLength = null, checksum = null;
//        ARCFileReader reader = new ARCFileReader(arcFile);
//        int t = 0;
//        int u = 1;
//        long time = System.currentTimeMillis();
//        rw.open(repoId);
//        for (CDXRecord i : reader.getCdxInstance().getCDXRecords()) {
//            id = i.getUrl();
//            mimeType = i.getMimetype();
//            utcDate = i.getDate();
//            recordLength = i.getLength();
//            checksum = i.getChecksum();
//            byte[] r = reader.getDataStreamUsingID(id).toByteArray();
//            InputStream bis = new ByteArrayInputStream(r);
//            rw.write(id, mimeType, bis, utcDate, recordLength, checksum, repoId, sourceUri);
//            if (u == 100) {
//                t = u + t;
//                System.out.println(t + " : " +  (System.currentTimeMillis() - time));
//                u = 0;
//            } 
//            u++;
//        }
//        rw.close(repoId);
//    }
    
    public void testWriteAppend() throws Exception {
    	rw.open(repoId);
    	String id = "info:lanl-repo/ds/" + UUIDFactory.generateUUID().getNudeId();
    	String mimeType = "image/jp2";
		Date utcDate = new Date();
		byte[] b = FileUtil.getBytesFromFile(new File(append));
    	String recordLength = Integer.toString(b.length);
		String imageDigest = DigestUtil.getSHA1Digest(b);
		InputStream is = new ByteArrayInputStream(b);
		rw.write(id, mimeType, is, utcDate, recordLength, imageDigest, repoId, append);
		rw.close(repoId);
    }
}
