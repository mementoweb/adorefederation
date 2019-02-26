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

package gov.lanl.xmltape.index.berkeleydbImpl;

import gov.lanl.xmltape.index.IndexItem;

import java.util.ArrayList;
import java.util.Iterator;

import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleOutput;


/**
 * IndexItemBinding.java - implementation of berkeyDB's tuple binding
 * @version IndexItemBinding.java 1157 2004-11-04 18:17:48Z liu_x $
 */

public class IndexItemBinding extends com.sleepycat.bind.tuple.TupleBinding {
    public void objectToEntry(Object obj, TupleOutput to) {
        IndexItem item = (IndexItem) obj;
        to.writeString(item.getIdentifier());
        to.writeString(item.getDatestamp());
        to.writeLong(item.getIndex());
        to.writeLong(item.getOffset());

        if (item.getSetSpecs() == null)
            to.writeInt(0);
        else
            to.writeInt(item.getSetSpecs().size());

        if (item.getSetSpecs() != null) {
            for (Iterator it = item.getSetSpecs().iterator(); it.hasNext();) {
                to.writeString((String) (it.next()));
            }
        }

    }

    public Object entryToObject(TupleInput ti) {
        IndexItem item = new IndexItem();
        item.setIdentifier(ti.readString());
        item.setDatestamp(ti.readString());
        item.setIndex(ti.readLong());
        item.setOffset(ti.readLong());
        ArrayList setSpecs = new ArrayList();
        item.setSetSpecs(setSpecs);
        int count = ti.readInt();
        if (count != 0) {
            try {

                String str;
                for (int i = 0; i < count; i++) {
                    str = ti.readString();
                    setSpecs.add(str);
                }

            } catch (IndexOutOfBoundsException ex) {
                ex.printStackTrace();
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        }
        return item;
    }

}
