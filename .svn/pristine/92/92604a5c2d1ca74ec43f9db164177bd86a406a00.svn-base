/*
 * Copyright (c) 2007  Los Alamos National Security, LLC.
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

package gov.lanl.locator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IdLocation implements Comparable {
    private String id;
    private String repo;
    private String date;
 
    public IdLocation(){}
    
    public IdLocation(String id, String repo, String date) {
        this.id = id;
        this.repo = repo;
        this.date = date;
    }
    
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getRepo() {
        return repo;
    }
    public void setRepo(String repo) {
        this.repo = repo;
    }

    public int compareTo(Object arg0) {
        final int EQUAL = 0;
        final int LESSER = -1;
        final int GREATER = 1;
        IdLocation i = (IdLocation) arg0;
        
        if (this.equals(i)) 
               return EQUAL;
        else if(i.getDate() != null && this.getDate() != null) {
            String a = i.getDate();
            String b = this.getDate();
            if (mysql2Date(b).after(mysql2Date(a))) {
                return LESSER;
            } else 
                return GREATER;
        } else
               return LESSER;
    }
    
    private static final Date mysql2Date(String mysqlDateString) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        ParseException exception = null;
        try {
            date = formatter.parse(mysqlDateString);
        } catch (ParseException e) {
            exception.printStackTrace();
        }
        return date;
    }
}
