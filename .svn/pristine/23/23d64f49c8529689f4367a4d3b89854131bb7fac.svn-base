package gov.lanl.xmltape.identifier.index.bdbImpl;

import com.sleepycat.je.CheckpointConfig;

public class BDBCleaner {
	public static void main(String[] args) throws Exception {
		BDBEnv env = new BDBEnv(args[0], false);
		for (String i : env.getEnv().getDatabaseNames()) {
			System.out.println(i);
			if (i.equals("identifier_by_record")) {
				env.getEnv().removeDatabase(null, "identifier_by_record");
			}
		}
		if (!env.getEnv().getConfig().getReadOnly()) {
			boolean changes = false;
			while (env.getEnv().cleanLog() > 0) {
				changes = true;
			}
			if (changes) {
				CheckpointConfig force = new CheckpointConfig();
				force.setForce(true);
				env.getEnv().checkpoint(force);
			}
		}
		env.shutDown();
	}

}
