/*
 * Copyright (c) 2007  Los Alamos National Security, LLC.
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

package gov.lanl.resource.filesystem.index;

import gov.lanl.resource.ResourceException;
import gov.lanl.resource.ResourceIndexInterface;
import gov.lanl.resource.ResourceRecord;
import gov.lanl.util.DateUtil;
import gov.lanl.util.csv.CSVReader;
import gov.lanl.util.csv.CSVWriter;
import gov.lanl.util.resource.Resource;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

public class FilesystemBasedIndex implements ResourceIndexInterface {
    private static final String idxFileId = "resolver.idx";
    private static final String LINESEPATATOR = "/";
    private String baseDir;
    private HashMap<String, CSVWriter> csvMap = new HashMap<String, CSVWriter>();
    
    public Resource getResource(String repoId, String recordId) throws ResourceException {
    	BufferedReader idxReader;
    	try {
    		String repo = repoId.substring(repoId.lastIndexOf("/") + 1);
            File f = new File(baseDir + LINESEPATATOR + repo, idxFileId);
            idxReader = new BufferedReader(new FileReader(f));
        } catch (Exception e) {
            throw new ResourceException("Error attempting to access resource directory", e);
        }
        // Parse the index file
        CSVReader reader = new CSVReader();
        InputStream is = null;
        String row = null;
        ArrayList<?> fields;
		Resource r = new Resource();
        try {
            for (int line = 0; true; line++) {
                row = idxReader.readLine();
                if (row == null)
                    break;
                if (line != 0) {
                    reader.parse(row);
                    fields = reader.list;
                    if (recordId.equals(((String) fields.get(1)))) {
						String resourceUri = ((String) fields.get(5));
						// Check to see if were working a true URI
						if (resourceUri.startsWith("file://")) {
							try {
								r.setContentType((String) fields.get(2));
								resourceUri = new URI(resourceUri).getPath();
							} catch (URISyntaxException e) {
								throw new ResourceException(
										"Specified resource does not exist: " + resourceUri);
							}
						}
						is = new BufferedInputStream(new FileInputStream(resourceUri));
						r.setInputStream(is);
						break;
					}
                }
            }
        } catch (IOException e) {
            throw new ResourceException("Error processing index file: ", e);
        }
        return r;
    }

    public InputStream listIdentifiers(String repoId, String from, String until) throws ResourceException {
        BufferedReader idxReader;
        Date fromDate = null;
        Date untilDate = null;
        if (from != null)
            fromDate = DateUtil.utc2Date(from);
        if (until != null)
            untilDate= DateUtil.utc2Date(until);
        Date datestamp;
        String identifier;
        StringBuffer ids = new StringBuffer();

        try {
        	String repo = repoId.substring(repoId.lastIndexOf("/") + 1);
            File f = new File(baseDir + LINESEPATATOR + repo, idxFileId);
			CSVReader reader = new CSVReader();
			String row = null;
			ArrayList fields;
			idxReader = new BufferedReader(new FileReader(f));
			try {
				for (int line = 0; true; line++) {
					row = idxReader.readLine();
					if (row == null)
						break;
					if (line == 0)
						continue;
					reader.parse(row);
					fields = reader.list;
					identifier = ((String) fields.get(1));
					if (fromDate == null && untilDate == null) {
						ids.append(identifier);
						ids.append("\n");
					} else {
						datestamp = DateUtil.utc2Date((String) fields.get(0));
						boolean fb = false;
						boolean ub = false;
						if (fromDate != null
								&& (datestamp.after(fromDate) || datestamp
										.equals(fromDate)))
							fb = true;
						if (untilDate != null
								&& (datestamp.before(untilDate) || datestamp
										.equals(untilDate)))
							ub = true;
						if (fb && ub) {
							ids.append(identifier);
							ids.append("\n");
						}
					}
				}
			} catch (IOException e) {
				throw new ResourceException("Error processing index file", e);
			}
		} catch (Exception e) {
			throw new ResourceException("Error attempting to list identifiers", e);
		}
        return new BufferedInputStream(new ByteArrayInputStream(ids.toString().getBytes()));
    }

    public void writeRecord(String repoId, ResourceRecord record) throws ResourceException {
    	CSVWriter csv = csvMap.get(repoId);
    	if (csv == null)
    		open(repoId);
        csv.write(record.getDate());
        csv.write(record.getIdentifier());
        csv.write(record.getMimetype());
        csv.write(record.getChecksum());
        csv.write(record.getLength());
        csv.write(record.getResourceUri());
        csv.write(record.getRepositoryId());
        csv.write(record.getSourceUri());
        csv.writeln();
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    public void setProperties(Properties props) {
        // No need to properties in this implementation
    }

	public void close(String repoId) {
		csvMap.get(repoId).close();
	}

	public void open(String repoId) throws ResourceException {
        File r;
        PrintWriter pw;
        boolean append = false;
        try {
			String repo = repoId.substring(repoId.lastIndexOf("/") + 1);
			File dir = new File(baseDir + LINESEPATATOR + repo);
			if (!dir.exists())
				dir.mkdir();
			r = new File(dir, idxFileId);
			if (!r.exists())
                pw = new PrintWriter(new FileWriter(r));
			else  {
				pw = new PrintWriter(new FileWriter(r,true));
				append = true;
			}
        } catch(Exception e) {
            throw new ResourceException("Error creating index file for: " + baseDir);
        }
        CSVWriter csv = new CSVWriter(pw, false, ',', System.getProperty("line.separator"));
        if (!append)
    	    csv.writeCommentln("IDX: datestamp, identifier, mimetype, checksum, length, resourceUri, repoId");
    	csvMap.put(repoId, csv);
	}

	public ResourceRecord getMetadata(String repoId, String recordId) throws ResourceException {
	   	BufferedReader idxReader;
    	try {
    		String repo = repoId.substring(repoId.lastIndexOf("/") + 1);
            File f = new File(baseDir + LINESEPATATOR + repo, idxFileId);
            idxReader = new BufferedReader(new FileReader(f));
        } catch (Exception e) {
            throw new ResourceException("Error attempting to access resource directory", e);
        }
        // Parse the index file
        CSVReader reader = new CSVReader();
        InputStream is = null;
        String row = null;
        ArrayList<?> fields;
        ResourceRecord r = null;
        try {
            for (int line = 0; true; line++) {
                row = idxReader.readLine();
                if (row == null)
                    break;
                if (line != 0) {
                    reader.parse(row);
                    fields = reader.list;
                    if (recordId.equals(((String) fields.get(1)))) {
                    	r = new ResourceRecord();
                    	r.setDate((String) fields.get(0));
                    	r.setIdentifier((String) fields.get(1));
                    	r.setMimetype((String) fields.get(2));
                    	r.setChecksum((String) fields.get(3));
                    	r.setLength((String) fields.get(4));
                    	r.setResourceUri((String) fields.get(5));
                    	r.setRepositoryId((String) fields.get(6));
                    	if (fields.size() == 7)
                    	    r.setSourceUri((String) fields.get(7));
						break;
					}
                }
            }
        } catch (IOException e) {
            throw new ResourceException("Error processing index file", e);
        }
        
        return r;
	}
}
