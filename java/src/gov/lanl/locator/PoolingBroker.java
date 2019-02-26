package gov.lanl.locator;

import java.sql.Connection;
import java.util.Map;

public interface PoolingBroker {
	public abstract Map<String, Integer> getRepoIds();
	public abstract Map<Integer, String> getRepoUrls();
	public abstract Map<String, String> getDates(Integer[] ids);
	public abstract Integer getRepoId(String repo_uri);
	public abstract String getRepoUrl(Connection con, Integer[] ids);
	public abstract String[] mapUrls(Integer[] a, Map<String, String> map);
}
