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

package gov.lanl.ingest.oaitape;

public class IngestRecord implements Comparable {

    private String ref = null;
    private String derefXPath = null;
    private String sourceURI = null;
    private String digest = null;
    private String localIdentifier = null;
    
    private boolean success = false;
    private String message;
    
    /**
     * @return Returns the derefXPath.
     */
    public String getDerefXPath() {
        return derefXPath;
    }
    /**
     * @param derefXPath The derefXPath to set.
     */
    public void setDerefXPath(String derefXPath) {
        this.derefXPath = derefXPath;
    }
    /**
     * @return Returns the digest.
     */
    public String getDigest() {
        return digest;
    }
    /**
     * @param digest The digest to set.
     */
    public void setDigest(String digest) {
        this.digest = digest;
    }
    /**
     * @return Returns the localIdentifier.
     */
    public String getLocalIdentifier() {
        return localIdentifier;
    }
    /**
     * @param localIdentifier The localIdentifier to set.
     */
    public void setLocalIdentifier(String localIdentifier) {
        this.localIdentifier = localIdentifier;
    }
    /**
     * @return Returns the ref.
     */
    public String getRef() {
        return ref;
    }
    /**
     * @param ref The ref to set.
     */
    public void setRef(String ref) {
        this.ref = ref;
    }
    /**
     * @return Returns the sourceURI.
     */
    public String getSourceURI() {
        return sourceURI;
    }
    /**
     * @param sourceURI The sourceURI to set.
     */
    public void setSourceURI(String sourceURI) {
        this.sourceURI = sourceURI;
    }
    
    public String[] toArray() {
        String[] log = new String[5];
        log[0] = checkNulls(ref);
        log[1] = checkNulls(derefXPath);
        log[2] = checkNulls(sourceURI);
        log[3] = checkNulls(digest);
        log[4] = checkNulls(localIdentifier);
        return log;
    }
    
    private static String checkNulls(String n) {
        if (n == null)
            n = "";
        return n;
    }
    
    public int compareTo(Object arg0) {
        int EQUALS = 0;
        int NOT_EQUAL = -1;
        IngestRecord rec = (IngestRecord) arg0;
        if (this.getLocalIdentifier().equals(rec))
                return EQUALS;
        else 
                return NOT_EQUAL;
    }
    /**
     * @return Returns the message.
     */
    public String getMessage() {
        return message;
    }
    /**
     * @param message The message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * @return Returns the success.
     */
    public String isSuccess() {
        if (success)
            return "success";
        else
            return "failure";
    }
    /**
     * @param success The success to set.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
