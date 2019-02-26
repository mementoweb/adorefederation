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

package gov.lanl.federator;

import java.util.*;

public class Performance {
    Date _date;

    Hashtable _ht;

    public Performance() {
        _ht = new Hashtable();
    }

    public void init() {
        _date = new Date();
    }

    public void tick(String operation) {
        Date newdate = new Date();
        long lapse = newdate.getTime() - _date.getTime();
        if (_ht.get(operation) == null)
            _ht.put(operation, new Long(lapse));
        else {
            Long pre = (Long) _ht.get(operation);
            _ht.put(operation, new Long(pre.longValue() + lapse));
        }

        _date = newdate;
    }

    public String output() {
        StringBuffer sb = new StringBuffer("");
        for (Enumeration en = _ht.keys(); en.hasMoreElements();) {
            String operation = (String) (en.nextElement());
            Long lapse = (Long) (_ht.get(operation));
            sb.append(operation).append(" : ").append(lapse.toString()).append(
                    "\n");
        }
        return sb.toString();
    }
}
