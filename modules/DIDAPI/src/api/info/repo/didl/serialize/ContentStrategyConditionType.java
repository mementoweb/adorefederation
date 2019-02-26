/*
 * ContentStrategyConditionType.java
 *
 * Created on January 16, 2006, 11:37 AM
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
 * The <code>ContentStrategyConditionType</code> interface is used to construct
 * rules for content matching. The matching solution is explained in 
 * <code> DIDLStrategyType </code>
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 * @see DIDLStrategyType
 */
public interface ContentStrategyConditionType {
    
    /**
     * if the combinations of attributes match a Content 
     * @param attributes attributes associating with the ContentWrapper
     * @param mimeType mimeType attributes of the ContentWrapper
     * @param namespace namespace of the inline content.
     * @return true if matched, false if not.
     */
    public boolean match(AttributeType attributes,String mimeType, String namespace);
   
    /**
     * find implementation content class associating with this condition
     * @return implementation content class 
     */
    public Class getImplClass();
}
