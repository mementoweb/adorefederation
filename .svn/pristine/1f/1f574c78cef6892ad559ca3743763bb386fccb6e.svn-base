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

import gov.lanl.didlwriter.LANLDIDLException;
import info.repo.didl.ComponentType;
import info.repo.didl.DIDLType;
import info.repo.didl.DescriptorType;
import info.repo.didl.ItemType;

import java.util.ArrayList;
import java.util.List;

import org.adore.didl.content.DII;
import org.adore.didl.content.Diadm;
import org.adore.didl.helper.Helper;

/**
 * Simplified DIDL Item for aDORe usage
 * 
 */
public class AdoreItem {
    private AdoreDIIIdentifier aDII;

    private AdoreDiadm aDiadm;

    private List<AdoreComponent> coms;

    private List<AdoreItem> items;

    /**
     * Creates a new AdoreItem instance
     */
    public AdoreItem() {
        coms = new ArrayList<AdoreComponent>();
        items = new ArrayList<AdoreItem>();
    }

    /**
     * Gets the AdoreDiadm for current AdoreItem
     * 
     * @return an AdoreDiadm instance
     */
    public AdoreDiadm getADiadm() {
        return aDiadm;
    }

    /**
     * Sets the AdoreDiadm for current AdoreItem
     * 
     * @param diadm
     *            an AdoreDiadm instance
     */
    public void setADiadm(AdoreDiadm diadm) {
        aDiadm = diadm;
    }

    /**
     * Gets the AdoreDIIIdentifier for current AdoreItem
     * 
     * @return an AdoreDIIIdentifier instance
     */
    public AdoreDIIIdentifier getADII() {
        return aDII;
    }

    /**
     * Sets the AdoreDIIIdentifier for current AdoreItem
     * 
     * @param adii
     *            an AdoreDIIIdentifier instance
     */
    public void setADII(AdoreDIIIdentifier adii) {
        aDII = adii;
    }

    /**
     * Gets list of AdoreComponent for current AdoreItem
     * 
     * @return a list of AdoreComponent objects
     */
    public List<AdoreComponent> getComs() {
        return coms;
    }

    /**
     * Sets list of AdoreComponent for current AdoreItem
     * 
     * @param coms
     *            a list of AdoreComponent objects
     */
    public void setComs(List<AdoreComponent> coms) {
        this.coms = coms;
    }

    /**
     * Gets list of AdoreItems for current AdoreItem
     * 
     * @return a list of AdoreItem objects
     */
    public List<AdoreItem> getItems() {
        return items;
    }

    /**
     * Sets list of AdoreItems for current AdoreItem
     * 
     * @param items
     *            a list of AdoreItem objects
     */
    public void setItems(List<AdoreItem> items) {
        this.items = items;
    }

    /**
     * Creates an ItemType instance with the provided objects
     * 
     * @param didl
     *            the DIDL instance the component will be added to
     * @return a DIDL ItemType to be added to a DIDL or Item
     * @throws LANLDIDLException
     */
    public ItemType create(DIDLType didl) throws LANLDIDLException {
        ItemType item = Helper.newItem(didl);

        item.addDescriptor(didl.newDescriptor()).addStatement(
                Helper.newXMLStatement(didl, aDII.create()));

        item.addDescriptor(didl.newDescriptor()).addStatement(
                Helper.newXMLStatement(didl, aDiadm.create()));

        for (AdoreItem i : getItems()) {
            item.addItem(i.create(didl));
        }

        for (AdoreComponent c : getComs()) {
            item.addComponent(c.create(didl));
        }
        return item;
    }

    /**
     * Populates an AdoreItem from a DIDL ItemType
     * 
     * @param item
     *            initialized ItemType
     * @throws LANLDIDLException
     */
    public void parse(ItemType item) throws LANLDIDLException {
        for (DescriptorType desc : item.getDescriptors()) {
            Object content = desc.getStatements().get(0).getContent();
            if (Diadm.class.isInstance(content)) {
                aDiadm = new AdoreDiadm();
                aDiadm.parse((Diadm) content);

            }

            else if (DII.class.isInstance(content)) {
                aDII = new AdoreDIIIdentifier();
                aDII.parse((DII) content);
            }
        }

        for (ItemType i : item.getItems()) {
            AdoreItem aItem = new AdoreItem();
            aItem.parse(i);
            getItems().add(aItem);
        }

        for (ComponentType c : item.getComponents()) {
            AdoreComponent aCom = new AdoreComponent();
            aCom.parse(c);
            getComs().add(aCom);
        }

    }
}
