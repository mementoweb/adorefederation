package gov.lanl.xmltape.identifier.openurl;

import java.util.Properties;

public interface IdentifierResolverIface {

	public void setProperties(Properties props);
	
	public abstract byte[] getRecord(String referent, String resolver)
			throws ResolverException;

	public abstract byte[] getRecordsList(String referent, String resolver)
			throws ResolverException;

	public abstract byte[] listIdentifiers(String resolver)
			throws ResolverException;

}