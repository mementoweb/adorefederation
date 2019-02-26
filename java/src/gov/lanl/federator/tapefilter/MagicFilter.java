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

package gov.lanl.federator.tapefilter;

import java.util.Date;

import gov.lanl.registryclient.RegistryRecord;
import gov.lanl.ockham.iesrdata.IESRCollection;
import gov.lanl.util.DateUtil;
import gov.lanl.util.oai.oaiharvesterwrapper.Sets;

/**
 * A little magic of filtering out good tapes for harvesting <p/>
 * 
 * 
 * @author liu_x
 * 
 */
public class MagicFilter implements FilterInterface {
    private String from;

    private String until;

    private Sets set;

    public MagicFilter() {
        this(null, null, null);
    }

    public MagicFilter(String from, String until, Sets set) {
        this.from = from;
        this.until = until;
        this.set = set;
    }

    public boolean accept(RegistryRecord record) throws ClassCastException {
        if (!(record.getMetaData() instanceof gov.lanl.ockham.iesrdata.IESRCollection)) {
            throw new ClassCastException(
                    "gov.lanl.ockham.iesrdata.IESRCollection");
        }
        IESRCollection coll = (IESRCollection) (record.getMetaData());

        // filter from and/or until parameter
        if (coll.getTypes().contains("xmltape") && until != null) {
            Date t = DateUtil.utc2Date(record.getDatestamp());
            Date u = DateUtil.utc2Date(until);
            if (u.before(t))
                return false;
        }

        if (set != null) {

            // filter set based on baseurl
            if (set.getType() == Sets.BASE_URL) {
                return Sets.decodeSetSpec(set.getValue()).equals(
                        record.getIdentifier()) ? true : false;
            }

            // filter set based on collection
            if (set.getType() == Sets.COLLECTION) {
                return coll.getIsPartOf().contains(
                        Sets.decodeSetSpec(set.getValue())) ? true : false;
            }

            // filter set based on format
            // TODO: we don't have a convention of describing format in IESR
            // registry yet
            if (set.getType() == Sets.FORMAT) {
                return true;
            }
        }

        return true;
    }

}
