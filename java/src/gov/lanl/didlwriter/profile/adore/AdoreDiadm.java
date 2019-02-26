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

package gov.lanl.didlwriter.profile.adore;

import org.adore.didl.content.DC;
import org.adore.didl.content.DCTerms;
import org.adore.didl.content.Diadm;

import java.util.List;
import java.util.ArrayList;

/**
 * Simplified DIADM Interface for aDORe usage
 * 
 */
public class AdoreDiadm {
    /** Field: dc:format */
    private String format;

    /** Field: dc:creator */
    private String creator;

    /** Field: dc:type | One item may have multiple semantics */
    private List<String> semantic;

    /** Field: dc:right */
    private String rights;

    /** Field: dcterms:created */
    private String created;

    /** Field: dcterms:isPartOf */
    private String isPartOf;

    /** Field: dcterms:isFormatOf */
    private String isFormatOf;

    /**
     * Creates a new AdoreDiadm instance
     */
    public AdoreDiadm() {
        semantic = new ArrayList<String>();
    }

    /**
     * Gets the format value (e.g. dc:format)
     * 
     * @return value to be rendered as dc:format
     */
    public String getFormat() {
        return format;
    }

    /**
     * Sets the format value (e.g. dc:format)
     * 
     * @param format
     *            value to be rendered as dc:format
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * Gets the date created value (e.g. dcterms:created)
     * 
     * @return value to be rendered as dcterms:created
     */
    public String getCreated() {
        return created;
    }

    /**
     * Sets the date created value (e.g. dcterms:created)
     * 
     * @param created
     *            value to be rendered as dcterms:created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * Gets the creator value (e.g. dc:creator)
     * 
     * @return value to be rendered as dc:creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Sets the creator value (e.g. dc:creator)
     * 
     * @param creator
     *            value to be rendered as dc:creator
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * Sets the rights value (e.g. dc:right)
     * 
     * @return value to be rendered as dc:right
     */
    public String getRights() {
        return rights;
    }

    /**
     * Sets the rights value (e.g. dc:right)
     * 
     * @param rights
     *            value to be rendered as dc:right
     */
    public void setRights(String rights) {
        this.rights = rights;
    }

    /**
     * Gets the list of semantic values (e.g. dc:type)
     * 
     * @return values to be rendered as dc:type
     */
    public List<String> getSemantic() {
        return semantic;
    }

    /**
     * Sets the list of semantic values (e.g. dc:type)
     * 
     * @param semantic
     *            values to be rendered as dc:type
     */
    public void setSemantic(List<String> semantic) {
        this.semantic = semantic;
    }

    /**
     * Gets the isFormatOf value
     * 
     * @return value to be rendered as dcterms:isFormatOf
     */
    public String getIsFormatOf() {
        return isFormatOf;
    }

    /**
     * Sets the isFormatOf value
     * 
     * @param isFormatOf
     *            value to be rendered as dcterms:isFormatOf
     */
    public void setIsFormatOf(String isFormatOf) {
        this.isFormatOf = isFormatOf;
    }

    /**
     * Gets the isPartOf value
     * 
     * @return value to be rendered as dcterms:isPartOf
     */
    public String getIsPartOf() {
        return isPartOf;
    }

    /**
     * Sets the isPartOf value
     * 
     * @param isPartOf
     *            value to be rendered as dcterms:isPartOf
     */
    public void setIsPartOf(String isPartOf) {
        this.isPartOf = isPartOf;
    }

    /**
     * Creates a Diadm instance from the set values
     * 
     * @return initialized Diadm instance
     */
    public Diadm create() {
        Diadm diadm = new Diadm();
        if (format != null) {
            diadm.addDC(new DC(DC.Key.FORMAT, format));
        }
        if (rights != null) {
            diadm.addDC(new DC(DC.Key.RIGHTS, rights));
        }

        if (creator != null) {
            diadm.addDC(new DC(DC.Key.CREATOR, creator));
        }

        for (String s : semantic) {
            diadm.addDC(new DC(DC.Key.TYPE, s));
        }

        if (created != null) {
            diadm.addDCTerms(new DCTerms(DCTerms.Key.CREATED, created));
        }

        if (isPartOf != null) {
            diadm.addDCTerms(new DCTerms(DCTerms.Key.IS_PART_OF, isPartOf));
        }

        if (isFormatOf != null) {
            diadm.addDCTerms(new DCTerms(DCTerms.Key.IS_FORMAT_OF, isFormatOf));
        }
        return diadm;

    }

    /**
     * Populates AdoreDiadm from a Diadm instance
     * 
     * @param diadm
     *            initialized Diadm instance
     */
    public void parse(Diadm diadm) {
        for (DC dc : diadm.getDC()) {
            if (dc.getKey().equals(DC.Key.FORMAT))
                setFormat(dc.getValue());
            else if (dc.getKey().equals(DC.Key.CREATOR)) {
                this.setCreated(dc.getValue());
            } else if (dc.getKey().equals(DC.Key.RIGHTS)) {
                this.setRights(dc.getValue());
            } else if (dc.getKey().equals(DC.Key.TYPE)) {
                this.semantic.add(dc.getValue());
            }
        }

        for (DCTerms dcterms : diadm.getDCTerms()) {
            if (dcterms.getKey().equals(DCTerms.Key.IS_FORMAT_OF))
                this.setIsFormatOf(dcterms.getValue());
            else if (dcterms.getKey().equals(DCTerms.Key.IS_PART_OF))
                this.setIsPartOf(dcterms.getValue());
            else if (dcterms.getKey().equals(DCTerms.Key.CREATED))
                this.setCreated(dcterms.getValue());
        }

    }

    /**
     * AdoreDiadm Hashcode
     */
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((created == null) ? 0 : created.hashCode());
        result = PRIME * result + ((creator == null) ? 0 : creator.hashCode());
        result = PRIME * result + ((format == null) ? 0 : format.hashCode());
        result = PRIME * result
                + ((isFormatOf == null) ? 0 : isFormatOf.hashCode());
        result = PRIME * result
                + ((isPartOf == null) ? 0 : isPartOf.hashCode());
        result = PRIME * result + ((rights == null) ? 0 : rights.hashCode());
        result = PRIME * result
                + ((semantic == null) ? 0 : semantic.hashCode());
        return result;
    }

    /**
     * AdoreDiadm Equals
     */
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final AdoreDiadm other = (AdoreDiadm) obj;
        if (created == null) {
            if (other.created != null)
                return false;
        } else if (!created.equals(other.created))
            return false;
        if (creator == null) {
            if (other.creator != null)
                return false;
        } else if (!creator.equals(other.creator))
            return false;
        if (format == null) {
            if (other.format != null)
                return false;
        } else if (!format.equals(other.format))
            return false;
        if (isFormatOf == null) {
            if (other.isFormatOf != null)
                return false;
        } else if (!isFormatOf.equals(other.isFormatOf))
            return false;
        if (isPartOf == null) {
            if (other.isPartOf != null)
                return false;
        } else if (!isPartOf.equals(other.isPartOf))
            return false;
        if (rights == null) {
            if (other.rights != null)
                return false;
        } else if (!rights.equals(other.rights))
            return false;
        if (semantic == null) {
            if (other.semantic != null)
                return false;
        } else if (!semantic.equals(other.semantic))
            return false;
        return true;
    }

}
