package gov.lanl.resource.filesystem.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ResourceTestSuite {
  
    public static Test suite() {

        TestSuite suite = new TestSuite();
        suite.addTestSuite(ResourceWriterTest.class);
        suite.addTestSuite(ResourceReaderTest.class);
        return suite;
    }

    /**
     * Runs the test suite using the textual runner.
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}
