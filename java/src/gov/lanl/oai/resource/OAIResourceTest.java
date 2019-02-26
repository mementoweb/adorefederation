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

package gov.lanl.oai.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class OAIResourceTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        String envPropsFile = "/Volumes/UserData/rchute/Desktop/tmp/oai-resource/etc/examples/env.properties";
        String projectPropsFile = "/Volumes/UserData/rchute/Desktop/tmp/oai-resource/etc/examples/aps/aps.properties";
        
        OAIResource resource = new OAIResource();
        Properties envProps = new Properties();
        try {
            envProps.load(new FileInputStream(new File(envPropsFile)));
            resource.setEnvParameters(envProps);
            Properties projectProps = new Properties();
            projectProps.load(new FileInputStream(new File(projectPropsFile)));
            resource.setProjectParameters(projectProps);
            resource.setFrom("2005-06-14");
            resource.setUntil("2005-06-15");
            resource.setLastIngest("20050713010101");
            resource.setLockFile();
            resource.beginHarvest();
            resource.beginDeref();
            resource.clearLockFile();
        } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        } catch (OAIResourceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

}
