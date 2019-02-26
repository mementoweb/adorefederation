/*
 * DescriptorType.java
 *
 * Created on October 8, 2005, 10:11 AM
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

import java.util.List;

/**
 * The <code>DescriptorType</code> represents DIDL Descriptor element,
 * Descriptorype provides the primary access of <code>StatementType</code>
 * 
 * @author Xiaoming Liu <liu_x@lanl.gov>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @see StatementType
 */
public interface DescriptorType extends DescriptableBaseType {
    /**
     * Get a list of statements
     * @return a list of statements
     */
    public List<StatementType> getStatements();
    
    /**
     * Add a new statement to the descriptor
     * @param statement the statement to be added
     * @return the statement added
     * @throws DIDLException - ALREADY_DEFINED if the descriptor contains one statement
     */
    public StatementType addStatement(StatementType statement) throws DIDLException;
    
    /**
     * Replace a statement with a new version
     * @param n the new statement
     * @param o the statement replaced
     * @return the statement replaced or null when the statement was not found
     */
    public StatementType replaceStatement(StatementType n, StatementType o);
    
    /**
     * Delete a statement
     * @param statement the statement to be deleted
     * @return the deleted statement or null when the statement was not found
     */
    public StatementType removeStatement(StatementType statement);
}
