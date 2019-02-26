/*
 * DefaultStrategy.java
 *
 * Created on October 14, 2005, 1:18 PM
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

package info.repo.didl.impl.serialize;

import info.repo.didl.AttributeType;
import info.repo.didl.impl.content.ByteArray;
import info.repo.didl.serialize.AttributeStrategyConditionType;
import info.repo.didl.serialize.ContentStrategyConditionType;
import info.repo.didl.serialize.DIDLStrategyType;
import java.util.ArrayList;
import java.util.List;


/**
 * The <code>XMLStrategy</code> defines implementation classes for Content or 
 * Attribute types; although useful for other cases, it is primarily used by
 * deserialization process. 
 * 
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public final class XMLStrategy implements DIDLStrategyType {
    private final List<AttributeStrategyConditionType> attributeStrategyMap;
    private final List<ContentStrategyConditionType>  contentStrategyMap;
       
    /**
     * Creates a new instance of DefaultStrategy
     */
    public XMLStrategy() {
        contentStrategyMap = new ArrayList<ContentStrategyConditionType>();
        attributeStrategyMap = new ArrayList<AttributeStrategyConditionType>();
    }
    
    /**
     * Adds a new Content Type Implementation
     */
    public void addContentStrategy(ContentStrategyConditionType condition) {
        contentStrategyMap.add(condition);
    }
    
    /**
     * Adds a new Attribute Implementation
     */
    public void addAttributeStrategy(AttributeStrategyConditionType condition) {
       attributeStrategyMap.add(condition);
    }
    
    /**
     * Gets the Attribute Implementation Class for a given namespace
     */
    public Class getAttributeImplementation(String namespace) { 
        for (AttributeStrategyConditionType cond: attributeStrategyMap) {
            if (cond.match(namespace)) {
                return cond.getImplClass();
            }
        }
        
        return null;
    }
    
    /**
     * Gets the Content Type Implementation Class for given criteria
     */
    public Class getContentImplementation(
            final AttributeType attributes, final String mimeType,
            final String namespace) {
        
        for (ContentStrategyConditionType cond: contentStrategyMap) {
            if (cond.match(attributes,mimeType,namespace)) {
                return cond.getImplClass();
            }
        }
        // ByteArray is a catch all for content
        return ByteArray.class;
    }
  
}
