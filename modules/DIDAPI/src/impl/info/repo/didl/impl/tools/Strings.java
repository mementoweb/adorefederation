/*
 * Strings.java
 *
 * Created on October 14, 2005, 3:37 PM
 *
 * Copyright (c) 2006  The Regents of the University of California and Ghent University
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
 */

package info.repo.didl.impl.tools;

/**
 * String Utilities
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public final class Strings {
	/**
	 * Concatenate String[] with provided delimiter
	 */
     public static final String join(String[] data, String delim) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0 ; i < data.length; i++) {
            buf.append(data[i]);
            if (i + 1 < data.length)
                buf.append(delim);
        }
        return buf.toString();
    }
}
