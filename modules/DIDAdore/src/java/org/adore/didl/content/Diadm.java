/*
 * Diadm.java
 *
 * Created on November 28, 2005, 10:06 AM
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

package org.adore.didl.content;

import org.adore.didl.content.DC;
import org.adore.didl.content.DCTerms;

import java.util.ArrayList;

/**
 * Helper class that represents an element of the Diadm namespace
 * This class uses a DOMList to provide a DOM style API
 *
 * @author Xiaoming Liu
 * @author Patrick Hochstenbach
 * @author Kjell Lotigiers
 */
public class Diadm {
    private ArrayList<DC> dcList;
    private ArrayList<DCTerms> dctermsList;
    
    /** Creates a new Diadm instance */
    public Diadm(){
        dcList=new ArrayList<DC>();
        dctermsList=new ArrayList<DCTerms>();
    }
    
    /**
     * Returns a list of DC elements contained in this Diadm element.
     * @return A List of DC elements
     */
    public java.util.List<DC> getDC(){
        return dcList;
    };
    
    /**
     * Adds a DC instance to list
     * @param dc a DC object instance
     */
    public void addDC(DC dc){
        dcList.add(dc);
        
    };
    
    /**
     * Returns a list of DCTerm elements contained in this Diadm element.
     * @return A List of DCTerm elements
     */
    public java.util.List<DCTerms> getDCTerms(){
        return dctermsList;
    };
    
    /**
     * Adds a DCTerms instance to list
     * @param term a DCTerm object instance
     */
    public void addDCTerms(DCTerms term){
        dctermsList.add(term);
        
    };
    
    
    
    
}
