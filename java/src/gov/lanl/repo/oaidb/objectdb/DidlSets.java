/*
 * Los Alamos National Laboratory
 * Research Library
 * Digital Library Research & Prototyping Team
 * 
 * Copyright (c) 1998-2005 The Regents of the University of California.
 * 
 * Unless otherwise indicated, this information has been authored by an employee 
 * or employees of the University of California, operator of the Los Alamos National
 * Laboratory under Contract No. W-7405-ENG-36 with the U.S. Department of Energy. 
 * The U.S. Government has rights to use, reproduce, and distribute this information. 
 * The public may copy and use this information without charge, provided that this 
 * Notice and any statement of authorship are reproduced on all copies. Neither the 
 * Government nor the University makes any warranty, express or implied, or assumes 
 * any liability or responsibility for the use of this information.
 * 
 */

package gov.lanl.repo.oaidb.objectdb;

import java.util.Iterator;

import gov.lanl.repo.oaidb.OAIPMHSets;

public class DidlSets extends OAIPMHSets {
    public boolean checkSets(Iterator sets) throws Exception {
        while (sets.hasNext()) {
            String set = (String) sets.next();
            if (!set.contains("objectdb")) {
                throw new Exception("set type is not supported by repository");
            }
        }
        return true;
    }

    public Iterator getSets(String meta) throws Exception {
        return null;
    }
}