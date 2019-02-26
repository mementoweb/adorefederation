/*
 * Copyright (c) 2008  Los Alamos National Security, LLC.
 *
 * Los Alamos National Laboratory
 * Research Library
 * Digital Library Research & Prototyping Team
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
 * 
 */

package gov.lanl.disseminator.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class EntityIterator implements Iterator {
private Entity e;
List <Entity> entities;
ListIterator iterator;
	 public EntityIterator(Entity e) {
	      // check for null being passed in etc.
	      this.e = e;
	      entities = new ArrayList();
	      makeList(e);
	      iterator = entities.listIterator();
	      
	   }

	 private void makeList ( Entity e) {		
	 for (Entity subent : e.getEntities()) {
		 entities.add(subent);
		 makeList(subent);
	 }	
	 }
	 
	 public boolean hasNext() { return iterator.hasNext(); }
	   public Entity next() { return (Entity)iterator.next(); }

	 public  void remove() {
	 iterator.remove();
	}
}
