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

package gov.lanl.disseminator.service.toc;

import gov.lanl.disseminator.model.Entity;
import gov.lanl.util.xml.XmlUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class EntityToTrix {

    static String res_url = null;

    String toTrix(Entity referent, String curdate, String res_url)
            throws Exception {

        this.res_url = res_url;
        List<String> srvList = new ArrayList<String>();

        StringBuilder builder = new StringBuilder();
        builder.append("<trix:TriX  >");

        String location = "?url_ver=Z39.88-2004&amp;rft_id="
                + referent.getProperty(Entity.IDENTIFIER_ATT) + "&amp;svc_id="
                + "info:lanl-repo/svc/toc.ore";
        referent.setProperty("currid", referent
                .getProperty(Entity.IDENTIFIER_ATT));

        if (res_url != null) {
            location = res_url + location;
        }

        builder.append("<trix:graph><trix:uri>" + location + "</trix:uri>");
        builder.append("<trix:triple>");
        builder.append("<trix:uri>" + location + "</trix:uri>");
        builder.append("<trix:uri>dc:created</trix:uri>");
        builder.append("<trix:typedLiteral datatype=\"xs:dateTime\">" + curdate
                + "</trix:typedLiteral>");
        builder.append("</trix:triple>");

        append(builder, referent, referent, srvList);
        builder.append("</trix:graph>");
        builder.append("</trix:TriX>");
        return builder.toString();
    }

    private static void append(StringBuilder builder, Entity referent,
            Entity e, List<String> srvList) {
        for (Entity subent : e.getEntities()) {
            if (subent.hasProperty("item")) {
                builder.append("<trix:triple>");
                builder.append("<trix:uri>"
                        + XmlUtil.encode(referent
                                .getProperty(Entity.IDENTIFIER_ATT))
                        + "</trix:uri>");
                builder.append("<trix:uri>info:ore/sem/hasPart</trix:uri>");
                builder.append("<trix:uri>"
                        + XmlUtil.encode(subent
                                .getProperty(Entity.IDENTIFIER_ATT))
                        + "</trix:uri>");
                builder.append("</trix:triple>");
            }
            appendEntry(builder, referent, subent, srvList);
            append(builder, referent, subent, srvList);
        }
    }

    private static void appendEntry(StringBuilder builder, Entity referent,
            Entity entity, List<String> srvList) {
        for (String service : entity.getServices()) {
            System.out.println("service" + service);
            if (!srvList.contains(service)) {
                srvList.add(service);
                HashMap params = entity.getParams(service);
                String cdate = (String) params.get("dateCreated");
                String title = (String) params.get("title");
                String sem = (String) params.get("semantic");
                Date servdate = gov.lanl.util.DateUtil.utc2Date(cdate);
                String id; 
                if (entity.hasProperty("item")) {
                    id = entity.getProperty(Entity.IDENTIFIER_ATT);
                    referent.setProperty("currid", id);
                }
                id = referent.getProperty("currid");
                String locurl = "?url_ver=Z39.88-2004&amp;rft_id=" + id
                        + "&amp;svc_id=" + service;

                if (res_url != null) {
                    locurl = res_url + locurl;
                }
                builder.append("<trix:triple>");
                builder.append("<trix:uri>" + XmlUtil.encode(id)
                        + "</trix:uri>");
                builder.append("<trix:uri>info:ore/sem/hasView</trix:uri>");
                builder.append("<trix:uri>" + locurl + "</trix:uri>");
                builder.append("</trix:triple>");
            }
        }
    }
}
