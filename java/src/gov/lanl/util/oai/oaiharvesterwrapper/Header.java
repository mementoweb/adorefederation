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

package gov.lanl.util.oai.oaiharvesterwrapper;

import java.util.ArrayList;
import java.util.Iterator;

public class Header {
    private String identifier;

    private String datestamp;
    
    private boolean isDeleted;

    private ArrayList setSpecs = new ArrayList();

    public Header(ORG.oclc.oai.harvester.verb.Header oheader) {
        identifier = oheader.getIdentifier();
        datestamp = oheader.getDatestamp();
        isDeleted = oheader.isDeleted();
        if (oheader.getSetSpecs() != null)
            for (Iterator it = oheader.getSetSpecs(); it.hasNext();) {
                setSpecs.add(it.next());
            }
    }

    public Header(String identifier, String datestamp, Iterator setSpecs) {
        this.identifier = identifier;
        this.datestamp = datestamp;
        if (setSpecs != null)
            for (; setSpecs.hasNext();) {
                this.setSpecs.add(setSpecs.next());
            }
    }

    /**
     * Get the XML &lt;header&gt;
     * 
     * @return the header as an XML string
     */
    public String getHeaderXML() {

        StringBuffer sb = new StringBuffer();
        
        if (isDeleted)
            sb.append("<header status=\"deleted\">");
        else
            sb.append("<header>");
        
        sb.append("<identifier>").append(identifier).append(
                  "</identifier><datestamp>").append(datestamp).append(
                  "</datestamp>");

        Iterator setSpecs = getSetSpecs();
        if (setSpecs != null) {
            while (setSpecs.hasNext()) {
                sb.append("<setSpec>").append((String) setSpecs.next()).append(
                        "</setSpec>");
            }
        }
        sb.append("</header>");
        return sb.toString();
    }

    /**
     * Get the content of the &lt;identifier&gt; element
     * 
     * @return The header's OAI identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Get the content of the &lt;datestamp&gt; element.
     * 
     * @return the header's datestamp.
     */
    public String getDatestamp() {
        return datestamp;
    }

    /**
     * Has the record been deleted from the source repository
     * 
     * @return boolean indicating whether the record has been deleted
     */
    public boolean isDeleted() {
        return isDeleted;
    }
    
    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    
    /**
     * Get the header's setSpecs.
     * 
     * @return an Iterator containing Strings of setSpec values. (null of none)
     */
    public Iterator getSetSpecs() {
        if (setSpecs.size() > 0)
            return setSpecs.iterator();
        else
            return null;
    }

    public void addSetSpec(String sets) {
        setSpecs.add(sets);
    }

    public void addSetSpec(Sets sets) {
        setSpecs.add(sets.getSetSpec());

    }

    /**
     * Removes all of the elements from setSpecs
     */
    public void emptySet(){
	setSpecs.clear();
    
    }

}
