/*
 * Descriptor.java
 *
 * Created on October 8, 2005, 5:10 PM
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

package info.repo.didl.impl;

import info.repo.didl.DIDLVisitorType;
import info.repo.didl.DescriptorType;
import info.repo.didl.StatementType;
import info.repo.didl.impl.DIDLBaseList;
import java.util.List;

/**
 * The <code>Descriptor</code> extends <code>DescriptableBase</code> and provides 
 * a wrapper for <code>DescriptorType</code> objects.  Each Descriptor object
 * provides a clean interface to manage Descriptor Statements.
 * 
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public final class Descriptor extends DescriptableBase implements DescriptorType {
    private final DIDLBaseList<StatementType> statements = new DIDLBaseList<StatementType>();
    
    /**
     * Creates a new Descriptor instance
     */
    public Descriptor() {}
    
    /**
     * Creates a new Descriptor instance with associated identifier
     * @param id idenfifier of instance
     */
    public Descriptor(final String id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        
        setId(id);
    }
    
    /**
     * Add a StatementType object to list
     * @param resource a StatementType instance
     * @return instance added to list
     */
    public StatementType addStatement(final StatementType statement) {
      return statements.add(statement);
    }
    
    /**
     * Gets the list of Statements
     * @return an ArrayList of StatementType objects
     */
    public List<StatementType> getStatements() {
        return statements.getList();
    }
    
    /**
     * Replace StatementType object contained in list
     * @param n new object
     * @param o old object
     * @return instance of old object
     */
    public StatementType replaceStatement(final StatementType n, final StatementType o) {
       return statements.replace(n,o);
    }
    
    /**
     * Remove a StatementType
     * @param record object to be removed from the list
     * @return instance of removed object
     */
    public StatementType removeStatement(final StatementType statement) {
       return statements.remove(statement);
    }
    
    /**
     * Implements the visitor pattern for serialization
     */
    public void accept(final DIDLVisitorType visitor) {
        if (visitor == null) {
            throw new IllegalArgumentException();
        }
        
        visitor.visitStart(this);
        
        for (DescriptorType descriptor: getDescriptors()) {
            descriptor.accept(visitor);
        }
        
        for (StatementType statement: getStatements()) {
            statement.accept(visitor);
        }
        
        visitor.visitEnd(this);
    }
}