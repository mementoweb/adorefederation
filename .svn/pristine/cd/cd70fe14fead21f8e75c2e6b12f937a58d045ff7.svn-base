/*
 * DIDLVisitorType.java
 *
 * Created on October 11, 2005, 9:14 AM
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

package info.repo.didl;

/**
 * The <code>DIDLVisitorType</code> defines the visitor pattern for navigating 
 * DIDL structure.
 *
 * @author Xiaoming Liu <liu_x@lanl.gov>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 */
public interface DIDLVisitorType {
    void visitStart(DIDLType didl);
    void visitEnd(DIDLType didl);
    
    void visitStart(DIDLInfoType didlInfo);
    void visitEnd(DIDLInfoType didlInfo);
    
    void visitStart(ItemType item);
    void visitEnd(ItemType item);
    
    void visitStart(ComponentType component);
    void visitEnd(ComponentType component);
    
    void visitStart(DescriptorType descriptor);
    void visitEnd(DescriptorType descriptor);
    
    void visitStart(StatementType statement);
    void visitEnd(StatementType statement);
    
    void visitStart(ResourceType resource);
    void visitEnd(ResourceType resource);
}