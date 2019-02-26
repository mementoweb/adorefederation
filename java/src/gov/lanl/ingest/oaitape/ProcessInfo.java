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

package gov.lanl.ingest.oaitape;

import java.util.Vector;

/**
 * ProcessInfo mainly serves as data structure 
 */

public class ProcessInfo
 {
    private Vector nLogInfo; // of String []
    private boolean shouldCommit = false;
    private String  faultDescription;
     
    public ProcessInfo ()
    {  nLogInfo = new Vector();
    }

    public void setStatus(boolean status)
    {this.shouldCommit = status; }

    public boolean getStatus()
    {  return shouldCommit;}
    
    public void setMessage(String msg) 
    {this.faultDescription = msg; } 
 
    public String  getMessage() 
    {  return this.faultDescription;} 

     /**
      * adds the array of values per stream to print csv output
      */
    public void addLogInfo(String [] loginfo){ 
        nLogInfo.add(loginfo); 
    } 

     /**
      *   returns vector of arrays of values to print csv output
      */
    public Vector returnVectorofLogInfo(){
    return nLogInfo;
    }
  
    
} 
