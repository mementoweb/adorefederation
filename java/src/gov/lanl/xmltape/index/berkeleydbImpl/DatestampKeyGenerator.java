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

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.SecondaryDatabase;
import com.sleepycat.je.SecondaryKeyCreator;

/**
 * $Id: DatestampKeyGenerator.java 1240 2004-11-30 22:51:03Z liu_x $
 */

public class DatestampKeyGenerator implements SecondaryKeyCreator {

    private String setSpec;

    private TupleBinding dataBinding;

    public DatestampKeyGenerator(String setSpec, TupleBinding dataBinding) {
        this.setSpec = setSpec;
        this.dataBinding = dataBinding;
    }

    public boolean createSecondaryKey(SecondaryDatabase secondaryDb,
            DatabaseEntry keyEntry, DatabaseEntry dataEntry,
            DatabaseEntry resultEntry) throws DatabaseException {

        /*
         * Convert the data entry to an IndexItem object, extract the secondary
         * key value from it, and then convert it to the resulting secondary key
         * entry.
         */
        try {
            IndexItem data = (IndexItem) dataBinding.entryToObject(dataEntry);
            ArrayList al = data.getSetSpecs();

            /**
             * we index the date by its long integer fromat
             */

            if (data.getDatestamp() == null)
                return false;

            byte[] datestamp = UTCTimeFormatter.parse(data.getDatestamp());
            if (setSpec == null) {
                resultEntry.setData(datestamp);
                return true;
            }

            if (al.contains(setSpec)) {
                resultEntry.setData(datestamp);
                return true;
            } else {

                /*
                 * if no match then return false to prevent it from being
                 * indexed. Note that if a required key is missing or an error
                 * occurs, an exception should be thrown by this method.
                 */
                return false;
            }
        } catch (Exception ex) {
            throw new DatabaseException(ex);
        }
    }

}
