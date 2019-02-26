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

import org.adore.didl.content.DII;
import org.adore.didl.content.Diadm;
import org.adore.didl.helper.Helper;

import gov.lanl.didlwriter.LANLDIDLException;
import info.repo.didl.DescriptorType;
import info.repo.didl.ComponentType;
import info.repo.didl.ResourceType;
import info.repo.didl.DIDLType;

import java.util.List;
import java.util.ArrayList;

/**
 * Simplified DIDL Component for aDORe usage
 * 
 */
public class AdoreComponent {
    private AdoreDIIIdentifier DIIIdentifier;

    private AdoreDIIRelatedIdentifier DIIRelatedIdentifier;

    private AdoreDiadm aDiadm;

    private List<AdoreResource> resources;

    /**
     * Creates a new AdoreComponent instance
     */
    public AdoreComponent() {
        resources = new ArrayList<AdoreResource>();
    }

    /**
     * Gets the AdoreDiadm for current AdoreComponent
     * 
     * @return an AdoreDiadm instance
     */
    public AdoreDiadm getADiadm() {
        return aDiadm;
    }

    /**
     * Sets the AdoreDiadm for current AdoreComponent
     * 
     * @param diadm
     *            an AdoreDiadm instance
     */
    public void setADiadm(AdoreDiadm diadm) {
        aDiadm = diadm;
    }

    /**
     * Gets the AdoreDIIIdentifier for current AdoreComponent
     * 
     * @return an AdoreDIIIdentifier instance
     */
    public AdoreDIIIdentifier getDIIIdentifier() {
        return DIIIdentifier;
    }

    /**
     * Sets the AdoreDIIIdentifier for current AdoreComponent
     * 
     * @param adii
     *            an AdoreDIIIdentifier instance
     */
    public void setDIIIdentifier(AdoreDIIIdentifier adii) {
        DIIIdentifier = adii;
    }

    /**
     * Gets the AdoreDIIRelatedIdentifier for current AdoreComponent
     * 
     * @return an AdoreDIIRelatedIdentifier instance
     */
    public AdoreDIIRelatedIdentifier getDIIRelatedIdentifier() {
        return DIIRelatedIdentifier;
    }

    /**
     * Sets the AdoreDIIRelatedIdentifier for current AdoreComponent
     * 
     * @param relatedIdentifier
     *            an AdoreDIIRelatedIdentifier instance
     */
    public void setDIIRelatedIdentifier(
            AdoreDIIRelatedIdentifier relatedIdentifier) {
        DIIRelatedIdentifier = relatedIdentifier;
    }

    /**
     * Gets list of AdoreResource for current AdoreComponent
     * 
     * @return a list of AdoreResource objects
     */
    public List<AdoreResource> getResources() {
        return resources;
    }

    /**
     * Sets list of AdoreResource for current AdoreComponent
     * 
     * @param resources
     *            a list of AdoreResource objects
     */
    public void setResources(List<AdoreResource> resources) {
        this.resources = resources;
    }

    /**
     * Creates a ComponentType with the provided objects
     * 
     * @param didl
     *            the DIDL instance the component will be added to
     * @return a DIDL ComponentType to be added to an Item
     * @throws LANLDIDLException
     */
    public ComponentType create(DIDLType didl) throws LANLDIDLException {
        ComponentType com = Helper.newComponent(didl);

        com.addDescriptor(didl.newDescriptor()).addStatement(
                Helper.newXMLStatement(didl, DIIIdentifier.create()));

        if (DIIRelatedIdentifier != null) {
            com.addDescriptor(didl.newDescriptor())
                    .addStatement(
                            Helper.newXMLStatement(didl, DIIRelatedIdentifier
                                    .create()));
        }

        com.addDescriptor(didl.newDescriptor()).addStatement(
                Helper.newXMLStatement(didl, aDiadm.create()));

        for (AdoreResource res : resources) {
            com.addResource(res.create(didl));
        }
        return com;
    }

    /**
     * Populates an AdoreComponent from a DIDL ComponentType
     * 
     * @param com
     *            initialized ComponentType
     * @throws LANLDIDLException
     */
    public void parse(ComponentType com) throws LANLDIDLException {
        for (DescriptorType desc : com.getDescriptors()) {
            Object content = desc.getStatements().get(0).getContent();
            if (Diadm.class.isInstance(content)) {
                aDiadm = new AdoreDiadm();
                aDiadm.parse((Diadm) content);
            } else if (DII.class.isInstance(content)) {
                if (((DII) content).getType() == DII.IDENTIFIER) {
                    DIIIdentifier = new AdoreDIIIdentifier();
                    DIIIdentifier.parse((DII) content);
                } else if (((DII) content).getType() == DII.RELATED_IDENTIFIER) {
                    DIIRelatedIdentifier = new AdoreDIIRelatedIdentifier();
                    DIIRelatedIdentifier.parse((DII) content);
                }
            }
        }
        for (ResourceType r : com.getResources()) {
            AdoreResource aResource = new AdoreResource();
            aResource.parse(r);
            getResources().add(aResource);
        }
    }
}
