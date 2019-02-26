/*
 * Copyright (c) 2008  Los Alamos National Security, LLC.
 *
 * Los Alamos National Laboratory
 * Research Library
 * Digital Library Research & Prototyping Team
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
 * 
 */

package gov.lanl.adore.djatoka.repo;

import gov.lanl.adore.djatoka.openurl.DjatokaImageMigrator;
import gov.lanl.adore.djatoka.openurl.IReferentMigrator;
import gov.lanl.adore.djatoka.openurl.IReferentResolver;
import gov.lanl.adore.djatoka.openurl.ResolverException;
import gov.lanl.adore.djatoka.util.ImageRecord;
import gov.lanl.identifier.sru.SRUDC;
import gov.lanl.identifier.sru.SRUSearchRetrieveResponse;
import gov.lanl.util.FileUtil;
import gov.lanl.util.resource.Resource;
import info.openurl.oom.entities.Referent;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * AdoreRepoResolver
 * 
 * Property: AdoreRepoResolver.disseminatorURL=http://localhost:8080/adore-disseminator/
 * 
 * @author Ryan Chute
 *
 */
public class AdoreRepoResolver implements IReferentResolver {
	static Logger logger = Logger.getLogger(AdoreRepoResolver.class);
	private static final String MIME_JP2 = "image/jp2";
	private static final String MIME_XML = "application/xml";
	private static final String SVC_RES_BASE_URL = "AdoreRepoResolver.resolverBaseURL";
	private static final String PROP_FILE_ADORE = "AdoreRepoResolver.repoProperties";
	private static LinkedHashMap<String, ImageRecord> imgs = new LinkedHashMap<String, ImageRecord>(16, 0.75f, true);
	private static IReferentMigrator dim = new DjatokaImageMigrator();
	private static RepoResolverProxy proxy;
	private static String resolverBaseURL;
	private static String adorePropFile;
	
	/**
	 * Referent Identifier to be resolved from Identifier Resolver. The returned
	 * ImageRecord need only contain the imageId and image file path.
	 * @param rft identifier of the image to be resolved
	 * @return ImageRecord instance containing resolvable metadata
	 * @throws ResolverException
	 */
	public ImageRecord getImageRecord(Referent rft) throws ResolverException {
        String id = ((URI)  rft.getDescriptors()[0]).toASCIIString();
        return getImageRecord(id);
	}
	
	/**
	 * Referent Identifier to be resolved from Identifier Resolver. The returned
	 * ImageRecord need only contain the imageId and image file path.
	 * @param rftId identifier of the image to be resolved
	 * @return ImageRecord instance containing resolvable metadata
	 * @throws ResolverException
	 */
	public ImageRecord getImageRecord(String rftId) throws ResolverException {
		ImageRecord ir = imgs.get(rftId);
		String rftIdCopy = rftId;
		// Try getMetadata on image, to see if image is available locally
		if (ir == null) {
			try {
				Resource r = proxy.get(resolverBaseURL, rftId, RepoResolverProxy.SVCID_ADORE8_GETMETADATA, true);
				if (r != null && r.getContentType().equals(MIME_XML)) {
					SRUDC dc = new SRUDC();
					SRUSearchRetrieveResponse sru = SRUSearchRetrieveResponse.read(r.getInputStream());
					
					String file = null;
					dc = sru.getRecords().firstElement();
					for (String i : dc.getKeys(SRUDC.Key.IDENTIFIER)) {
						if (i.startsWith("file://"))
							file = new URI(i).getPath();
					}
					// Check if file defined in metadata exists locally
					if (file != null && new File(file).exists()) {
						ir = new ImageRecord(rftId, file);
						imgs.put(rftId, ir);
					} else {
						// Determine the mimetype of resource
						if (dc.getKeys(SRUDC.Key.FORMAT).get(0).equals(MIME_JP2)) {
							// Download resource from repo and cache locally
							r = proxy.get(resolverBaseURL, rftId, RepoResolverProxy.SVCID_ADORE4_GETRESOURCE, true);
							File f = File.createTempFile("repo-cache-", ".jp2");
							f.deleteOnExit();
							file = f.getAbsolutePath();
							FileUtil.serialize(r.getInputStream(), file);
							ir = new ImageRecord(rftId, file);
							imgs.put(rftId, ir);
						} else {
							// Build http resource reference
							rftId = RepoResolverProxy.getURL(resolverBaseURL, rftId, RepoResolverProxy.SVCID_ADORE4_GETRESOURCE, true);
						}
					}
				} 
			} catch (Exception e) {
				logger.error(e,e);
			}
		}
		
		// Must be a remote URI or non-JP2 within the repository
		if (ir == null && isResolvableURI(rftId)) {
			try {
				URI uri = new URI(rftId);
				if (dim.getProcessingList().contains(uri.toString())) {
					int i = 0;
					Thread.sleep(1000);
					while (dim.getProcessingList().contains(uri) && i < (5 * 60)){
						Thread.sleep(1000);
						i++;
					}
					if (imgs.containsKey(rftId))
					    return imgs.get(rftId);
				}
				File f = dim.convert(uri);
				ir = new ImageRecord(rftId, f.getAbsolutePath());
				if (f.length() > 0) {
				    imgs.put(rftId, ir);
				    if (rftIdCopy != null)
				    	imgs.put(rftIdCopy, ir);
				} else
					throw new ResolverException("An error occurred processing file:" + uri.toURL().toString());
			} catch (Exception e) {
				logger.error(e,e);
				throw new ResolverException(e);
			}
		} else if (isResolvableURI(rftId) && !new File(ir.getImageFile()).exists()) {
				// Handle ImageRecord in cache, but file does not exist on the file system
				imgs.remove(rftId);
				return getImageRecord(rftId);
		}
		return ir;
	}
		
	private static boolean isResolvableURI(String rftId) {
		return (rftId.startsWith("http") || rftId.startsWith("file") || rftId.startsWith("ftp"));
	}
	
	/**
	 * Returns list of most recently requested images in accessed order.
	 * @param cnt limit list to top n ImageRecords
	 * @return list of requested image records
	 */
	public ArrayList<ImageRecord> getImageRecordList(int cnt) {
		if (cnt >= imgs.size())
			return new ArrayList<ImageRecord>(imgs.values());
		else {
		    ArrayList<ImageRecord> l = new ArrayList<ImageRecord>();
		    int i = 0;
		    for (ImageRecord rec : imgs.values()) {
		    	if (rec != null && i < cnt) {
		    		l.add(rec);
		    		i++;
		    	} else
		    		return l;
		    }
		}
		return null;
	}

	/**
	 * Sets a Properties object that may be used by underlying implementation
	 * @param props Properties object for use by implementation
	 * @throws ResolverException
	 */
	public void setProperties(Properties props) throws ResolverException {
		try {
			String svcDis = props.getProperty(SVC_RES_BASE_URL);
			if (svcDis != null) {
				resolverBaseURL = svcDis;
				URL url = new URL(resolverBaseURL);
				proxy = new RepoResolverProxy(url);
				imgs = new LinkedHashMap<String, ImageRecord>(16, 0.75f, true);
			} else
				throw new ResolverException(SVC_RES_BASE_URL + " is not defined.");
			String prop = props.getProperty(PROP_FILE_ADORE);
			if (prop != null) {
				URL url = Thread.currentThread().getContextClassLoader().getResource(props.getProperty(PROP_FILE_ADORE));
				adorePropFile = url.getFile();
			} else
				logger.info(PROP_FILE_ADORE + " is not defined, images will only be temporarily cached");
		} catch (Exception e) {
			logger.error(e,e);
			throw new ResolverException(e);
		}
	}

	public IReferentMigrator getReferentMigrator() {
		return dim;
	}
	
	public int getStatus(String rftId) {
		if (imgs.get(rftId) != null)
			return HttpServletResponse.SC_OK;
		else if (dim.getProcessingList().contains(rftId))
			return HttpServletResponse.SC_ACCEPTED;
		else
			return HttpServletResponse.SC_NOT_FOUND;
	}
}
