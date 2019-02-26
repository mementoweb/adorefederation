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

package gov.lanl.util.misc;

import java.util.logging.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class MyLogFormatter extends java.util.logging.Formatter {
    static SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd:HHmmss");

    // This method is called for every log records
    public String format(LogRecord rec) {
        StringBuffer buf = new StringBuffer(1000);
        buf.append(formatter.format(new Date(rec.getMillis())));
        buf.append('\t');
        //  buf.append(rec.getThreadID()).append('\t');
        buf.append(rec.getMessage());
        buf.append('\n');
        return buf.toString();
    }

}
