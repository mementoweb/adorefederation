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

package gov.lanl.xmltape.create;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Generic Tape Creation Utilities
 *
 */
public class TapeCreateUtilities {
    
    /**
     * Simple date normalization tool
     */ 
    public static String normalizeDate(String date) throws ParseException {
        SimpleDateFormat formatter;
        Date sourceDate = null;

        if (date.length() == 25) {
            formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            date = date.substring(0, 19)+"GMT" + date.substring(19);
            sourceDate = formatter.parse(date);
        }
        else if (date.length() == 24) {
            formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            sourceDate = formatter.parse(date);
        }
        else if (date.length() == 19) {
            formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            sourceDate = formatter.parse(date);
        }
        else if (date.length() == 10) {
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            sourceDate = formatter.parse(date);
        }
        else if (date.length() == 8) {
            formatter = new SimpleDateFormat("yyyyMMdd");
            sourceDate = formatter.parse(date);
        }
        else if (date.length() == 6) {
            formatter = new SimpleDateFormat("yyMMdd");
            sourceDate = formatter.parse(date);
        }
        else {
            formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            sourceDate = formatter.parse(date);
            // Since the date is valid UTC, just return the original date
            return date;
        }

        formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        TimeZone tz = TimeZone.getTimeZone("UTC");
        formatter.setTimeZone(tz);
        return formatter.format(sourceDate);
    }
}
