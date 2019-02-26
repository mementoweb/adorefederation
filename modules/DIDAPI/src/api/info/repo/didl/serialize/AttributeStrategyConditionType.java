/*
 * AttributeStrategyConditionType.java
 *
 * Created on October 8, 2005, 10:34 AM
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

package info.repo.didl.serialize;

/**
 * The <code>AttributeStategyConditionType</code> interface is used to construct
 * rules for attribute matching. The matching solution is explained in 
 * <code> DIDLStrategyType </code>
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 * @see DIDLStrategyType
 * @see info.repo.didl.AttributeType
 */

public interface AttributeStrategyConditionType {
    
     /**
     * if the namespace matches to a AttributeType implementation
     * @param namespace namespace of the attributes.
     * @return true if matched, false if not.
     */
    
    public boolean match(String namespace);
    
     /**
     * find implementation content class associating with this condition
     * @return implementation AttributeType class 
     */
    public Class getImplClass();
}
