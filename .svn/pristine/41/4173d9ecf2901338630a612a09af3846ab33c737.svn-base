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

import java.net.URL;

/**
 * dummy client for adore delete
 * 
 * when a collection is requested to be deleted, both collection and associated
 * service will be deleted
 * 
 * when a service is requested to be deleted, only service will be deleted.
 */

import gov.lanl.ockham.ServiceRegistryConstants;
import gov.lanl.ockham.client.app.Registry;
import gov.lanl.ockham.client.app.UnrecognizedIdentifierException;
import gov.lanl.util.properties.PropertiesUtil;

public class Delete {
    public Registry registry = null;

    public Delete(URL baseurl) {
        registry = new Registry(baseurl);
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("usage: java gov.lanl.ockham.put.client.Delete <identifier> ");
            System.exit(1);
        }
        Delete delete = new Delete(new URL(PropertiesUtil.getGlobalProperty(ServiceRegistryConstants.PUT_BASEURL)));
        if (delete.delete(args[0])) {
            System.exit(0);
        } else {
            System.err.println("deletion failed");
            System.exit(1);
        }
    }

    public boolean delete(String identifier) throws Exception {
        if ("xmltape".equals(Util.getRepositoryType(identifier))) {
            return registry.delete(identifier)
                    && registry.delete(Util.getOAIPMHServiceId(identifier))
                    && registry.delete(Util.getOpenURLaDORe1ServiceId(identifier))
                    && registry.delete(Util.getOpenURLaDORe2ServiceId(identifier))
                    && registry.delete(Util.getOpenURLaDORe3ServiceId(identifier))
                    && registry.delete(Util.getOpenURLaDORe7ServiceId(identifier));
        } else if ("arc".equals(Util.getRepositoryType(identifier))) {
            return registry.delete(identifier)
                    && registry.delete(Util.getOpenURLaDORe3ServiceId(identifier))
                    && registry.delete(Util.getOpenURLaDORe4ServiceId(identifier));
        } else
            throw new UnrecognizedIdentifierException(identifier);
    }

}
