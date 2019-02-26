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

package gov.lanl.arc.registry;

import gov.lanl.arc.ARCEnvConfig;

import java.util.Iterator;
import java.util.Properties;

/**
 * OAIRegistryMain.java
 * 
 * @author ludab
 */

public class OAIRegistryMain {

    public static void main(String[] args) {
        try {
            String baseUrl = args[0];
            String arc_uri = args[1];
            OAIRegistry registry = new OAIRegistry(baseUrl);
            ARCEnvConfig t = registry.getARCConfig(arc_uri);
            for (Iterator it = registry.iterator(); it.hasNext();) {
                Properties p = ((ARCEnvConfig) it.next()).getProperties();
                p.list(System.out);
            }
            ARCEnvConfig tt = registry.getARCConfig(arc_uri);
            Properties pp = tt.getProperties();
            pp.list(System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
