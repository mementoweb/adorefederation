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

package gov.lanl.ockham.client.adore;

import java.util.Map;

import gov.lanl.ockham.ServiceRegistryConstants;
import gov.lanl.ockham.client.oai.OckhamOAIRegistry;
import gov.lanl.ockham.iesrdata.IESRCollection;
import gov.lanl.ockham.iesrdata.IESRService;

import gov.lanl.registryclient.RegistryException;
import gov.lanl.registryclient.RegistryRecord;
import gov.lanl.util.properties.PropertiesUtil;

/**
 * List registries for adore environment.
 * 
 * 
 * @author liu_x
 * 
 */
public class List {
    OckhamOAIRegistry registry = null;

    public List(String baseurl) throws RegistryException {
        registry = new OckhamOAIRegistry(baseurl);
    }

    public void listCollections() throws RegistryException {
        Map<String, RegistryRecord<IESRCollection>> collections = registry.listCollections(
                null, null);
        for (Map.Entry<String, RegistryRecord<IESRCollection>> entry : collections.entrySet()) {
            System.out.print("\t" + entry.getValue().getIdentifier());
            System.out.print("\t" + entry.getValue().getDatestamp());
            System.out.println("\t"
                    + ((IESRCollection) (entry.getValue().getMetaData()))
                            .getTitle());
        }
    }

    public void listServices() throws RegistryException {
        Map<String, RegistryRecord<IESRService>> services = registry
                .listServices(null, null);
        for (Map.Entry<String, RegistryRecord<IESRService>> entry : services.entrySet()) {
            System.out.print("\t" + entry.getValue().getIdentifier());
            System.out.print("\t" + entry.getValue().getDatestamp());
            System.out.println("\t"
                    + ((IESRService) (entry.getValue().getMetaData()))
                            .getLocator());
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            printUsage();
        }

        List list = new List(PropertiesUtil.getGlobalProperty(ServiceRegistryConstants.OAI_BASEURL));
        if ("collection".equals(args[0])) {
            list.listCollections();
        } else if ("service".equals(args[0])) {
            list.listServices();
        } else
            printUsage();
    }

    public static void printUsage() {
        System.err.println("usage: java gov.lanl.ockham.client.adore.List <collection|service> ");
        System.exit(1);
    }

}
