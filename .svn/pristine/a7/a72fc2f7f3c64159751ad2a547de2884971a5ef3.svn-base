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

import java.util.*;

public class Performance {
    Hashtable _ht;

    public Performance() {
        _ht = new Hashtable();
    }

    public void start(String operation) {
        Operation op = null;
        if (_ht.get(operation) == null) {
            op = new Operation();
            op.name = operation;
            op.lapse = 0;
            op.count = 0;
            _ht.put(operation, op);
        } else
            op = (Operation) (_ht.get(operation));

        op.starttime = new Date();
    }

    public void end(String operation) {
        Operation op = (Operation) (_ht.get(operation));
        op.endtime = new Date();
        op.lapse = op.lapse + op.endtime.getTime() - op.starttime.getTime();
        op.count++;
    }

    public String output() {
        StringBuffer sb = new StringBuffer("");
        for (Enumeration enums = _ht.keys(); enums.hasMoreElements();) {
            String operation = (String) (enums.nextElement());
            Operation op = (Operation) (_ht.get(operation));
            sb.append(op.toString()).append("\n");
        }
        return sb.toString();
    }
}

class Operation {
    Date starttime;

    Date endtime;

    String name;

    int count;

    long lapse;

    public String toString() {
        StringBuffer sb = new StringBuffer("");
        sb.append("Name:").append(name).append("; Lapse Per Record:").append(
                lapse / (count * 1.0)).append("; Total Lapse:").append(lapse)
                .append("; total records:").append(count);
        return sb.toString();
    }
}
