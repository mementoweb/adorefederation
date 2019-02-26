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

package gov.lanl.xmltape.oai;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.apache.log4j.Logger;

/**
 * SetsList.java 1239 2004-11-30 22:28:02Z liu_x $
 * 
 * Maintain list of sets, for either ListSets purpose, and Set specification in
 * Record header Notice OAI-PMH section 2.6 specifies: ListIdentifiers,
 * ListRecords and GetRecord requests. The list of setSpec should include only
 * the minimum number of setSpec required to specify the set membership. and in
 * ListSets response we must generate the complete set structure. So we must be
 * able to output sets in two different format.
 */
public class SetsList {
    /**
     * The input string takes the output format of ListSets, e.g. <set>
     * <setSpec></setSpec> <setName></setName> </set> <set>... </set>
     */

    HashSet allsets;

    static Logger log = Logger.getLogger(SetsList.class.getName());

    public SetsList(String input)
            throws gov.lanl.xmltape.oai.SetformatException {
        allsets = new HashSet();
        Pattern p = Pattern.compile("<setSpec>[^>]*</setSpec>");
        Matcher m = p.matcher(input);
        for (; m.find();) {
            String setspecxml = input.substring(m.start(), m.end());
            String setspec = setspecxml.substring(9, setspecxml.length() - 10);
            allsets.add(new SetSpec(setspec));
            log.info("setslist add " + setspec);
        }
    }

    public SetsList() {
        allsets = new HashSet();
    }

    public boolean addSetSpec(String setspec)
            throws gov.lanl.xmltape.oai.SetformatException {
        if (allsets.contains(setspec))
            return false;
        else {
            allsets.add(new SetSpec(setspec));
            return true;
        }

    }

    /**
     * generate a list which includes string in the format of <set><setSpec>
     * </setSpec> <setName></setName> </set> This format is required by
     * TapeOAICatelog
     */
    public List getXMLList() {
        ArrayList list = new ArrayList();
        for (Iterator it = allsets.iterator(); it.hasNext();) {
            list.add(((SetSpec) (it.next())).getxml());
        }
        return list;
    }

    /**
     * generate a list which includes string in the format of plain setSpec
     */

    public List getSpecList() {
        ArrayList list = new ArrayList();
        for (Iterator it = allsets.iterator(); it.hasNext();) {
            String str = ((SetSpec) (it.next())).getSetSpec();
            if (str.indexOf(":") != -1)
                list.add(str);
        }
        return list;
    }

}
