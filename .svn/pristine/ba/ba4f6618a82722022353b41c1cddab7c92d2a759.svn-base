/*
 * AbstractAttribute.java
 *
 * Created on October 17, 2005, 3:27 PM
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

import info.repo.didl.AttributeType;
import java.util.AbstractMap;
import java.util.HashMap;

/**
 * <code>AbstractAttribute</code> provides a generic framework for creation of
 * DIDL level attributes.  Attributes are stored as key-value pairs.
 * 
 * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
 * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
 * @author Xiaoming Liu <liu_x@lanl.gov>
 */
public abstract class AbstractAttribute extends AbstractMap implements AttributeType {
    private HashMap<Object,Object> map;
    
    /**
     * <code>Entry</code> handles the key-value attribute pairs 
     * 
     * @author Kjell Lotigiers <kjell.lotigiers@ugent.be>
     * @author Patrick Hochstenbach <patrick.hochstenbach@ugent.be>
     * @author Xiaoming Liu <liu_x@lanl.gov>
     */
    class Entry implements AbstractMap.Entry {
        private Object key;
        private Object value;
        
        protected Entry(Object key, Object value) {
            this.key = key;
            this.value = value;
        }
        
        public Object getKey() {
            return key;
        }
        
        public Object getValue() {
            return value;
        }
        
        public Object setValue(Object value) {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Creates a new AbstractAttribute instance
     */
    public AbstractAttribute() {
        this.map = new HashMap<Object, Object>();
    }
    
    /**
     * Gets the value for the specified key
     */
    public Object getValue(final String key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        
        return map.get(key);
    }
    
    /**
     * Sets the value for the specified key
     */
    public void setValue(final String key, final Object value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        
        map.put(key, value);
    }
    
    /**
     * Gets the set the key/value attribute pairs
     */
    public java.util.Set<java.util.Map.Entry<Object, Object>> entrySet() {
        return map.entrySet();
    }
    
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        else if (this.getClass() == o.getClass()) {
            return true;
        }
        else {
            return false;
        }
    }

    public Object remove(Object key) {
        throw new UnsupportedOperationException();
    }

    public Object put(Object key, Object value) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }
}
