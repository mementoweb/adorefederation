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

package gov.lanl.resource.filesystem.index.bdb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.DatabaseEntry;

/**
 * UTCTimeFormatter.java 1411 2005-02-22 23:55:27Z liu_x $
 * - Utility class to generate byte from datestamp
 */

public class UTCTimeFormatter {
    public static byte[] parse(String datestamp) throws ParseException {
        SimpleDateFormat formatter;
        if (datestamp.length() == 10)
            formatter = new SimpleDateFormat("yyyy-MM-dd");
        else
            formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        TimeZone tz = TimeZone.getTimeZone("UTC");
        formatter.setTimeZone(tz);
        long date = (formatter.parse(datestamp)).getTime();
        DatabaseEntry tmp = new DatabaseEntry();
        TupleBinding.getPrimitiveBinding(Long.class).objectToEntry(
                new Long(date), tmp);
        return tmp.getData();
    }
}
