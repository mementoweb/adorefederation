/*
 * TempWriter.java
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

package org.adore.didl.json;

import java.io.File;
import java.io.FileOutputStream;
import java.security.MessageDigest;

/**
 * temporary writer for processing bytestream
 */

public class FileBytestreamHandler implements BytestreamHandler{
    private String dir=null;
    MessageDigest digester;
    
    public FileBytestreamHandler(String dir){
	this.dir=dir;
	try{
            digester = MessageDigest.getInstance("MD5");
        } catch (java.security.NoSuchAlgorithmException ex){
            System.err.println("MD5 cannot be found?");
	}
    }

    /**
     * write byte array to a directory, file name is the md5 value of
     * file content. return a file URI
     * 
     * 
     * @param input byte array to write
     * @return file URI of returned stream
     * 
     */

    public String write(byte[] input) throws java.io.IOException{
	byte[] digest=digester.digest(input);
	StringBuilder filename=new StringBuilder();
	for (byte b: digest){
	    filename.append(Integer.toHexString(b & 0xFF));
	}
	File file=new File(dir,filename.toString());
	
	FileOutputStream fw=new FileOutputStream(file);
	fw.write(input);
	fw.close();
	return "file://"+file.getAbsolutePath();

    }


}
