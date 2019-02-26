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
package gov.lanl.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * General Date Utility Class
 * @author rchute
 */
public class DateUtil {

    static SimpleDateFormat formatter;
    static SimpleDateFormat simple;
    static {
        formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        simple = new SimpleDateFormat("yyyy-MM-dd");
        TimeZone tz = TimeZone.getTimeZone("UTC");
        formatter.setTimeZone(tz);
        simple.setTimeZone(tz);
    }

    /**
     * Convert a Date object to UTC of format "yyyy-MM-dd'T'HH:mm:ss'Z'"
     * @param date - Date object to be formated as UTC string
     * @return String UTC Date of form yyyy-MM-dd'T'HH:mm:ss'Z
     */
    public static final String date2UTC(Date date) {
        return formatter.format(date);
    }
    
    /**
     * Convert a UTC string of format "yyyy-MM-dd'T'HH:mm:ss'Z' to a Date object"
     * @param utcString - UTC Date of form yyyy-MM-dd'T'HH:mm:ss'Z
     * @return Date object
     */
    public static final Date utc2Date(String utcString) {
        Date utc = null;
        ParseException exception = null;
        try {
            utc = formatter.parse(utcString);
        } catch (ParseException e) {
            exception = e;
        }
        if (utc == null) {
            try {
                utc = simple.parse(utcString);
            } catch (ParseException e) {
                exception = e;
            }
        }
        if (utc == null && exception != null)
            exception.printStackTrace();
        
        return utc;
    }
}
