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

package gov.lanl.archive.util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class FileUtil {
    
    /**
     * Gets a ArrayList of File objects provided a dir or file path.
     * @param filePath
     *        Absolute path to file or directory
     * @param fileFilter
     *        Filter dir content by extention; allows "null"
     * @param recursive
     *        Recursively search for files
     * @return
     *        ArrayList of File objects matching specified criteria.
     */
    public static ArrayList<File> getFileList(String filePath, FileFilter fileFilter, boolean recursive) {
        ArrayList<File> files = new ArrayList<File>();
        File file = new File(filePath);
        if (file.exists() && file.isDirectory()) {
            File[] fa = file.listFiles(fileFilter);
            for (int i = 0; i < fa.length; i++) {
                if (fa[i].isFile())
                  files.add(fa[i]);
                else if (recursive && fa[i].isDirectory())
                  files.addAll(getFileList(fa[i].getAbsolutePath(), fileFilter, recursive));
            }
        }
        else if (file.exists() && file.isFile()) {
            files.add(file);
        }
        
        return files;
    }
}
