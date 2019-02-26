/*
 * DIDLStrategyType.java
 *
 * Created on January 10, 2006, 9:38 AM
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

import info.repo.didl.AttributeType;

/**
 * The <code>DIDLStrategyType</code> finds implementation class for Content or 
 * Attribute, although useful for other cases, it is primarily used by
 * deserialization process. 
 * 
 *<p>
 * DIDL specification allows anyAttributes for many elements, and also allows 
 * AnyData inside DIDLInfo, Statement, or Resource. When deserializer processes 
 * these open-ended data type, a deserializer needs to decide which class the data
 * is mapping to, and once find the class, the deserializer can construct a right
 * <code>Object</code>.
 *<p>
 * During deserialization process, for anyAttributes, the common available 
 * information is the namespace; for anyData, the common available information 
 * includes attributes, mimeType, and namespace. DIDLStrategyType uses these 
 * information to derive appropriate attributes or contents type.
 * <p>
 * 
 * The implementor of <code>DIDLStrategyType</code> need to decide the matching 
 * algorithm. A possible approach is looping through all registered StategyCondition,
 * and the first match wins.
 *
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @see AttributeStrategyConditionType
 * @see ContentStrategyConditionType
 * @see DIDLRegistryType
 */

//TODO: This class still needs improvements in next release:
//      (a) perhaps an interface for exposing rules inside
//      (b) the interface of getContentImplementation(...) is duplicating 
//             StategyCondition.match(...) method.
//
public interface DIDLStrategyType {
    /**
     * register a content strategy condition to be used in the matching
     * @param condition a content condition defining a rule for matching
     */
    public void addContentStrategy(ContentStrategyConditionType condition);
    
     /**
     * register an attribute strategy condition to be used in the matching
     * @param condition an attribute condition defining a rule for matching
     */
    public void addAttributeStrategy(AttributeStrategyConditionType condition);
    
    /**
     * find best matching content class from query criteria
     * @param attributes attributes associating with the contentwrapper, a 
     *        contentwrapper can be Statement, Resource, or DIDLInfo. A null value
     *       can be passed if no attributes availab.e
     * @param mimeType mimeType attribute of the contentWrapper, it's only applicale
     *        to Statement and Resource. A null value can be passed for DIDLInfo
     *
     * @param namespace namespace of the inline content.
     *
     * @return the matching ContentClass, or null if no matching  
     **/
    
    public Class getContentImplementation(AttributeType attributes, String mimeType, String namespace);
    
    
     /**
     * find best matching attribute class from query criteria
     * @param namespace namespace of the attributes.
     *
     * @return the matching AttributeType class, or null if no matching  
     **/
    
    public Class getAttributeImplementation(String namespace);
}
