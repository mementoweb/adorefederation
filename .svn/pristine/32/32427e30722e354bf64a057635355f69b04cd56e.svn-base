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

package gov.lanl.xmltape.registry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * DirectoryRegistry.java 
 * 
 * Directory based tape Registry, each property file in this directory
 * corresponds to one xmltape configuration. file name is same as xmltape name.
 *  
 */
public class DirectoryRegistry extends Registry {
    String configdir;

    static Logger log = Logger.getLogger(DirectoryRegistry.class.getName());

    public DirectoryRegistry(String dir) throws RegistryException {
        super();
        this.configdir = dir;
        log.debug("configdir: " + configdir);
        try {
            File directory = new File(configdir);
            if (!directory.exists())
                throw new RegistryException(
                        "directory configuration file doesn't exist");
            String[] names = directory.list(new Filter());
            log.debug(names);
            for (int i = 0; i < names.length; i++) {
                String tapename = names[i].substring(0, names[i].indexOf('.'));
                log.debug("tape " + i + ": " + tapename);
                tapemap.put(tapename, readTapeConfig(tapename));
            }
        } catch (java.lang.SecurityException ex) {
            throw new RegistryException(ex);
        }
    }

    /**
     * read tape configuration, implemented by underlying system
     */
    protected TapeConfig readTapeConfig(String tapename)
            throws RegistryException {
        try {
            File f = new File(configdir, tapename + ".properties");
            Properties p = new Properties();
            p.load(new FileInputStream(f));
            p.setProperty(TapeConfig.TAG_TAPE_REPO_ID, tapename);
            return new TapeConfig(p);
        } catch (java.io.IOException ex) {
            throw new RegistryException(ex);
        }
    }

}

class Filter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        if (name.indexOf(".properties") == -1)
            return false;
        else
            return true;
    }
}
