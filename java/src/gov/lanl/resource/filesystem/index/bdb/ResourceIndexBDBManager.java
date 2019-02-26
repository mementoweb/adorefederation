package gov.lanl.resource.filesystem.index.bdb;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import gov.lanl.resource.ResourceException;
import gov.lanl.resource.ResourceIndexInterface;
import gov.lanl.resource.ResourceRecord;
import gov.lanl.util.resource.Resource;

public class ResourceIndexBDBManager implements ResourceIndexInterface {
	static Logger log = Logger.getLogger(ResourceIndexBDBManager.class.getName());
    private static final String idxFileId = "resolver.idx";
    private static final String LINESEPATATOR = "/";
    private String baseDir;
	private LinkedHashMap<String, BDBIndex> indexCache;
	private int maxRemoteCacheSize = 10;
	
	public ResourceIndexBDBManager() {
		init();
	}
	
	public void close(String repoId) throws ResourceException {
		indexCache.get(repoId).closeDatabases();
		indexCache.get(repoId).close();
	}

	public ResourceRecord getMetadata(String repoId, String recordId)
			throws ResourceException {
		if (!indexCache.containsKey(repoId))
			open(repoId);
		return indexCache.get(repoId).getMetadata(recordId);
	}

	public Resource getResource(String repoId, String recordId)
			throws ResourceException {
		if (!indexCache.containsKey(repoId))
			open(repoId);
		return indexCache.get(repoId).getResource(recordId);
	}

	public InputStream listIdentifiers(String repoId, String from, String until)
			throws ResourceException {
		if (!indexCache.containsKey(repoId))
			open(repoId);
		ByteArrayInputStream bais = null;
		bais = new ByteArrayInputStream(indexCache.get(repoId).listIdentifiers());
		return bais;
	}

	public void open(String repoId) throws ResourceException {
		if (!indexCache.containsKey(repoId)) {
		    String repo = repoId.substring(repoId.lastIndexOf("/") + 1);
            File f = new File(baseDir + LINESEPATATOR + repo, idxFileId);
            if (!f.exists())
            	f.mkdirs();
            BDBEnv env = new BDBEnv(f.getAbsolutePath(),false);
            BDBIndex idx = new BDBIndex();
            idx.setDbEnvironment(env);
            idx.open(false);
            indexCache.put(repoId, idx);
		}
	}

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    public void setProperties(Properties props) {
        // No need to properties in this implementation
    }

	public void writeRecord(String repoId, ResourceRecord record)
			throws ResourceException {
		if (!indexCache.containsKey(repoId))
			open(repoId);
		indexCache.get(repoId).putResourceRecord(record);
	}
	
	private void init() {
		indexCache = new LinkedHashMap<String, BDBIndex>(16, .85f, true) {
			protected boolean removeEldestEntry(Map.Entry<String, BDBIndex> eldest) {
				log.debug("indexCacheSize: " + size());
				boolean d = size() > maxRemoteCacheSize;
				if (d) {
					log.debug("closing: " + eldest.getKey());
					eldest.getValue().close();
					remove(eldest.getKey());
				}
				return false;
			};
		};
	}

}
